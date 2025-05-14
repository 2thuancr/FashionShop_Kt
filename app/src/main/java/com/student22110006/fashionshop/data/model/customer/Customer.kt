package com.student22110006.fashionshop.data.model.customer

data class Customer(
    val customerId: Int,
    val customerName: String,
    val phoneNumber: String,
    val dob: String?,
    val address: String,
    val accountId: String
)