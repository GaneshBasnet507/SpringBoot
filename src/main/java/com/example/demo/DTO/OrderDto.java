package com.example.demo.DTO;

public class OrderDto {
    private int id;
    private double totalAmount;
    private boolean status;

    public OrderDto(int id, double totalAmount, boolean status) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
