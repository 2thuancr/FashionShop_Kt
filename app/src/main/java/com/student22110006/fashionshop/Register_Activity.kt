package com.student22110006.fashionshop

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class Register_Activity : AppCompatActivity() {

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
            val intent = Intent(this, Login_Activity::class.java)
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
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            } else {
                register(email, phoneNumber, fullName, username, password)
            }
        }
    }

    private fun register(
        email: String,
        phoneNumber: String,
        fullname: String,
        username: String,
        password: String
    ) {
        val apiUrl = "https://fashionshop-f0hthbhrevbwdtcj.southeastasia-01.azurewebsites.net/api/Account/Register"

        val jsonBody = """
        {
            "email": "$email",
            "phoneNumber": "$phoneNumber",
            "fullName": "$fullname",
            "userName": "$username",
            "password": "$password"  
        }
    """.trimIndent()

        // Gọi API trong coroutine
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = sendPostRequest(apiUrl, jsonBody)
                withContext(Dispatchers.Main) {
                    Log.d("RegisterResponse", response)

                    // Xử lý phản hồi từ server
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("isSuccess")) {

                        Toast.makeText(this@Register_Activity, "User $fullname has registered successfully", Toast.LENGTH_SHORT).show()


                        // Chuyển sang màn OTP Activity
                        val intent = Intent(this@Register_Activity, OTP_Activity::class.java)
                        intent.putExtra("email", email)  // Truyền email qua Intent
                        intent.putExtra("username", username)  // Truyền username qua Intent
                        startActivity(intent)  // Mở màn hình OTP
                    } else {
                        val errorMessage = jsonObject.getString("message")
                        Toast.makeText(this@Register_Activity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("RegisterError", e.message ?: "Unknown error")
                    Toast.makeText(this@Register_Activity, "Registration failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun sendPostRequest(apiUrl: String, jsonBody: String): String {
        val url = URL(apiUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("accept", "*/*")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true
        connection.connectTimeout = 10000 // 10 giây timeout
        connection.readTimeout = 10000

        // Ghi dữ liệu JSON vào output stream
        connection.outputStream.use { outputStream ->
            outputStream.write(jsonBody.toByteArray())
            outputStream.flush()
        }

        // Đọc kết quả trả về từ server
        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            return connection.inputStream.bufferedReader().use { it.readText() }
        } else {
            val error = connection.errorStream?.bufferedReader()?.use { it.readText() }
            throw Exception("HTTP error $responseCode: $error")
        }
    }
}
