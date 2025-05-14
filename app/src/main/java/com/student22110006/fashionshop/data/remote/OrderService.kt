package com.student22110006.fashionshop.data.remote

import com.student22110006.fashionshop.data.model.ApiResponse
import com.student22110006.fashionshop.data.model.order.Order
import com.student22110006.fashionshop.data.model.order.OrderGetHistoryRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderService {

    @POST("Bills/create")
    suspend fun createOrder(@Body() request: Order): ApiResponse<Order>;

    @POST("Bills/update")
    suspend fun updateOrder(@Body() request: Order): ApiResponse<Order>;

    @POST("Bills/remove-product")
    suspend fun removeProduct(@Body() request: Order): ApiResponse<Order>;

    @POST("Bills/checkout")
    suspend fun checkout(@Body() request: Order): ApiResponse<Order>;

    @POST("Bills/history")
    suspend fun getOrderHistory(@Body() request: OrderGetHistoryRequest): ApiResponse<List<Order>>;
}