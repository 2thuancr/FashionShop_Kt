package com.student22110006.fashionshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class Login_Activity : AppCompatActivity() {
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

    private fun login(userName: String, password: String) {
        val apiUrl =
            "https://fashionshop-f0hthbhrevbwdtcj.southeastasia-01.azurewebsites.net/api/Account/Login"

        // Dữ liệu JSON
        val jsonBody = """
            {
                "userName": "$userName",
                "password": "$password"
            }
        """.trimIndent()

        // Gọi API trong Coroutine
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = sendPostRequest(apiUrl, jsonBody)
                withContext(Dispatchers.Main) {
                    Log.d("LoginResponse", response)
                    // TODO: Xử lý response tại đây (chuyển màn hình, lưu token, ...)

                    // Thông báo user displayName đã đăng nhập thành công
                    val jsonObject = JSONObject(response)
                    val displayName = jsonObject.getString("displayName")
                    Toast.makeText(this@Login_Activity, "User $displayName has logged in successfully", Toast.LENGTH_SHORT).show()

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("LoginError", e.message ?: "Unknown error")
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
