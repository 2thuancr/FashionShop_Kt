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
import com.student22110006.fashionshop.data.model.account.AccountLoginRequest
import com.student22110006.fashionshop.data.repository.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val accountRepository = AccountRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sử dụng WindowInsetsControllerCompat để thiết lập chế độ full screen
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val etEmail = findViewById<EditText>(R.id.etEmail)
            val etPassword = findViewById<EditText>(R.id.etPassword)

            // Thực hiện login API
            login(etEmail.getText().toString(), etPassword.getText().toString())
        }

        // Lắng nghe sự kiện của TextView để chuyển sang màn hình Register
        val tvRegisterPrompt = findViewById<TextView>(R.id.tvRegisterPrompt)
        tvRegisterPrompt.setOnClickListener {
            // Chuyển tới Register Activity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        // Lắng nghe sự kiện của TextView để chuyển sang màn hình nhập mail
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        tvForgotPassword.setOnClickListener {
            // Chuyển tới ForgotPassword Activity
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        // Lắng nghe sự kiện để chuyển màn hình sang Home


        // Xử lý insets cho màn hình nếu cần
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun login(userName: String, password: String) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = AccountLoginRequest(userName, password)
                val response = accountRepository.login(request)

                withContext(Dispatchers.Main) {

                    // Serialize user object to JSON string
                    val jsonResponse = Gson().toJson(response)
                    Log.d("LoginResponse", jsonResponse)

                    // Nếu login không thành công (isSuccess = false), hiển thị thông báo
                    if (!response.isSuccess) {
                        Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT)
                            .show()
                        return@withContext
                    } else {
                        // Lưu thông tin user vào SharedPreferences
                        val user = Gson().toJson(response.data)
                        val sharedPreferences = getSharedPreferences("FashionShop", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("user", user)
                        editor.apply()

                        // Hiển thị thông báo đăng nhập thành công
                        Toast.makeText(
                            this@LoginActivity,
                            "User ${response.data?.displayName} has logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("LoginError", e.message ?: "Unknown error")
                    Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}
