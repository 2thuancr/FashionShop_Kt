package com.student22110006.fashionshop.data.repository

import com.student22110006.fashionshop.data.model.ApiResponse
import com.student22110006.fashionshop.data.model.order.Order
import com.student22110006.fashionshop.data.model.order.OrderGetHistoryRequest
import com.student22110006.fashionshop.data.remote.ApiClient

class OrderRepository {
    private val api = ApiClient.orderService;

    suspend fun createOrder(request: Order): ApiResponse<Order> {
        return api.createOrder(request)
    }

    suspend fun updateOrder(request: Order): ApiResponse<Order> {
        return api.updateOrder(request)
    }

    suspend fun removeProduct(request: Order): ApiResponse<Order> {
        return api.removeProduct(request)
    }

    suspend fun checkout(request: Order): ApiResponse<Order> {
        return api.checkout(request)
    }

    suspend fun getOrderHistory(request: OrderGetHistoryRequest): ApiResponse<List<Order>> {
        return api.getOrderHistory(request)
    }
}