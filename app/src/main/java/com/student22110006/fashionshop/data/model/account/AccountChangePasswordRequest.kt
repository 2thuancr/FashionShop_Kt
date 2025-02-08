package com.student22110006.fashionshop.data.model.account

import java.util.Date

data class AccountChangePasswordRequest(
    val username: String,
   // val oldPassword: String?,
    val newPassword: String
)
