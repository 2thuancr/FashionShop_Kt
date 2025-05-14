package com.student22110006.fashionshop.data.model.order;

public class OrderItem {
    private int productId;
    private String name;
    private String imageUrl;

    private int amount;
    private double price;
    public double discount;
    public double campaignDiscountPercent;

    public OrderItem() {
        // Default constructor
    }
    
    public OrderItem(int productId, String name, String imageUrl, int amount, double price) {
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.amount = amount;
        this.price = price;
    }

    public OrderItem(int productId, String name, String imageUrl, int amount, double price, double discount, double campaignDiscountPercent) {
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.amount = amount;
        this.price = price;
        this.discount = discount;
        this.campaignDiscountPercent = campaignDiscountPercent;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getCampaignDiscountPercent() {
        return campaignDiscountPercent;
    }

    public void setCampaignDiscountPercent(double campaignDiscountPercent) {
        this.campaignDiscountPercent = campaignDiscountPercent;
    }
}
