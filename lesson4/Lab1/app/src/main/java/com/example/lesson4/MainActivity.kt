package com.example.lesson4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    // Khai báo các View theo ID trong layout Đăng nhập
    private lateinit var etUsername: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main) // Sử dụng layout Đăng nhập

        // Ánh xạ các View
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        // Thiết lập sự kiện click cho nút LOGIN
        btnLogin.setOnClickListener {
            handleLogin()
        }

        // Cấu hình EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleLogin() {
        // Lấy dữ liệu và loại bỏ khoảng trắng thừa
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Kiểm tra dữ liệu đầu vào
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ Tên đăng nhập và Mật khẩu.", Toast.LENGTH_SHORT).show()
            return
        }

        // Logic Đăng nhập
        val loginSuccess: Boolean

        // Đăng nhập thành công nếu username là "admin" và password là "123"
        if (username == "admin" && password == "123") {
            loginSuccess = true
        } else {
            loginSuccess = false
        }

        // Chuyển sang ResultActivity
        val intent = Intent(this, LoginResult::class.java)

        // Đính kèm kết quả đăng nhập và tên người dùng vào Intent
        intent.putExtra("LOGIN_STATUS", loginSuccess)
        intent.putExtra("USERNAME", username)

        startActivity(intent)

        // Xóa nội dung input sau khi chuyển màn hình
        etUsername.setText("")
        etPassword.setText("")
    }
}