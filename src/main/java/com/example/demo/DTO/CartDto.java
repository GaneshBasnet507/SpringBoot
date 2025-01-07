package com.example.demo.DTO;

public class CartDto {
    private int id;


    private double total_amount;


    private int user_id;

    public CartDto(int id, double total_amount, int user_id) {
        this.id = id;
        this.total_amount = total_amount;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
