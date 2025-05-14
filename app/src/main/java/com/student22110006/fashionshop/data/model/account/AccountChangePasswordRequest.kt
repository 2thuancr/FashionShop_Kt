package com.student22110006.fashionshop.data.model.account

import java.util.Date

data class AccountChangePasswordRequest(
    val email: String,
    // val oldPassword: String?,
    val password: String
)
