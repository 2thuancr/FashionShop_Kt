package com.student22110006.fashionshop.data.repository;

import com.student22110006.fashionshop.data.model.ApiResponse;
import com.student22110006.fashionshop.data.model.PagedResult;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.data.remote.ApiClient;
import com.student22110006.fashionshop.data.remote.ProductService;

import java.util.List;

import retrofit2.Call;

public class ProductRepository {

    private final ProductService api;

    public ProductRepository() {
        this.api = ApiClient.INSTANCE.getProductService();
    }

    public Call<ApiResponse<PagedResult<Product>>> getAllProducts(int page, int pageSize) {
        return api.getAllProducts(page, pageSize);
    }

    public Call<ApiResponse<Product>> getProductById(int id) {
        return api.getProductById(id);
    }

    public Call<ApiResponse<PagedResult<Product>>> searchAndFilterProducts(String query, String brand, String size,
                                                                           Double minPrice, Double maxPrice,
                                                                           Integer categoryId,
                                                                           int page, int pageSize) {
        return api.searchAndFilterProducts(query, brand, size, minPrice, maxPrice, categoryId, page, pageSize);
    }

    public Call<ApiResponse<List<Product>>> getProductsByCategory(int categoryId) {
        return api.getProductsByCategory(categoryId);
    }

    public Call<ApiResponse<List<Product>>> getRelatedProducts(int id) {
        return api.getRelatedProducts(id);
    }
}

