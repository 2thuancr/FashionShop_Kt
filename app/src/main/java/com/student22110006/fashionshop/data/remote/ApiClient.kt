package com.student22110006.fashionshop.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

    var gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") // hoặc "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" nếu có ký tự Z ở cuối
        .create()

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
            .addConverterFactory(GsonConverterFactory.create(gson)) // Chuyển đổi JSON thành đối tượng
            .build()
    }

    val accountService: AccountService by lazy {
        retrofit.create(AccountService::class.java)
    }
    val productService: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }
    val categoryService: CategoryService by lazy {
        retrofit.create(CategoryService::class.java)
    }
    val orderService: OrderService by lazy {
        retrofit.create(OrderService::class.java)
    }
    val customerService: CustomerService by lazy {
        retrofit.create(CustomerService::class.java)
    }
}