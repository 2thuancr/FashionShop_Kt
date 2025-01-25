package com.student22110006.fashionshop.data.model.account

data class AccountVerifyOtpRequest(
    val otp: String,
    val userName: String
)
