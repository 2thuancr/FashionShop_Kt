package com.student22110006.fashionshop.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.student22110006.fashionshop.adapter.CategoryAdapter;
import com.student22110006.fashionshop.adapter.ListProductAdapter;
import com.student22110006.fashionshop.data.model.category.Category;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<Category> listCategories = new ArrayList<Category>();
    private ArrayList<Product> listProducts = new ArrayList<Product>();
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // categoryRecyclerView
        final RecyclerView categoriesRecyclerView = binding.categoryRecyclerView;
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        LoadCategoryData();
        CategoryAdapter categoriesAdapter = new CategoryAdapter(this.getContext(), this.listCategories);
        categoriesRecyclerView.setAdapter(categoriesAdapter);

        // featuredProductsRecyclerView
        final RecyclerView featuredProductsRecyclerView = binding.featuredProductsRecyclerView;
        featuredProductsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        LoadFeaturedProductData();
        ListProductAdapter featuredProductsAdapter = new ListProductAdapter(this.getContext(), this.listProducts);
        featuredProductsRecyclerView.setAdapter(featuredProductsAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadCategoryData() {
        // Tạo danh sách danh mục mẫu
        this.listCategories.add(new Category("Áo khoác", "https://product.hstatic.net/200000671183/product/khong_fpt_50be7875479d4710ba4622b8c3702c83_1024x1024.jpg"));
        this.listCategories.add(new Category("Áo Thun", "https://product.hstatic.net/1000360022/product/ao-thun-nam-hoa-tiet-in-xop-noi-luminous-form-regular_202fda29d081498c93fac416891d6633_1024x1024.jpg"));
        this.listCategories.add(new Category("Áo trẻ em", "https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/473659Eud/rabity-kids-fashion-715939.jpg"));
        this.listCategories.add(new Category("Áo nữ", "https://file.hstatic.net/1000284478/file/gigi__1_.jpg"));
        this.listCategories.add(new Category("Áo nam", "https://file.hstatic.net/1000284478/file/artboard_2_f9527c5a59aa4a04b3025612c17b842a.jpg"));
    }

    private void LoadFeaturedProductData() {
        // Thêm sản phẩm vào danh sách
        listProducts.add(new Product(1, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", 120000.0, 20.0, 17, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", "https://product.hstatic.net/200000642007/product/50whs_3atst0153_2_9b6e53b833ee483cb9281843d361a1ed_e2eb29fcebe04276afd08adaf11d4dfd_grande.jpg"));
        listProducts.add(new Product(2, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", 5000000.0, 15.0, 35, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", "https://product.hstatic.net/200000642007/product/50bks_3atst0153_2_7fdcb64c273049c0bc81e02f02b8763c_e1cc238f10104daaa97e606121649107_grande.jpg"));
        listProducts.add(new Product(3, "MLB - Áo thun unisex cổ tròn tay ngắn Basic Coopers", 5000000.0, 0.0, 4, "MLB - Áo thun unisex cổ tròn tay ngắn Basic Coopers", "https://product.hstatic.net/200000642007/product/50ivs_3atsb1153_2_178394b32f8e4b52aa7871a37561a90c_a98589dd19e048cdacc3eedf2921e188_grande.jpg"));
        listProducts.add(new Product(4, "MLB - Áo sweatshirt unisex Basic Gorpcore Woven Piste", 5000000.0, 12.0, 6, "MLB - Áo sweatshirt unisex Basic Gorpcore Woven Piste", "https://product.hstatic.net/200000642007/product/50bks_3amtb0851_2_ca3f7d7fa01b48a5948ccd8b2b5198c6_1634db3329414fe3b01b7ff5b96212c9_grande.jpg"));
        listProducts.add(new Product(5, "MLB - Áo sweatshirt unisex cổ tròn tay dài College T Varsity", 5000000.0, 0.0, 15, "MLB - Áo sweatshirt unisex cổ tròn tay dài College T Varsity", "https://product.hstatic.net/200000642007/product/50crs_3amtv0651_2_e7e58bfad69a4b14a8f5c959513e31d3_d0ab112619dd4e2cb0520ffc1b2794df_grande.jpg"));
    }
}