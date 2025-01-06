package com.example.demo.service;

import com.example.demo.model.Books;
import com.example.demo.model.Order;
import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShoppingCartRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ShoppingCartRepository shoppingCartRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ShoppingCartRepository shoppingCartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Transactional
    public Order placingOrder(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalArgumentException("Session does not exist. You have to login first.");
        }
        String userName = (String) session.getAttribute("username");
        System.out.println(userName);
        if (userName == null) {
            throw new IllegalArgumentException("No user found in session");
        }
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("No user found with username: " + userName);
        }
        User user = userOptional.get();
        Optional<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserId(user.getId());
        if (!shoppingCarts.isPresent()) {
            throw new IllegalArgumentException("No shopping cart found for user: " + userName);
        }
        ShoppingCart cart = shoppingCarts.get();
        List<Books> booksCopy = new ArrayList<>();
        for (Books book : cart.getBooks()) {
            booksCopy.add(book);
        }
        Order order = new Order(user, cart.getTotalAmount(), false, booksCopy);
        cart.getBooks().clear();
        cart.setTotalAmount(0);
        orderRepository.save(order);
        return order;
    }

    public Order retrieveOrder(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        Optional<User> userOptional = userRepository.findByUserName(username);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("No user found with username: " + username);
        }
        User user = userOptional.get();
        int userId = (int) user.getId();

        Optional<Order> orderOptional = orderRepository.findByUserId(userId);
        if (!orderOptional.isPresent()) {
            throw new IllegalArgumentException("No order found with that user ID: " + userId);
        }

        Order userOrder = orderOptional.get();
        boolean status = userOrder.getStatus();

        return userOrder;
    }

    public Order updateOrderStatus(int userId, String username) {
        Optional<User> userOptional = userRepository.findByUserName(username);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("No user found with that username" + username);
        }
        User user = userOptional.get();
        if (user.getRole().equals("ADMIN")) {
            Optional<Order> orderOptional = orderRepository.findByUserId(userId);
            if (!orderOptional.isPresent()) {
                throw new IllegalArgumentException("No order found with ID: " + userId);
            }
            Order order = orderOptional.get();
            order.setStatus(true);
            orderRepository.save(order);
            return order;
        } else {
            throw new IllegalArgumentException("User does not have permission to update order status");

        }

    }

}




