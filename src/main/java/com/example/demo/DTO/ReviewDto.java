package com.example.demo.DTO;

import com.example.demo.model.Review;

public class ReviewDto {
    private int id;
    private String comment;
    private int rating;
    private int book_id;
    private int user_id;
    private double avg_ratting;
    public ReviewDto(){

    }

    public ReviewDto(int id, String comment, int rating, int book_id, int user_id, double avg_ratting) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.book_id = book_id;
        this.user_id = user_id;
        this.avg_ratting = avg_ratting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getAvg_ratting() {
        return avg_ratting;
    }

    public void setAvg_ratting(double avg_ratting) {
        this.avg_ratting = avg_ratting;
    }

    public static ReviewDto mapToReviewDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setComment(review.getComment());
        reviewDto.setRating(review.getRating());
        reviewDto.setBook_id(review.getBooks().getId());
        reviewDto.setUser_id((int) review.getUser().getId());
        return reviewDto;

    }
}
