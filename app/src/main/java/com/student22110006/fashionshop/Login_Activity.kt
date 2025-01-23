package com.student22110006.fashionshop

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import android.widget.TextView

class Login_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sử dụng WindowInsetsControllerCompat để thiết lập chế độ full screen
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

        setContentView(R.layout.activity_login)

        // Lắng nghe sự kiện của TextView để chuyển sang màn hình Register
        val tvRegisterPrompt = findViewById<TextView>(R.id.tvRegisterPrompt)
        tvRegisterPrompt.setOnClickListener {
            // Chuyển tới Register Activity
            val intent = Intent(this, Register_Activity::class.java)
            startActivity(intent)
        }

        // Xử lý insets cho màn hình nếu cần
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
