package com.student22110006.fashionshop.data.model.customer

data class CustomerInfoData(
    val customerId: Int,
    val customerName: String,
    val phoneNumber: String,
    val dob: String?,
    val address: String,
    val accountId: String,
    val userName: String,
    val displayName: String,
    val password: String,
    val email: String,
    val firebaseId: String,
    val otp: String?,
    val otpExpiration: String?,
    val typeId: Int
)