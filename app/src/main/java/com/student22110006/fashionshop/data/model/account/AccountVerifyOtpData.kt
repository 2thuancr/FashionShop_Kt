package com.student22110006.fashionshop.data.model.account

data class AccountVerifyOtpData(
    val status: String,
    val userName: String,
    val otp: String,
    val verifiedOTP: String?,
    val expirationTime: String?
)
