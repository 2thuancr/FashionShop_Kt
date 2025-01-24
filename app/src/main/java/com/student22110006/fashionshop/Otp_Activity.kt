package com.student22110006.fashionshop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class OTP_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        // Lắng nghe sự kiện quay lại trang đăng nhập
        val tvBackToLogin = findViewById<TextView>(R.id.tvBackToLogin)
        tvBackToLogin.setOnClickListener {
            val intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
        }

        // Lắng nghe sự kiện xác minh OTP
        val btnVerifyOtp = findViewById<Button>(R.id.btnVerifyOtp)
        btnVerifyOtp.setOnClickListener {
            val otp = findViewById<EditText>(R.id.etOtp).text.toString().trim()

            if (otp.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show()
            } else {
                verifyOtp(otp)
            }
        }
    }

    private fun verifyOtp(otp: String) {
        val apiUrl = "https://your-api-endpoint.com/api/VerifyOtp"

        val jsonBody = """
            {
                "otp": "$otp"
            }
        """.trimIndent()

        // Gọi API trong coroutine
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = sendPostRequest(apiUrl, jsonBody)
                withContext(Dispatchers.Main) {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.getBoolean("success")) {
                        Toast.makeText(this@OTP_Activity, "Xác minh thành công", Toast.LENGTH_SHORT).show()

                        // Chuyển sang màn hình tiếp theo (Đăng nhập hoặc thay đổi mật khẩu)
                        val intent = Intent(this@OTP_Activity, Login_Activity::class.java)
                        startActivity(intent)
                    } else {
                        val errorMessage = jsonObject.getString("message")
                        Toast.makeText(this@OTP_Activity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@OTP_Activity, "Lỗi: ${e.message}", Toast.LENGTH_LONG).show()
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
        connection.connectTimeout = 10000 // Timeout
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
