package com.student22110006.fashionshop.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
import androidx.appcompat.widget.SwitchCompat
import com.student22110006.fashionshop.ui.MainActivity
import kotlin.random.Random


class LoginActivity : AppCompatActivity() {

    private val accountRepository = AccountRepository()
    private lateinit var backgroundImageView: ImageView
    private lateinit var switchBackground: SwitchCompat
    private var currentBackgroundIndex = 0

    // Các hình nền của giao diện
    private val backgroundImages = listOf(
        R.drawable.top_background1,
        R.drawable.top_background2,
        //R.drawable.top_background3
    )

    override fun onStart() {
        super.onStart()

        val sharedPreferences = getSharedPreferences("FashionShop", MODE_PRIVATE)
        val customerId = sharedPreferences.getString("customerId", null)
        val userEmail = sharedPreferences.getString("email", null)
        val userPassword = sharedPreferences.getString("password", null)

        if (userEmail != null && userPassword != null) {
            // Kiểm tra xem người dùng đã đăng nhập chưa
            val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
            // Nếu người dùng đã đăng nhập, chuyển sang màn hình chính
            if (isLoggedIn) {
                // Người dùng đã đăng nhập, tự động chuyển sang màn hình chính
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Người dùng chưa đăng nhập, thực hiện đăng nhập thông qua email và mật khẩu
                login(userEmail, userPassword)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.decorView.post {
            // Sử dụng WindowInsetsControllerCompat để thiết lập chế độ full screen
            val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
            windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
        }
        // Gắn hình nền
        backgroundImageView = findViewById(R.id.imageView)
        switchBackground = findViewById(R.id.switchBackground)

        // Chọn hình nền ngẫu nhiên khi load app
        currentBackgroundIndex = Random.nextInt(backgroundImages.size) // Random ảnh nền
        backgroundImageView.setImageResource(backgroundImages[currentBackgroundIndex])

        // Lắng nghe sự kiện khi Switch thay đổi
        // Xử lý khi bấm Switch
        switchBackground.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                var newIndex: Int
                do {
                    newIndex = Random.nextInt(backgroundImages.size)
                } while (newIndex == currentBackgroundIndex)

                currentBackgroundIndex = newIndex
                backgroundImageView.setImageResource(backgroundImages[currentBackgroundIndex])
            }
        }

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
        // nếu hiện thị thông báo đăng nhập thành công thì chuyển sang màn hình Home


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
                    if (response.isSuccess) {
                        // Lưu thông tin user vào SharedPreferences
                        val user = Gson().toJson(response.data)

                        // Sau khi đăng nhập thành công
                        val sharedPreferences = getSharedPreferences("FashionShop", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()

                        // Lưu email và password (hãy lưu mật khẩu an toàn nếu cần)
                        editor.putString("customerId", response.data?.customerId?.toString())
                        editor.putString("email", userName)
                        editor.putString("password", password)
                        editor.putBoolean("isLoggedIn", true)
                        editor.apply()

                        // Hiển thị thông báo đăng nhập thành công
                        Toast.makeText(
                            this@LoginActivity,
                            "User ${response.data?.displayName} has logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Chuyển sang màn hình HomeActivity
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
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
