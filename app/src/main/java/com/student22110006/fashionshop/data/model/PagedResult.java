package com.student22110006.fashionshop.data.model;

import java.util.List;

public class PagedResult<T> {
    private List<T> items;
    private int totalItems;
    private int page;
    private int pageSize;

    public PagedResult() {
    }

    public PagedResult(List<T> items, int totalItems, int page, int pageSize) {
        this.items = items;
        this.totalItems = totalItems;
        this.page = page;
        this.pageSize = pageSize;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
