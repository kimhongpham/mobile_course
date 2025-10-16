package com.example.lesson3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ Tên đăng nhập và Mật khẩu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tạo Intent để chuyển sang RegisterSubjectActivity
            val intent = Intent(this, RegisterSubjectActivity::class.java)

            // Gửi kèm dữ liệu (Username và Password)
            intent.putExtra("EXTRA_USERNAME", username)
            intent.putExtra("EXTRA_PASSWORD", password)

            // Khởi động Activity mới
            startActivity(intent)
        }
    }
}