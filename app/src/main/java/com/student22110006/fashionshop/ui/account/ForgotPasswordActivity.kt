package com.student22110006.fashionshop.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.student22110006.fashionshop.R
import com.student22110006.fashionshop.data.model.account.AccountGenerateOtpRequest
import com.student22110006.fashionshop.data.repository.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordActivity : AppCompatActivity() {

    private val accountRepository = AccountRepository()

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
                generateOtp(email) // Gọi hàm tạo OTP
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun generateOtp(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = AccountGenerateOtpRequest(email)
                Log.d("GenerateOtpRequest", Gson().toJson(request))
                val response = accountRepository.generateOtp(request)

                withContext(Dispatchers.Main) {
                    // Serialize kết quả response (nếu cần log)
                    val jsonResponse = Gson().toJson(response)
                    Log.d("GenerateOtpResponse", jsonResponse)

                    if (response.isSuccess) {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "OTP đã được gửi đến email",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Chuyển sang màn OTP
                        val intent = Intent(this@ForgotPasswordActivity, OtpActivity::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("activity", "forgotPassword")
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "Không thể gửi OTP: ${response.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("GenerateOtpError", e.message ?: "Unknown error")
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Lỗi: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}
