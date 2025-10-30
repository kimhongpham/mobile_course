package com.example.btvn_lab2

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var edtFullName: TextInputEditText
    private lateinit var edtDateOfBirth: TextInputEditText
    private lateinit var edtPhoneNumber: TextInputEditText
    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtCMT: TextInputEditText
    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ các View
        edtFullName = findViewById(R.id.edtFullName)
        edtDateOfBirth = findViewById(R.id.edtDateOfBirth)
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber)
        edtEmail = findViewById(R.id.edtEmail)
        edtCMT = findViewById(R.id.edtCMT)
        btnUpdate = findViewById(R.id.btnUpdate)

        // Date Picker Dialog
        setupDatePicker()

        // Validation (Email/SĐT) và Button Enable/Disable
        setupInputWatchers()
    }

    // Date Picker
    private fun setupDatePicker() {
        edtDateOfBirth.setOnClickListener {
            showDatePickerDialog()
        }
        // Bắt buộc thêm lắng nghe khi người dùng bấm vào icon lịch
        edtDateOfBirth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDatePickerDialog()
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Định dạng ngày sinh và gán vào EditText
                val dateString = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
                edtDateOfBirth.setText(dateString)
                checkAllFields() // Kiểm tra lại Button sau khi chọn ngày
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    // Validation và Button Enable/Disable
    private fun setupInputWatchers() {
        val fields = listOf(edtFullName, edtDateOfBirth, edtPhoneNumber, edtEmail, edtCMT)

        for (field in fields) {
            field.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    // Kiểm tra trạng thái Button sau mỗi lần nhập
                    checkAllFields()
                }
            })
        }

        // Validation cụ thể cho Email và SĐT
        edtEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (!isValidEmail(edtEmail.text.toString())) {
                    edtEmail.error = "Email không hợp lệ"
                } else {
                    edtEmail.error = null // Xóa lỗi nếu hợp lệ
                }
            }
        }

        edtPhoneNumber.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (!isValidPhone(edtPhoneNumber.text.toString())) {
                    edtPhoneNumber.error = "Số điện thoại không hợp lệ (Phải đủ 10 số)"
                } else {
                    edtPhoneNumber.error = null
                }
            }
        }
    }

    /**
     * Kiểm tra trạng thái của Button
     * Button chỉ được enable khi TẤT CẢ các trường đều có dữ liệu.
     */
    private fun checkAllFields() {
        val isAllFieldsFilled = edtFullName.text.toString().isNotEmpty() &&
                edtDateOfBirth.text.toString().isNotEmpty() &&
                edtPhoneNumber.text.toString().isNotEmpty() &&
                edtEmail.text.toString().isNotEmpty() &&
                edtCMT.text.toString().isNotEmpty()

        // Kiểm tra thêm các trường đã nhập có hợp lệ (không có lỗi) không
        val isInputValid = edtEmail.error == null && edtPhoneNumber.error == null

        btnUpdate.isEnabled = isAllFieldsFilled && isInputValid

        // Cập nhật màu Button (tùy chọn)
        btnUpdate.backgroundTintList = if (btnUpdate.isEnabled) {
            getColorStateList(R.color.colorPrimary)
        } else {
            getColorStateList(R.color.gray)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhone(phone: String): Boolean {
        // Kiểm tra phải đủ 10 ký tự
        return phone.length == 10
    }
}