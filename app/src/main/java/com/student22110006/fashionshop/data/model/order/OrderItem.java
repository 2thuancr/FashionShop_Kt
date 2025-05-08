package com.student22110006.fashionshop.data.model.order;

public class OrderItem {
    private final String name;
    private final int quantity;
    private final String price;
    private final String imageUrl;

    public OrderItem(String name, int quantity, String price, String imageUrl) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
