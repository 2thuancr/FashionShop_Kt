package com.student22110006.fashionshop.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    /**
     * Base URL for the API (notice the trailing slash).
     */
    private const val BASE_URL =
        "https://fashionshop-f0hthbhrevbwdtcj.southeastasia-01.azurewebsites.net/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Log toàn bộ request và response
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Thời gian kết nối tối đa
        .readTimeout(30, TimeUnit.SECONDS)    // Thời gian đọc response tối đa
        .writeTimeout(30, TimeUnit.SECONDS)   // Thời gian ghi request tối đa
        .addInterceptor(loggingInterceptor)   // Thêm interceptor cho logging
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Cung cấp OkHttpClient vào Retrofit
            .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON thành đối tượng
            .build()
    }

    val accountService: AccountService by lazy {
        retrofit.create(AccountService::class.java)
    }

    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }
}