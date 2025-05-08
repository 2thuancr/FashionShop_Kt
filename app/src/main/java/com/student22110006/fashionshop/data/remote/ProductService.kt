package com.student22110006.fashionshop.data.remote

import com.student22110006.fashionshop.data.model.ApiResponse
import com.student22110006.fashionshop.data.model.PagedResult
import com.student22110006.fashionshop.data.model.product.Product
import com.student22110006.fashionshop.data.model.product.ProductGetAllRequest
import com.student22110006.fashionshop.data.model.product.ProductSearchRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    @GET("Product/{id}")
    suspend fun getProductById(@Path("id") id: Int): ApiResponse<Product>

    @POST("Product/list")
    suspend fun getAll(
        @Body() request: ProductGetAllRequest
    ): ApiResponse<PagedResult<Product>>

    @POST("Product/search")
    suspend fun searchAndFilterProducts(
        @Body() request: ProductSearchRequest
    ): ApiResponse<PagedResult<Product>>

    @GET("Product/by-category/{categoryId}")
    suspend fun getProductsByCategory(@Path("categoryId") categoryId: Int): ApiResponse<List<Product>>

    @GET("Product/{id}/related")
    suspend fun getRelatedProducts(@Path("id") id: Int): ApiResponse<List<Product>>
}