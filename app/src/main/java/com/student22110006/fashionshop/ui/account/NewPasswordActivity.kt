package com.student22110006.fashionshop.ui.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.student22110006.fashionshop.R
import com.student22110006.fashionshop.data.model.account.AccountChangePasswordRequest
import com.student22110006.fashionshop.data.repository.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewPasswordActivity : AppCompatActivity() {
    private val accountRepository = AccountRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_password)

        // Lấy email từ Intent
        val email = intent.getStringExtra("email") ?: ""
        // set email vào EditText
        val etEmail = findViewById<EditText>(R.id.etEmail)
        etEmail.setText(email)

        // Lấy ra mật khẩu mới từ EditText
        val etNewPassword = findViewById<EditText>(R.id.etNewPassword)
        val newPassword = etNewPassword.text.toString().trim()

        // Lấy ra mật khẩu xác nhận từ EditText
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val confirmPassword = etConfirmPassword.text.toString().trim()

        // Sự kiện click button xác nhận mật khẩu mới 
        val btnConfirm = findViewById<Button>(R.id.btnChangePassword)
        btnConfirm.setOnClickListener {
            // Xử lý logic xác nhận mật khẩu mới
            confirmPassword(newPassword, confirmPassword)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
    }

    private fun confirmPassword(newPassword: String, confirmPassword: String) {
        // Xử lý logic xác nhận mật khẩu mới
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu mới và xác nhận mật khẩu", Toast.LENGTH_SHORT).show()
        } else if (newPassword != confirmPassword) {
            Toast.makeText(this, "Mật khẩu mới và xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show()
        } else {
            // Lấy email từ Intent
            val email = intent.getStringExtra("email") ?: ""
            // Gọi hàm đổi mật khẩu mới
            changePassword(email, newPassword)
        }
    }

    // Đổi mật khẩu mới
    private fun changePassword(email: String, newPassword: String) {
        // Xử lý logic đổi mật khẩu mới
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = AccountChangePasswordRequest(email, newPassword)
                val response = accountRepository.changePassword(request)

                withContext(Dispatchers.Main) {
                    val jsonResponse = Gson().toJson(response)
                    Log.d("ChangePasswordResponse", jsonResponse)

                    if (response.isSuccess) {
                        Toast.makeText(this@NewPasswordActivity, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()

                        // Chuyển sang man hình đăng nhập
                        val intent = Intent(this@NewPasswordActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@NewPasswordActivity, response.message, Toast.LENGTH_SHORT).show()
                    }

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@NewPasswordActivity, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

}