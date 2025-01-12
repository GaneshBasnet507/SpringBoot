package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "shopping_cart")


public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    public ShoppingCart() { }
    public ShoppingCart(User user, List<Books> books, double totalAmount) {
        this.user = user;
        this.books = books;
        this.totalAmount = totalAmount;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "shopping_cart_books",
            joinColumns = @JoinColumn(name = "shopping_cart_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )

    private List<Books> books = new ArrayList<>();
    private double totalAmount;
    public void addBook(Books book) {
        this.books.add(book);
        calculateTotalAmount();
    }
    public void calculateTotalAmount() {
        totalAmount = books.stream()
                .mapToDouble(Books::getPrice)
                .sum();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }


}
