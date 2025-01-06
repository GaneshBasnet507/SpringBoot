package com.example.demo.controller;

import com.example.demo.DTO.OrderDto;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-order")
public class OrderController {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderService orderService;

    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @PostMapping("/place-order")
    public Order placeOrder(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalArgumentException("Session does not exist. You have to login first.");
        }
        String username = (String) session.getAttribute("username");
        System.out.println("username is " + username);
        if (username == null) {
            throw new IllegalArgumentException("No username found in session");
        }
        return orderService.placingOrder(request);
    }

    @GetMapping("/retrieve-order/{userId}")
    public Order userOrder(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalArgumentException("Session does not exist. You have to login first.");
        }
        String username = (String) session.getAttribute("username");
        System.out.println("username is this " + username);
        if (username == null) {
            throw new IllegalArgumentException("No username found in session");
        }
        return orderService.retrieveOrder(username);
//    public ResponseEntity<OrderDto> retrieveOrder(@PathVariable int userId, HttpServletRequest request) {
//        List<OrderDto> orderDtos =orderService.retrieveOrder(userId,request);
//        return new ResponseEntity<>(orderDtos, HttpStatus.OK);

    }


    @PutMapping("/update-status/{userId}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable int userId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalArgumentException("Session does not exist. You have to login first.");
        }
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new IllegalArgumentException("No username found in session");
        }
        try {
            Order orderUpdate = orderService.updateOrderStatus(userId, username);
            return new ResponseEntity<>(orderUpdate, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


    }
}

