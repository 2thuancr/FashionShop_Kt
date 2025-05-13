package com.student22110006.fashionshop.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.student22110006.fashionshop.R
import com.student22110006.fashionshop.data.model.account.AccountVerifyOtpRequest
import com.student22110006.fashionshop.data.repository.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OtpActivity : AppCompatActivity() {

    private val accountRepository = AccountRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        // Lấy dữ liệu từ Intent
        val email = intent.getStringExtra("email") // Lấy email
        // val username = intent.getStringExtra("username") // Lấy username
        val activity = intent.getStringExtra("activity") // Lấy thông tin Activity

        // Lắng nghe sự kiện quay lại trang đăng nhập
        val tvBackToLogin = findViewById<TextView>(R.id.tvBackToLogin)
        tvBackToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Lắng nghe sự kiện xác minh OTP
        val btnVerifyOtp = findViewById<Button>(R.id.btnVerifyOtp)
        btnVerifyOtp.setOnClickListener {
            val otp = findViewById<EditText>(R.id.etOtp).text.toString().trim()

            if (otp.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show()
            } else {
                if (email != null) {
                    verifyOtp(email, otp, activity ?: "")
                } else {
                    Toast.makeText(this, "Lỗi: Email không hợp lệ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun verifyOtp(email: String, otp: String, activity: String = "") {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = AccountVerifyOtpRequest(otp, email)
                val response = accountRepository.verifyOtp(request)

                withContext(Dispatchers.Main) {
                    val jsonResponse = Gson().toJson(response)
                    Log.d("VerifyOtpResponse", jsonResponse)

                    if (response.isSuccess) {
                        Toast.makeText(this@OtpActivity, "Xác minh thành công", Toast.LENGTH_SHORT)
                            .show()

                        if (activity == "forgotPassword") {
                            // Chuyển sang sang màn hình new password
                            val intent = Intent(this@OtpActivity, NewPasswordActivity::class.java)
                            intent.putExtra("email", email) // Truyền username qua Intent
                            startActivity(intent)
                        } else {
                            // Chuyển sang màn hình tiếp theo (Đăng nhập hoặc thay đổi mật khẩu)
                            val intent = Intent(this@OtpActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@OtpActivity, response.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@OtpActivity, "Lỗi: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
