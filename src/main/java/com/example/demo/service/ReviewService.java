package com.example.demo.service;

import com.example.demo.model.Books;
import com.example.demo.model.Order;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.repository.BooksRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final BooksRepository booksRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, OrderRepository orderRepository, BooksRepository booksRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.booksRepository = booksRepository;
    }

    public Review addUserReview(Review review, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalArgumentException("Session does not exist. You have to login first.");
        }
        String userName = (String) session.getAttribute("username");
        if (userName == null) {
            throw new IllegalArgumentException("No user found in session");
        }
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("No user found with username: " + userName);
        }
        User user = userOptional.get();
        int userId = (int) user.getId();
        Optional<Order> userOrder = orderRepository.findByUserId(userId);
        if (userOrder.isEmpty()) {
            throw new IllegalArgumentException("No Order found with that userId: " + userId);
        }
        Order order = userOrder.get();
        List<Books> books = order.getBook();
        Books book = books.get(0);

        System.out.println(order);
        Review newReview = new Review(user,book, review.getRating(), review.getComment(), review.getAvg_rating());
        Review saveReview = reviewRepository.save(newReview);
        return reviewRepository.save(newReview);
    }
}
