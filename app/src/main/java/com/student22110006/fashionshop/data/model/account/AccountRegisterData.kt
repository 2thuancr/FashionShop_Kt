package com.student22110006.fashionshop.data.model.account

data class AccountRegisterData(
    val accountId: Int,
    val customerId: Int,
    val otp: String?,
    val otpExpiration: String?
)
