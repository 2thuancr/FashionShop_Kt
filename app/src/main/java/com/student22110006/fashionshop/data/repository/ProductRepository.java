package com.student22110006.fashionshop.data.repository;

import com.student22110006.fashionshop.data.model.ApiResponse;
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

    public Call<ApiResponse<List<Product>>> getAllProducts() {
        return api.getAllProducts();
    }

    public Call<ApiResponse<Product>> getProductById(int id) {
        return api.getProductById(id);
    }

    public Call<ApiResponse<List<Product>>> searchProducts(String query) {
        return api.searchProducts(query);
    }

    public Call<ApiResponse<List<Product>>> filterProducts(String brand, String size, Double minPrice, Double maxPrice, Integer categoryId) {
        return api.filterProducts(brand, size, minPrice, maxPrice, categoryId);
    }
}
