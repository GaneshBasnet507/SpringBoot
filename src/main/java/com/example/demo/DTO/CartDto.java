package com.example.demo.DTO;

import com.example.demo.model.Books;
import com.example.demo.model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class CartDto {

    public CartDto() {
    }

    private int id;
    private String title;


    private double total_amount;


    private int user_id;

    public CartDto(int id, String title, double total_amount, int user_id) {
        this.id = id;
        this.title = title;
        this.total_amount = total_amount;
        this.user_id = user_id;
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

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public static List<CartDto> mapToCarts(List<ShoppingCart> carts) {
        List<CartDto> cartDtoList = new ArrayList<>();
        for (ShoppingCart shoppingCart : carts) {
            CartDto cartDto = new CartDto();
            cartDto.setId(shoppingCart.getId());
            List<Books> books = shoppingCart.getBooks();
             for (int i=0; i<books.size(); i++){
                 cartDto.setTitle(shoppingCart.getBooks().get(i).getTitle());
             }
            cartDto.setTotal_amount(shoppingCart.getTotalAmount());
            int user_id = (int) shoppingCart.getUser().getId();
            cartDto.setUser_id(user_id);
            cartDtoList.add(cartDto);
        }
        return cartDtoList;
    }
}
