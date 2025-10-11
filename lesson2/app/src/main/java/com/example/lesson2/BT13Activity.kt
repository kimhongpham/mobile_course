package com.example.lesson2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson2.databinding.ActivityBt13Binding

class BT13Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBt13Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBt13Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Thiết lập sự kiện lắng nghe cho tất cả các nút toán tử
        binding.btnPlus.setOnClickListener { calculate('+') }
        binding.btnMinus.setOnClickListener { calculate('-') }
        binding.btnMultiply.setOnClickListener { calculate('*') }
        binding.btnDivide.setOnClickListener { calculate('/') }
    }

    private fun calculate(operator: Char) {
        // Lấy dữ liệu đầu vào và kiểm tra rỗng
        val inputA = binding.etA.text.toString()
        val inputB = binding.etB.text.toString()

        if (inputA.isBlank() || inputB.isBlank()) {
            Toast.makeText(this, "Vui lòng nhập cả hai số A và B.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val a = inputA.toDouble()
            val b = inputB.toDouble()
            var result = 0.0

            when (operator) {
                '+' -> result = a + b
                '-' -> result = a - b
                '*' -> result = a * b
                '/' -> {
                    if (b == 0.0) {
                        // Xử lý chia cho 0
                        binding.tvResult.text = "Lỗi: Không thể chia cho 0"
                        return
                    }
                    result = a / b
                }
            }

            // Hiển thị kết quả
            binding.tvResult.text = "$inputA $operator $inputB = ${String.format("%.2f", result)}"

        } catch (e: NumberFormatException) {
            // Xử lý lỗi nếu người dùng nhập ký tự không phải số
            Toast.makeText(this, "Đầu vào không hợp lệ. Vui lòng chỉ nhập số.", Toast.LENGTH_SHORT).show()
            binding.tvResult.text = "Lỗi tính toán."
        }
    }
}