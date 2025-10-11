package com.example.lesson2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson2.databinding.ActivityBt12Binding

class BT12Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBt12Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBt12Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Xử lý sự kiện khi nút SEND được nhấn
        binding.btnSend.setOnClickListener {
            val to = binding.etTo.text.toString()
            val subject = binding.etSubject.text.toString()
            val message = binding.etMessage.text.toString()

            if (to.isNotBlank() && subject.isNotBlank() && message.isNotBlank()) {
                // Hiển thị thông báo khi tất cả các trường được điền đầy đủ
                Toast.makeText(this, "Email Sent to: $to", Toast.LENGTH_LONG).show()
                // Xóa nội dung
                binding.etTo.text.clear()
                binding.etSubject.text.clear()
                binding.etMessage.text.clear()
            } else {
                // Hiển thị cảnh báo nếu thiếu thông tin
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}