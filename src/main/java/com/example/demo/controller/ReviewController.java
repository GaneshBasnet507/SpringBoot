package com.example.demo.controller;

import com.example.demo.model.Books;
import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final ReviewService reviewService;

    public ReviewController(ReviewRepository reviewRepository, ReviewService reviewService) {
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
    }
    @PostMapping("/add-review")
    public Review addReview(@RequestBody Review review,HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new IllegalArgumentException("Session does not exist. You have to login first.");
        }
        return reviewService.addUserReview(review,request);
    }
}
