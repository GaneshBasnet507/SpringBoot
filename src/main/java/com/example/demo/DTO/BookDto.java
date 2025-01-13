package com.example.demo.DTO;

import com.example.demo.model.Books;

public class BookDto {


    private int id;


    private String title;


    private String author;


    private String genre;


    private Double price;


    private String quantity;

    public BookDto(int id, String title, String author, String genre, Double price, String quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.quantity = quantity;
    }

    public BookDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public static BookDto mapToBooks(Books books){
        BookDto bookDto = new BookDto();
        bookDto.setId(books.getId());
        bookDto.setTitle(books.getTitle());
        bookDto.setAuthor(books.getAuthor());
        bookDto.setGenre(books.getGenre());
        bookDto.setPrice(books.getPrice());
        bookDto.setQuantity(books.getQuantity());
        return bookDto;
    }
}
