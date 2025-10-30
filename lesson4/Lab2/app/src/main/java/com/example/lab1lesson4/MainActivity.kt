package com.example.lab1lesson4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ và xử lý sự kiện cho Button
        val btnGoTo2: Button = findViewById(R.id.btn_go_to_2)
        val btnGoTo3: Button = findViewById(R.id.btn_go_to_3)

        btnGoTo2.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        btnGoTo3.setOnClickListener {
            // Màn hình 3 đã được cấu hình hiển thị dạng Dialog trong AndroidManifest
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        // Bước 3: Thêm Toast cho các sự kiện Lifecycle (sẽ làm chi tiết ở bước 3)
        showToast("MainActivity: onCreate()")
    }

    // Hàm hỗ trợ để hiển thị Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        // Có thể dùng Log.d("Lifecycle", message) để theo dõi chi tiết hơn trong Logcat
        Log.d("Lifecycle", message)
    }

    // Ghi đè các sự kiện Lifecycle
    override fun onStart() {
        super.onStart()
        showToast("${this::class.simpleName}: onStart()")
    }

    override fun onResume() {
        super.onResume()
        showToast("${this::class.simpleName}: onResume()")
    }

    override fun onPause() {
        super.onPause()
        showToast("${this::class.simpleName}: onPause()")
    }

    override fun onStop() {
        super.onStop()
        showToast("${this::class.simpleName}: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        showToast("${this::class.simpleName}: onDestroy()")
    }

    override fun onRestart() {
        super.onRestart()
        showToast("${this::class.simpleName}: onRestart()")
    }
}