package com.student22110006.fashionshop.data.remote

import com.student22110006.fashionshop.data.model.ApiResponse
import com.student22110006.fashionshop.data.model.customer.Customer
import com.student22110006.fashionshop.data.model.customer.CustomerInfoData
import com.student22110006.fashionshop.data.model.customer.CustomerInfoGetInput
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CustomerService {

    @GET("Customer/GetAllCustomer")
    suspend fun getAllCustomer(): ApiResponse<Customer>

    @POST("Customer/GetCustomerInfo")
    suspend fun getCustomerInfo(@Body request: CustomerInfoGetInput): ApiResponse<CustomerInfoData>
}