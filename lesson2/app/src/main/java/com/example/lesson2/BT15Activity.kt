package com.example.lesson2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson2.databinding.ActivityBt15Binding

class BT15Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBt15Binding
    private var currentNumber: StringBuilder = StringBuilder("0")
    private var lastOperator: String? = null
    private var firstOperand: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBt15Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lắng nghe click cho các nút số (ví dụ: 1, 2, 3)
        setupNumberButtons()

        // Lắng nghe click cho các nút chức năng (ví dụ: C, +, =)
        setupFunctionButtons()
    }

    // Khung hàm cho các nút số và dấu chấm
    private fun setupNumberButtons() {
        val numberButtons = listOf(binding.btn0, binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6, binding.btn7,
            binding.btn8, binding.btn9)

        numberButtons.forEach { button ->
            button.setOnClickListener {
                appendNumber(button.text.toString())
            }
        }

        binding.btnDot.setOnClickListener {
            if (!currentNumber.contains(".")) {
                currentNumber.append(".")
                updateDisplay()
            }
        }
    }

    // Hàm cập nhật màn hình
    private fun updateDisplay() {
        binding.tvDisplay.text = currentNumber.toString()
    }

    // Hàm xử lý nhập số
    private fun appendNumber(digit: String) {
        if (currentNumber.toString() == "0") {
            currentNumber.clear().append(digit)
        } else {
            currentNumber.append(digit)
        }
        updateDisplay()
    }

    // Khung hàm cho các nút chức năng (Cần logic phức tạp hơn)
    private fun setupFunctionButtons() {
        // Nút Xóa (Clear)
        binding.btnC.setOnClickListener {
            currentNumber.clear().append("0")
            lastOperator = null
            firstOperand = null
            updateDisplay()
        }

        // Nút Cộng (Khung sườn logic)
        binding.btnPlus.setOnClickListener {
            performOperation("+")
        }

        // Nút Bằng (Khung sườn logic)
        binding.btnEquals.setOnClickListener {
            performEquals()
        }

        // ... Thêm logic cho các nút phép toán và chức năng khác
    }

    private fun performOperation(operator: String) {
        // Logic tính toán sẽ được thêm vào đây
        // Ví dụ: Lưu số hiện tại vào firstOperand, đặt lastOperator, và reset currentNumber
    }

    private fun performEquals() {
        // Logic thực hiện phép toán cuối cùng đã được lưu
    }
}