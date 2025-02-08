package com.student22110006.fashionshop.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.gson.Gson
import com.student22110006.fashionshop.R
import com.student22110006.fashionshop.data.model.account.AccountRegisterRequest
import com.student22110006.fashionshop.data.repository.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private val accountRepository = AccountRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sử dụng WindowInsetsControllerCompat để thiết lập chế độ full screen
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())  // Ẩn thanh trạng thái
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())  // Ẩn thanh điều hướng

        setContentView(R.layout.activity_register)

        // Chuyển sang màn hình đăng nhập
        val tvLoginPrompt = findViewById<TextView>(R.id.tvLoginPrompt)
        tvLoginPrompt.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Xử lý insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Lắng nghe sự kiện nút đăng ký
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val phoneNumber = findViewById<EditText>(R.id.etPhoneNumber).text.toString().trim()
            val fullName = findViewById<EditText>(R.id.etFullName).text.toString().trim()
            val username = findViewById<EditText>(R.id.etUsername).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString().trim()

            // Kiểm tra các trường dữ liệu
            if (email.isEmpty() || phoneNumber.isEmpty() || fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT)
                    .show()
            } else {
                register(email, phoneNumber, fullName, username, password)
            }
        }
    }

    private fun register(
        email: String,
        phoneNumber: String,
        fullName: String,
        username: String,
        password: String
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = AccountRegisterRequest(
                    fullName,
                    phoneNumber,
                    email,
                    null, // firebaseId
                    null, // DoB
                    null, // address
                    username,
                    password
                )
                val response = accountRepository.register(request)

                withContext(Dispatchers.Main) {

                    val jsonResponse = Gson().toJson(response)
                    Log.d("RegisterResponse", jsonResponse)

                    // Nếu đăng ký thành công (isSuccess = true), hiển thị thông báo
                    if (response.isSuccess) {

                        Toast.makeText(
                            this@RegisterActivity,
                            "User $fullName has registered successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Chuyển sang màn OTP Activity
                        val intent = Intent(this@RegisterActivity, OtpActivity::class.java)
                        intent.putExtra("email", email)  // Truyền email qua Intent
                        intent.putExtra("username", username)  // Truyền username qua Intent
                        // Truyền thông tin Activity hiện tại qua Intent
                        intent.putExtra("activity", "register")
                        startActivity(intent)  // Mở màn hình OTP
                    } else {
                        Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("RegisterError", e.message ?: "Unknown error")
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration failed: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
