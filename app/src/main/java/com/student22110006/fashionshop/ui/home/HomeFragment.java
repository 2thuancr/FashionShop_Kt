package com.student22110006.fashionshop.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.adapter.CategoryAdapter;
import com.student22110006.fashionshop.adapter.ImageSliderAdapter;
import com.student22110006.fashionshop.adapter.ListProductAdapter;
import com.student22110006.fashionshop.data.model.category.Category;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.data.repository.ProductRepository;
import com.student22110006.fashionshop.databinding.FragmentHomeBinding;
import com.student22110006.fashionshop.ui.account.LoginActivity;
import com.student22110006.fashionshop.ui.search.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private final ArrayList<Category> listCategories = new ArrayList<Category>();
    private final ArrayList<Product> listProducts = new ArrayList<Product>();
    private FragmentHomeBinding binding;
    private SearchViewModel searchViewModel;
    private ListProductAdapter featuredProductsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        // Lắng nghe LiveData sản phẩm từ ViewModel
        searchViewModel.getProductList().observe(getViewLifecycleOwner(), products -> {
            if (binding.featuredProductsRecyclerView.getAdapter() == null) {
                featuredProductsAdapter = new ListProductAdapter(requireContext(), products, this::showProductDetails);
                binding.featuredProductsRecyclerView.setAdapter(featuredProductsAdapter);
            } else {
                featuredProductsAdapter.updateProducts(products);
            }
        });

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView imgHome = root.findViewById(R.id.imgMenu);
        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        setupViewPager();
        setupCategories();
        setupFeaturedProducts();

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

    private void setupViewPager() {
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://file.hstatic.net/1000284478/file/25_1920x700_96fa105db37a40a29f055cd5d09869dd.jpg");
        imageUrls.add("https://file.hstatic.net/200000642007/file/1920x640_vn_723159492c914c908dabd24c00da338c.jpg");
        imageUrls.add("https://file.hstatic.net/1000284478/file/owen_1920x700_240be40b27fe41efa37472acf3259c19.jpg");
        imageUrls.add("https://file.hstatic.net/1000284478/file/1920x700_3.jpg");

        ImageSliderAdapter adapter = new ImageSliderAdapter(imageUrls);
        binding.viewPagerImages.setAdapter(adapter);
        binding.viewPagerImagesIndicator.setViewPager2(binding.viewPagerImages);
    }

    private void setupCategories() {
        final RecyclerView categoriesRecyclerView = binding.categoryRecyclerView;
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        LoadCategoryData();
        CategoryAdapter categoriesAdapter = new CategoryAdapter(this.getContext(), this.listCategories);
        categoriesRecyclerView.setAdapter(categoriesAdapter);

        final TextView tvViewAllCategories = binding.tvViewAllCategories;
        tvViewAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Xem tất cả danh mục", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupFeaturedProducts() {
        final RecyclerView featuredProductsRecyclerView = binding.featuredProductsRecyclerView;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        // GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false);
        featuredProductsRecyclerView.setLayoutManager(linearLayoutManager);

        LoadFeaturedProductData();
        featuredProductsAdapter = new ListProductAdapter(this.getContext(), this.listProducts, this::showProductDetails, 180);
        featuredProductsRecyclerView.setAdapter(featuredProductsAdapter);

        final TextView tvViewAllProducts = binding.tvViewAllProducts;
        tvViewAllProducts.setOnClickListener(v -> Toast.makeText(getContext(), "Xem tất cả sản phẩm", Toast.LENGTH_SHORT).show());
    }

    private void showProductDetails(Product product) {
        // Lấy NavController từ NavHostFragment
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putInt("productId", product.getId());

        // Điều hướng sang navigation_product_detail fragment
        navController.navigate(R.id.navigation_product_detail, bundle);
    }

    private void showPopupMenu(View anchor) {
        androidx.appcompat.widget.PopupMenu popup = new androidx.appcompat.widget.PopupMenu(requireContext(), anchor);
        popup.getMenuInflater().inflate(R.menu.home_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            if (id == R.id.menu_profile) {
                // Điều hướng đến ProfileFragment
                Toast.makeText(getContext(), "Thông tin cá nhân", Toast.LENGTH_SHORT).show();
                // Thay thế HomeFragment bằng ProfileFragment mà không đè lên nhau
                navController.navigate(R.id.navigation_profile);
                return true;
            } else if (id == R.id.menu_orders) {
                Toast.makeText(getContext(), "Đơn hàng của tôi", Toast.LENGTH_SHORT).show();
                // Chuyển đến OrderHistoryFragment
                navController.navigate(R.id.navigation_order_history);
                return true;
            } else if (id == R.id.menu_logout) {
                // Xử lý đăng xuất
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("FashionShop", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false); // Đặt lại trạng thái đăng nhập
                editor.remove("email"); // Xóa email
                editor.remove("password"); // Xóa mật khẩu
                editor.apply();

                // Chuyển về màn hình đăng nhập
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();  // Đảm bảo không quay lại màn hình chính sau khi đăng xuất

                Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        popup.show();
    }
}