package com.student22110006.fashionshop.data.model.account

import java.util.Date

data class AccountRegisterRequest(
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val firebaseId: String?,
    val doB: Date?,
    val address: String?,
    val userName: String,
    val password: String
)
