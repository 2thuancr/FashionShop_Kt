package com.student22110006.fashionshop.data.remote;

import com.student22110006.fashionshop.data.model.ApiResponse;
import com.student22110006.fashionshop.data.model.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {

    @GET("Product/{id}")
    Call<ApiResponse<Product>> getProductById(@Path("id") int id);

    @GET("Product")
    Call<ApiResponse<List<Product>>> getAllProducts();

    @GET("Product/Search")
    Call<ApiResponse<List<Product>>> searchProducts(@Query("query") String query);

    @GET("Product/Filter")
    Call<ApiResponse<List<Product>>> filterProducts(
            @Query("brand") String brand,
            @Query("size") String size,
            @Query("minPrice") Double minPrice,
            @Query("maxPrice") Double maxPrice,
            @Query("categoryId") Integer categoryId
    );
}
