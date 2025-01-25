package com.student22110006.fashionshop.data.model

data class ApiResponse<T>(
    val isSuccess: Boolean,
    val message: String?,
    val errorCode: String?,
    val data: T?
)
