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

}

