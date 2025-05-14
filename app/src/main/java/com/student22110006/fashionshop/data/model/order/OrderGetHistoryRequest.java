package com.student22110006.fashionshop.data.model.order;

public class OrderGetHistoryRequest {

    private int customerId;
    private int page;
    private int pageSize;

    public OrderGetHistoryRequest(int customerId, int page, int pageSize) {
        this.customerId = customerId;
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
