package com.student22110006.fashionshop.data.remote;

import com.student22110006.fashionshop.data.model.ApiResponse;
import com.student22110006.fashionshop.data.model.PagedResult;
import com.student22110006.fashionshop.data.model.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {

    @GET("Product/{id}")
    Call<ApiResponse<Product>> getProductById(@Path("id") int id);

    @GET("Product/list")
    Call<ApiResponse<PagedResult<Product>>> getAllProducts(
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );

    @GET("Product/search")
    Call<ApiResponse<PagedResult<Product>>> searchAndFilterProducts(
            @Query("query") String query,             // Từ khóa tìm kiếm
            @Query("brand") String brand,             // Lọc theo brand
            @Query("size") String size,               // Lọc theo size
            @Query("minPrice") Double minPrice,       // Lọc theo giá
            @Query("maxPrice") Double maxPrice,
            @Query("categoryId") Integer categoryId,  // Lọc theo danh mục
            @Query("page") int page,                  // Phân trang
            @Query("pageSize") int pageSize
    );

    @GET("Product/by-category/{categoryId}")
    Call<ApiResponse<List<Product>>> getProductsByCategory(@Path("categoryId") int categoryId);

    @GET("Product/{id}/related")
    Call<ApiResponse<List<Product>>> getRelatedProducts(@Path("id") int id);

}
