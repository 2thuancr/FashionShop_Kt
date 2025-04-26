package com.student22110006.fashionshop.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;
import com.student22110006.fashionshop.R;
import com.student22110006.fashionshop.adapter.ImageSliderAdapter;
import com.student22110006.fashionshop.adapter.ListProductAdapter;
import com.student22110006.fashionshop.data.model.product.Product;
import com.student22110006.fashionshop.databinding.ActivityProductDetailBinding;
import com.student22110006.fashionshop.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private int productId;
    private Product product;
    private ActivityProductDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo binding thay vì dùng setContentView thông thường
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        // setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        productId = intent.getIntExtra("productId", 0); // 0 là giá trị mặc định

        loadData();
    }

    private void loadData() {
        // Call Service lấy thông tin sản phẩm dựa vào productId

        binding.txtProductName.setText("Nike Sneakers");
        binding.txtNewPrice.setText("₹1,500");
        binding.txtOldPrice.setText("₹2,999");
        binding.txtDiscount.setText("50% Off");
        binding.ratingBar.setRating(4.5f);
        binding.txtDescription.setText("Perhaps the most iconic sneaker of all-time...");

        String[] sizes = {"6 UK", "7 UK", "8 UK", "9 UK", "10 UK"};

        setupViewPager();
        setupSizeChips(sizes);

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
    }

    private void setupSizeChips(String[] sizes) {

        for (String size : sizes) {
            Chip chip = new Chip(this);
            chip.setText(size);
            chip.setCheckable(true);
            chip.setClickable(true);
            chip.setTextColor(getResources().getColor(R.color.color_primary_light, getTheme()));
            chip.setChipBackgroundColorResource(R.color.color_primary_dark);
            binding.chipGroupSizes.addView(chip);
        }
    }

    private void setupSimilarProducts(List<Product> similarProducts) {
        ListProductAdapter adapter = new ListProductAdapter(this, similarProducts);
        binding.recyclerViewSimilar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewSimilar.setAdapter(adapter);
    }

    private void setupListeners() {
        // binding.btnBack.setOnClickListener(v -> finish());
        // Về Home Page
        binding.btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        binding.btnCart.setOnClickListener(v -> Toast.makeText(this, "Cart clicked", Toast.LENGTH_SHORT).show());
        binding.btnGoToCart.setOnClickListener(v -> Toast.makeText(this, "Go to Cart", Toast.LENGTH_SHORT).show());
        binding.btnBuyNow.setOnClickListener(v -> Toast.makeText(this, "Buy Now clicked", Toast.LENGTH_SHORT).show());
    }
}