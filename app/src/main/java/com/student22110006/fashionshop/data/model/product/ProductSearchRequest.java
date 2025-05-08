package com.student22110006.fashionshop.data.model.product;

public class ProductSearchRequest {
    public Integer page;
    public Integer pageSize;
    public String query;
    public String brand;
    public String size;
    public Double minPrice;
    public Double maxPrice;
    public Integer categoryId;

    public ProductSearchRequest(Integer page, Integer pageSize, String query) {
        this.pageSize = pageSize;
        this.page = page;
        this.query = query;
    }

    public ProductSearchRequest(Integer page, Integer pageSize, String query, String brand, String size, Double minPrice, Double maxPrice, Integer categoryId) {
        this.page = page;
        this.pageSize = pageSize;
        this.query = query;
        this.brand = brand;
        this.size = size;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.categoryId = categoryId;
    }
}

