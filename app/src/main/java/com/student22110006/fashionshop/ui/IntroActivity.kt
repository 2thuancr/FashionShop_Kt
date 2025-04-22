package com.student22110006.fashionshop.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.student22110006.fashionshop.R
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.Intent
import com.student22110006.fashionshop.ui.account.LoginActivity


class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.intro)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Ánh xạ nút "Bắt đầu"
        val startBtn = findViewById<ConstraintLayout>(R.id.startBtn)
        // Xử lý sự kiện click
        startBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Đóng màn hình Intro để không quay lại khi nhấn Back
        }
    }
}