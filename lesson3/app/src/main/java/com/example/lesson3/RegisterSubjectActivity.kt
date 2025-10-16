package com.example.lesson3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson3.databinding.ActivityRegisterSubjectBinding

class RegisterSubjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterSubjectBinding

    // Định nghĩa học phí cho mỗi môn học (đơn vị: nghìn VNĐ)
    private val subjectFees = mapOf(
        "Lập trình C++" to 5000,
        "MIS" to 3000,
        "AI Ứng dụng" to 8000,
        "Đồ Án Thực Tế" to 10000
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nhận Username từ Intent (nếu có)
        val username = intent.getStringExtra("EXTRA_USERNAME") ?: "bạn"
        binding.tvHeaderWelcome.text = "Xin chào $username \nHãy đăng ký môn học"

        // --- Nút Đăng ký: hiển thị danh sách và tính tổng tiền ---
        binding.btnRegister.setOnClickListener {
            displaySelectedSubjectsAndCalculateFee()
        }

        // --- Nút Thanh toán ---
        binding.btnProceedToPayment.setOnClickListener {
            val totalFee = calculateTotalFee()
            if (totalFee > 0) {
                proceedToPayment(totalFee)
            } else {
                Toast.makeText(this, "Vui lòng chọn môn học trước khi thanh toán.", Toast.LENGTH_SHORT).show()
            }
        }

        // --- Nút Xác nhận (chỉ thông báo, không điều hướng) ---
        binding.btnConfirm.setOnClickListener {
            handleConfirmation()
        }
    }

    /**
     * Hiển thị danh sách môn học và tính tổng tiền.
     */
    private fun displaySelectedSubjectsAndCalculateFee() {
        val selectedSubjects = mutableListOf<String>()

        if (binding.cbCPlusPlus.isChecked) selectedSubjects.add("Lập trình C++")
        if (binding.cbMIS.isChecked) selectedSubjects.add("MIS")
        if (binding.cbAI.isChecked) selectedSubjects.add("AI Ứng dụng")
        if (binding.cbProject.isChecked) selectedSubjects.add("Đồ Án Thực Tế")

        if (selectedSubjects.isEmpty()) {
            binding.tvSubjectList.text = "Danh sách môn đăng ký: (Chưa chọn môn nào)"
            binding.tvTotalFee.text = "Tổng tiền: 0 VNĐ"
            Toast.makeText(this, "Vui lòng chọn ít nhất một môn học.", Toast.LENGTH_SHORT).show()
        } else {
            val listText = selectedSubjects.joinToString(separator = "\n- ", prefix = "- ")
            binding.tvSubjectList.text = "Danh sách môn đăng ký:\n$listText"

            // Tính tổng tiền và cập nhật TextView
            val totalFee = calculateTotalFee()
            val formattedFee = String.format("Tổng tiền: %,d VNĐ", totalFee * 1000)
            binding.tvTotalFee.text = formattedFee

            Toast.makeText(this, "Đã ghi nhận ${selectedSubjects.size} môn học.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Tính tổng học phí dựa vào các môn được chọn.
     */
    private fun calculateTotalFee(): Int {
        var totalFee = 0
        if (binding.cbCPlusPlus.isChecked) totalFee += subjectFees["Lập trình C++"]!!
        if (binding.cbMIS.isChecked) totalFee += subjectFees["MIS"]!!
        if (binding.cbAI.isChecked) totalFee += subjectFees["AI Ứng dụng"]!!
        if (binding.cbProject.isChecked) totalFee += subjectFees["Đồ Án Thực Tế"]!!
        return totalFee
    }

    /**
     * Chuyển sang màn hình Thanh toán và gửi tổng tiền.
     */
    private fun proceedToPayment(totalFee: Int) {
        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra("EXTRA_TOTAL_FEE_K", totalFee) // gửi đơn vị nghìn VNĐ
        startActivity(intent)
    }

    /**
     * Hiển thị thông báo chọn tình trạng (không điều hướng).
     */
    private fun handleConfirmation() {
        when (binding.rgStatus.checkedRadioButtonId) {
            binding.rbTemporary.id -> {
                Toast.makeText(this, "Đã chọn Tạm thời.", Toast.LENGTH_SHORT).show()
            }
            binding.rbOfficial.id -> {
                Toast.makeText(this, "Đã chọn Chính thức.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Vui lòng chọn Tình trạng đăng ký.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
