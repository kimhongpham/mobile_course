package com.example.lesson4

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast

class LoginResult : AppCompatActivity() {
    private lateinit var tvResultTitle: TextView
    private lateinit var tvResultMessage: TextView
    private lateinit var btnContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_result)

        // Ánh xạ các View
        tvResultTitle = findViewById(R.id.tvResultTitle)
        tvResultMessage = findViewById(R.id.tvResultMessage)
        btnContinue = findViewById(R.id.btnContinue)

        // Nhận dữ liệu từ Intent
        val loginSuccess = intent.getBooleanExtra("LOGIN_STATUS", false)
        val username = intent.getStringExtra("USERNAME") ?: "Người dùng"

        // Cập nhật giao diện dựa trên kết quả
        updateUI(loginSuccess, username)

        // Thiết lập sự kiện click cho nút
        btnContinue.setOnClickListener {
            handleContinue(loginSuccess)
        }

        // Cấu hình EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun updateUI(isSuccess: Boolean, user: String) {
        if (isSuccess) {
            tvResultTitle.text = "Đăng Nhập Thành Công!"
            tvResultTitle.setTextColor(Color.parseColor("#4CAF50")) // Màu xanh lá cây
            tvResultMessage.text = "Chào mừng ${user} đến với ứng dụng. Bạn đã có thể bắt đầu sử dụng các tính năng."
            btnContinue.text = "TRANG CHỦ"
            btnContinue.setBackgroundColor(Color.parseColor("#6200EE")) // Màu tím
        } else {
            tvResultTitle.text = "Đăng Nhập Thất Bại!"
            tvResultTitle.setTextColor(Color.RED) // Màu đỏ
            tvResultMessage.text = "Tên đăng nhập hoặc mật khẩu không đúng. Vui lòng kiểm tra và thử lại."
            btnContinue.text = "THỬ LẠI"
            btnContinue.setBackgroundColor(Color.RED) // Màu đỏ
        }
    }

    private fun handleContinue(isSuccess: Boolean) {
        if (isSuccess) {
            // Nếu thành công: Chuyển sang màn hình chính (hoặc kết thúc nếu ResultActivity là màn hình cuối)
            Toast.makeText(this, "Chuyển đến Trang Chủ...", Toast.LENGTH_SHORT).show()
            // Ví dụ: startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            // Nếu thất bại: Quay lại màn hình Đăng nhập (MainActivity)
            finish() // Kết thúc ResultActivity để quay về MainActivity
        }
    }
}