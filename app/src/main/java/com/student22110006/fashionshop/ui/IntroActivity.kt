package com.student22110006.fashionshop.ui

import android.os.Bundle
import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.student22110006.fashionshop.R
import com.student22110006.fashionshop.ui.account.LoginActivity
import kotlinx.coroutines.*

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() // Nếu bạn sử dụng edge-to-edge layout
        setContentView(R.layout.activity_intro)

        // Ánh xạ layout để xử lý padding cho system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.intro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Dùng Coroutine để trì hoãn 3 giây
        GlobalScope.launch {
            delay(3000) // Trì hoãn 3 giây
            val intent = Intent(this@IntroActivity, LoginActivity::class.java)
            startActivity(intent)
            finish() // Đóng màn hình Intro để không quay lại khi nhấn Back
        }
    }
}
