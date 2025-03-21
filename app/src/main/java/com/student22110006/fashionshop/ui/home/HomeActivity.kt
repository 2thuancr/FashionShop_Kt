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

class HomeActivity : AppCompatActivity() {

    private lateinit var rv_list_product : RecyclerView;
    private var listProducts : ArrayList<Product> = ArrayList<Product>();
    private lateinit var adapter : ListProductAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Ánh xạ các thành phần giao diện
        rv_list_product = findViewById(R.id.rv_list_product);


        // Khởi tạo danh sách sản phẩm
        // Thêm sản phẩm vào danh sách
        listProducts.add(Product(1, "Áo", 120000.0, "Áo đẹp", "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"));
        listProducts.add(Product(2, "Quần", 5000000.0, "Áo đẹp", "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"));
        listProducts.add(Product(1, "Quần", 5000000.0, "Áo đẹp", "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"));
        listProducts.add(Product(2, "Quần", 5000000.0, "Áo đẹp", "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"));
        listProducts.add(Product(2, "Quần", 5000000.0, "Áo đẹp", "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"));
        listProducts.add(Product(2, "Quần", 5000000.0, "Áo đẹp", "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"));
        listProducts.add(Product(2, "Quần", 5000000.0, "Áo đẹp", "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"));
        listProducts.add(Product(2, "Quần", 5000000.0, "Áo đẹp", "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"));
        listProducts.add(Product(2, "Quần", 5000000.0, "Áo đẹp", "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"));
        listProducts.add(Product(2, "Quần", 5000000.0, "Áo đẹp", "https://bizweb.dktcdn.net/100/446/974/products/ao-thun-mlb-new-era-heavy-cotton-new-york-yankees-black-13086578-1.jpg"));


        // Khởi tạo adapter
        adapter = ListProductAdapter(this, listProducts);
        // Thiết lập adapter cho RecyclerView
        rv_list_product.setAdapter(adapter);
        var numberOfColumns = 2;
        var gridLayoutManager = GridLayoutManager(this, numberOfColumns);
       // var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list_product.setLayoutManager(gridLayoutManager);
    }


}