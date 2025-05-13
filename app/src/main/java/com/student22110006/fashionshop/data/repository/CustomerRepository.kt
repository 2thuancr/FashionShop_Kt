package com.student22110006.fashionshop.data.repository

import com.student22110006.fashionshop.data.model.customer.CustomerInfoGetInput
import com.student22110006.fashionshop.data.remote.ApiClient

class CustomerRepository {
    private val api = ApiClient.customerService

    suspend fun getCustomerInfo(request: CustomerInfoGetInput) = api.getCustomerInfo(request);

    suspend fun getAllCustomer() = api.getAllCustomer();
}