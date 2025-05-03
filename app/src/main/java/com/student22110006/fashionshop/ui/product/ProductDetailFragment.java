package com.student22110006.fashionshop.ui.product;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.adapter.ImageSliderAdapter;
import com.student22110006.fashionshop.adapter.ListProductAdapter;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.FragmentProductDetailBinding;
import com.student22110006.fashionshop.ui.MainActivity;
import com.student22110006.fashionshop.ui.notifications.NotificationDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailFragment extends Fragment {

    private ProductDetailViewModel mViewModel;
    private int productId;
    private Product product;
    private FragmentProductDetailBinding binding;
    private boolean isDescriptionExpanded = false;

    public static ProductDetailFragment newInstance() {
        return new ProductDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Khởi tạo binding thay vì dùng setContentView
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Cài đặt giao diện bên trong Fragment
        loadData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });
    }

    private void loadData() {
        // Call Service lấy thông tin sản phẩm dựa vào productId

        binding.txtProductName.setText("Nike Sneakers");
        binding.txtNewPrice.setText("1,500");
        binding.txtOldPrice.setText("2,999");
        binding.txtDiscount.setText("50% Off");
        binding.ratingBar.setRating(4.5f);
        binding.txtDescription.setText("Perhaps the most iconic sneaker of all-time...");

        String[] sizes = {"6 UK", "7 UK", "8 UK", "9 UK", "10 UK"};

        setupViewPager();
        setupSizeChips(sizes);
        setupSeeMore();

        List<Product> similarProducts = getSimilarProducts(productId);
        setupSimilarProducts(similarProducts);

        setupListeners();
    }

    private List<Product> getSimilarProducts(int productId) {
        List<Product> similarProducts = new ArrayList<>();
        similarProducts.add(new Product(1, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", 120000.0, 20.0, 17, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", "https://product.hstatic.net/200000642007/product/50whs_3atst0153_2_9b6e53b833ee483cb9281843d361a1ed_e2eb29fcebe04276afd08adaf11d4dfd_grande.jpg"));
        similarProducts.add(new Product(2, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", 5000000.0, 15.0, 35, "MLB - Áo thun unisex cổ tròn tay ngắn Star Big Lux", "https://product.hstatic.net/200000642007/product/50bks_3atst0153_2_7fdcb64c273049c0bc81e02f02b8763c_e1cc238f10104daaa97e606121649107_grande.jpg"));
        similarProducts.add(new Product(3, "MLB - Áo thun unisex cổ tròn tay ngắn Basic Coopers", 5000000.0, 0.0, 4, "MLB - Áo thun unisex cổ tròn tay ngắn Basic Coopers", "https://product.hstatic.net/200000642007/product/50ivs_3atsb1153_2_178394b32f8e4b52aa7871a37561a90c_a98589dd19e048cdacc3eedf2921e188_grande.jpg"));
        similarProducts.add(new Product(4, "MLB - Áo sweatshirt unisex Basic Gorpcore Woven Piste", 5000000.0, 12.0, 6, "MLB - Áo sweatshirt unisex Basic Gorpcore Woven Piste", "https://product.hstatic.net/200000642007/product/50bks_3amtb0851_2_ca3f7d7fa01b48a5948ccd8b2b5198c6_1634db3329414fe3b01b7ff5b96212c9_grande.jpg"));
        similarProducts.add(new Product(5, "MLB - Áo sweatshirt unisex cổ tròn tay dài College T Varsity", 5000000.0, 0.0, 15, "MLB - Áo sweatshirt unisex cổ tròn tay dài College T Varsity", "https://product.hstatic.net/200000642007/product/50crs_3amtv0651_2_e7e58bfad69a4b14a8f5c959513e31d3_d0ab112619dd4e2cb0520ffc1b2794df_grande.jpg"));

        return similarProducts;
    }

    private void setupViewPager() {
        List<String> imageUrls = new ArrayList<>();
        imageUrls.add("https://product.hstatic.net/1000284478/product/q0a_umv450077_1_3969888417cc41d1a03c9df648ecf376_large.jpg");
        imageUrls.add("https://product.hstatic.net/1000284478/product/q0a_umv450077_2_291484de3b9040568f8c2ec80168a035_large.jpg");
        imageUrls.add("https://product.hstatic.net/1000284478/product/q0a_umv450078_1_e9eb85bccc0a462e9e9b11cd32539ce1_large.jpg");
        imageUrls.add("https://product.hstatic.net/1000284478/product/q0a_umv450078_2_6ca6cee9054b4a5b9ef1dd6860fef351_large.jpg");

        ImageSliderAdapter adapter = new ImageSliderAdapter(imageUrls);
        binding.viewPagerImages.setAdapter(adapter);
        binding.viewPagerImagesIndicator.setViewPager2(binding.viewPagerImages);
    }

    private Resources.Theme getTheme() {
        if (getContext() == null) {
            return null;
        }
        return getContext().getTheme();
    }

    private void setupSizeChips(String[] sizes) {
        for (String size : sizes) {
            Chip chip = new Chip(getContext());
            chip.setText(size);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setTextColor(getResources().getColor(R.color.color_primary_light, getTheme()));
            chip.setChipBackgroundColorResource(R.color.color_primary_dark);
            binding.chipGroupSizes.addView(chip);
        }
    }

    private void setupSimilarProducts(List<Product> similarProducts) {
        ListProductAdapter adapter = new ListProductAdapter(getContext(), similarProducts, this::showProductDetails);
        binding.recyclerViewSimilar.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewSimilar.setAdapter(adapter);
    }

    private void setupListeners() {
        binding.btnAddToCart.setOnClickListener(v -> Toast.makeText(getContext(), "Add to Cart clicked", Toast.LENGTH_SHORT).show());
        binding.btnBuyNow.setOnClickListener(v -> Toast.makeText(getContext(), "Buy Now clicked", Toast.LENGTH_SHORT).show());
    }

    private void setupSeeMore() {
        binding.btnSeeMore.setOnClickListener(v -> {
            if (isDescriptionExpanded) {
                // Collapse
                binding.txtDescription.setMaxLines(3);
                binding.txtDescription.setEllipsize(TextUtils.TruncateAt.END);
                binding.btnSeeMore.setText("See More");
            } else {
                // Expand
                binding.txtDescription.setMaxLines(Integer.MAX_VALUE);
                binding.txtDescription.setEllipsize(null);
                binding.btnSeeMore.setText("See Less");
            }
            isDescriptionExpanded = !isDescriptionExpanded;
        });
    }

    private void showProductDetails(Product product) {
        // Lấy NavController từ NavHostFragment
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        // Điều hướng sang navigation_product_detail fragment
        navController.navigate(R.id.navigation_product_detail);
    }
}