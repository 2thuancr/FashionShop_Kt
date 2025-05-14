package com.student22110006.fashionshop.data.model.order;

import java.util.Date;

public class Order {
    private int id;
    private Date businessTime;
    private int customerId;
    private double totalPrice;
    private double totalDiscount;
    private int status;
    private OrderItem[] items;

    public Order() {
        // Default constructor
    }

    public Order(int id, Date businessTime, int customerId, double totalPrice, double totalDiscount, int status, OrderItem[] items) {
        this.id = id;
        this.businessTime = businessTime;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.status = status;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(Date businessTime) {
        this.businessTime = businessTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OrderItem[] getItems() {
        return items;
    }

    public void setItems(OrderItem[] items) {
        this.items = items;
    }
}
