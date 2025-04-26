package com.student22110006.fashionshop.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.student22110006.fashionshop.R
import com.student22110006.fashionshop.adapter.ListProductAdapter
import com.student22110006.fashionshop.data.model.product.Product
import androidx.viewpager2.widget.ViewPager2;
import android.os.Handler;
import android.os.Looper
import android.widget.LinearLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.student22110006.fashionshop.adapter.BannerAdapter
import com.student22110006.fashionshop.adapter.CategoryAdapter
import com.student22110006.fashionshop.data.model.category.Category
import com.student22110006.fashionshop.ui.shared.SpaceItemDecoration

class HomeActivity : AppCompatActivity() {

    private lateinit var rv_list_product: RecyclerView;
    private var listProducts: ArrayList<Product> = ArrayList<Product>();
    private lateinit var adapter: ListProductAdapter;

    // Khai báo các thành phần giao diện Banner
    private lateinit var viewPager: ViewPager2
    private val imageList = listOf(
        R.drawable.banner1, R.drawable.banner2, R.drawable.banner3
    )
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    // Khai báo các thành phần giao diện Category
    private lateinit var rvCategories: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryList = mutableListOf<Category>()

    private lateinit var homeBtn: LinearLayout
    private lateinit var profileBtn: LinearLayout
    private lateinit var fabCart: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Ánh xạ các thành phần giao diện Banner
        viewPager = findViewById(R.id.viewPagerBanner)
        viewPager.adapter = BannerAdapter(imageList)
        // Tự động chạy banner
        handler.postDelayed(autoScrollRunnable, 2000)

        // Ánh xạ các thành phần giao diện
        rv_list_product = findViewById(R.id.rv_list_product);

        // Ánh xạ các thành phần giao diện Category
        rvCategories = findViewById(R.id.rv_categories)

        // Ánh xạ các thành phần giao diện Bottom Navigation
        homeBtn = findViewById(R.id.homeBtn)
        profileBtn = findViewById(R.id.profileBtn)
        fabCart = findViewById(R.id.fab)

        // Tạo danh sách danh mục mẫu
        categoryList.add(
            Category(
                "Áo khoác",
                "https://product.hstatic.net/200000671183/product/khong_fpt_50be7875479d4710ba4622b8c3702c83_1024x1024.jpg"
            )
        );
        categoryList.add(
            Category(
                "Áo Thun",
                "https://product.hstatic.net/1000360022/product/ao-thun-nam-hoa-tiet-in-xop-noi-luminous-form-regular_202fda29d081498c93fac416891d6633_1024x1024.jpg"
            )
        );
        categoryList.add(
            Category(
                "Áo trẻ em",
                "https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/473659Eud/rabity-kids-fashion-715939.jpg"
            )
        );
        categoryList.add(
            Category(
                "Áo nữ",
                "https://file.hstatic.net/1000284478/file/gigi__1_.jpg"
            )
        );
        categoryList.add(
            Category(
                "Áo nam",
                "https://file.hstatic.net/1000284478/file/artboard_2_f9527c5a59aa4a04b3025612c17b842a.jpg"
            )
        );
        // Khởi tạo danh sách sản phẩm
        // Thêm sản phẩm vào danh sách
        listProducts.add(
            Product(
                1,
                "Áo",
                120000.0,
                12.0,
                56,
                "Áo đẹp",
                "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"
            )
        );
        listProducts.add(
            Product(
                2,
                "Quần",
                5000000.0,
                0.0,
                57,
                "Áo đẹp",
                "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"
            )
        );
        listProducts.add(
            Product(
                1,
                "Quần",
                5000000.0,
                30.0,
                67,
                "Áo đẹp",
                "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"
            )
        );
        listProducts.add(
            Product(
                2,
                "Quần",
                5000000.0,
                70.0,
                9,
                "Áo đẹp",
                "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"
            )
        );
        listProducts.add(
            Product(
                2,
                "Quần",
                5000000.0,
                0.0,
                7,
                "Áo đẹp",
                "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"
            )
        );
        listProducts.add(
            Product(
                2,
                "Quần",
                5000000.0,
                12.0,
                5,
                "Áo đẹp",
                "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"
            )
        );
        listProducts.add(
            Product(
                2,
                "Quần",
                5000000.0,
                0.0,
                6,
                "Áo đẹp",
                "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"
            )
        );
        listProducts.add(
            Product(
                2,
                "Quần",
                5000000.0,
                30.0,
                5,
                "Áo đẹp",
                "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"
            )
        );
        listProducts.add(
            Product(
                2,
                "Quần",
                5000000.0,
                10.0,
                6,
                "Áo đẹp",
                "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"
            )
        );
        listProducts.add(
            Product(
                2,
                "Quần",
                5000000.0,
                0.0,
                7,
                "Áo đẹp",
                "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"
            )
        );


        // Khởi tạo adapter
        adapter = ListProductAdapter(this, listProducts);
        // Thiết lập adapter cho RecyclerView
        rv_list_product.setAdapter(adapter);
        var numberOfColumns = 2;
        var gridLayoutManager = GridLayoutManager(this, numberOfColumns);
        // var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list_product.setLayoutManager(gridLayoutManager);

        // Cấu hình RecyclerView
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategories.layoutManager = layoutManager
        categoryAdapter = CategoryAdapter(this, categoryList)
        rvCategories.adapter = categoryAdapter

        rv_list_product.addItemDecoration(
            SpaceItemDecoration(
                200
            )
        ) // Khoảng cách giữa các item

    }

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            if (currentPage == imageList.size) {
                currentPage = 0
            }
            viewPager.setCurrentItem(currentPage++, true)
            handler.postDelayed(this, 2000) // Chạy lại sau 2 giây
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(autoScrollRunnable) // Xóa handler khi thoát Activity
    }
}