package com.student22110006.fashionshop.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.student22110006.fashionshop.R

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        // Lắng nghe sự kiện của TextView để chuyển sang màn hình đăng nhập
        val tvLoginPrompt = findViewById<TextView>(R.id.tvLoginPrompt)
        tvLoginPrompt.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Lắng nghe sự kiện của TextView để chuyển sang màn hình xác thực OTP
        val tvVerifyPrompt = findViewById<TextView>(R.id.btnGenerateOtp)
        tvVerifyPrompt.setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            // Kiểm tra người dùng nhập email chưa
            if (email.isEmpty()) {
                // Hiển thị thông báo nếu chưa nhập email
                Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show()
            } else {
                // Chuyển sang màn OTP Activity
                val intent = Intent(this@ForgotPasswordActivity, OtpActivity::class.java)
                intent.putExtra("email", email) // Truyền email qua Intent
                // Truyền thông tin Activity hiện tại qua Intent
                intent.putExtra("activity", "forgotPassword")
                startActivity(intent) // Mở màn hình OTP
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
