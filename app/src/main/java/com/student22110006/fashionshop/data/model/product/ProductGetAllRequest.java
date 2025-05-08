package com.student22110006.fashionshop.data.model.product;

public class ProductGetAllRequest {
    public int page;
    public int pageSize;

    public ProductGetAllRequest(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
