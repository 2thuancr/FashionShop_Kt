package com.student22110006.fashionshop.data.repository

import com.student22110006.fashionshop.data.model.ApiResponse
import com.student22110006.fashionshop.data.model.PagedResult
import com.student22110006.fashionshop.data.model.product.Product
import com.student22110006.fashionshop.data.model.product.ProductGetAllRequest
import com.student22110006.fashionshop.data.model.product.ProductSearchRequest
import com.student22110006.fashionshop.data.remote.ApiClient

class ProductRepository {
    private val api = ApiClient.productService;

    suspend fun getAll(request: ProductGetAllRequest): ApiResponse<PagedResult<Product>> {
        return api.getAll(request)
    }

    suspend fun getProductById(id: Int): ApiResponse<Product> {
        return api.getProductById(id)
    }

    suspend fun search(
        request: ProductSearchRequest
    ): ApiResponse<PagedResult<Product>> {
        return api.searchAndFilterProducts(
            request
        )
    }

    suspend fun getProductsByCategory(categoryId: Int): ApiResponse<List<Product>> {
        return api.getProductsByCategory(categoryId)
    }

    suspend fun getRelatedProducts(id: Int): ApiResponse<List<Product>> {
        return api.getRelatedProducts(id)
    }
}