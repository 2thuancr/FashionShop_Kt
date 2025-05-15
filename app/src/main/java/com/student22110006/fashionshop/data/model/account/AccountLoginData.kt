package com.student22110006.fashionshop.data.model.account

data class AccountLoginData(
    val token: String,
    // val refreshToken: String,
    // val user: User

    val customerId: String,
    val userName: String,
    val displayName: String,
    val typeID: Int
)
