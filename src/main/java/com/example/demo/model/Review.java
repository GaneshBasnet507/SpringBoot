package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn (name = "user_id",nullable = false)
    private User user;
    @OneToOne
    @JoinColumn (name = "book_id",nullable = false)
    private Books books;
    private  int rating;
    private String comment;
    private double avg_rating;
    public Review(){

    }

    public Review(User user, Books books, int rating, String comment, double avg_rating) {
        this.user = user;
        this.books = books;
        this.rating = rating;
        this.comment = comment;
        this.avg_rating = avg_rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(double avg_rating) {
        this.avg_rating = avg_rating;
    }
}
