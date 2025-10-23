package com.example.lesson3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson3.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding

    // --- Tỷ giá giả định (đơn vị: VNĐ / 1 đơn vị Crypto) ---
    private val BITCOIN_RATE_VN_PER_UNIT = 1_500_000_000L // 1.5 Tỷ
    private val ETHEREUM_RATE_VN_PER_UNIT = 80_000_000L   // 80 Triệu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nhận tổng tiền từ Intent (đơn vị nghìn VNĐ)
        val totalFeeK = intent.getIntExtra("EXTRA_TOTAL_FEE_K", 0)
        val totalFeeVND = totalFeeK * 1000L

        if (totalFeeVND > 0) {
            binding.tvFeeVND.text = String.format("Số tiền cần thanh toán: %,d VNĐ", totalFeeVND)
        } else {
            binding.tvFeeVND.text = "Số tiền cần thanh toán: 0 VNĐ"
        }

        updateImage(binding.rbBitcoin.text.toString())
        updateCryptoFee(totalFeeVND)

        binding.rgPaymentMethod.setOnCheckedChangeListener { _, checkedId ->
            val selectedMethod = when (checkedId) {
                binding.rbBitcoin.id -> binding.rbBitcoin.text.toString()
                binding.rbEthereum.id -> binding.rbEthereum.text.toString()
                else -> ""
            }
            updateImage(selectedMethod)
            updateCryptoFee(totalFeeVND)
        }

        binding.btnPayNow.setOnClickListener {
            handlePaymentSuccess()
        }

        binding.imgBtnExit.setOnClickListener {
            finish()
        }

        // GỌI HỖ TRỢ
        binding.btnCallSupport.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:0901234567")
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Không tìm thấy ứng dụng gọi điện.", Toast.LENGTH_SHORT).show()
            }
        }

        // GỬI EMAIL HÓA ĐƠN
        binding.btnSendEmail.setOnClickListener {
            val totalFee = binding.tvFeeVND.text.toString()
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822" // Định dạng email
                putExtra(Intent.EXTRA_EMAIL, arrayOf("support@academy.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Hóa đơn thanh toán")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Chào bạn,\n\nĐây là hóa đơn thanh toán $totalFee.\n\nTrân trọng."
                )
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(intent, "Chọn ứng dụng gửi Email:"))
            } else {
                Toast.makeText(this, "Không tìm thấy ứng dụng email.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Cập nhật hình ảnh ImageView theo hình thức thanh toán
    private fun updateImage(method: String) {
        val imageResId = when (method) {
            "Bitcoin" -> R.drawable.ic_bitcoin
            "Ethereum" -> R.drawable.ic_ethereum
            else -> 0
        }

        if (imageResId != 0) {
            binding.ivCryptoImage.setImageResource(imageResId)
        }
    }

    // Tính và hiển thị số lượng Crypto cần thanh toán
    private fun updateCryptoFee(totalFeeVND: Long) {
        val selectedCrypto = when (binding.rgPaymentMethod.checkedRadioButtonId) {
            binding.rbBitcoin.id -> "Bitcoin"
            binding.rbEthereum.id -> "Ethereum"
            else -> "Bitcoin"
        }

        val rate = if (selectedCrypto == "Bitcoin") BITCOIN_RATE_VN_PER_UNIT else ETHEREUM_RATE_VN_PER_UNIT
        val cryptoAmount = totalFeeVND.toDouble() / rate.toDouble()
        val formattedAmount = String.format("%.8f %s", cryptoAmount, selectedCrypto)

        binding.tvFeeCrypto.text = "Quy đổi $selectedCrypto: $formattedAmount"
    }

    // Xử lý nút Thanh toán thành công
    private fun handlePaymentSuccess() {
        val selectedText = when (binding.rgPaymentMethod.checkedRadioButtonId) {
            binding.rbBitcoin.id -> "Bitcoin"
            binding.rbEthereum.id -> "Ethereum"
            else -> "một hình thức chưa xác định"
        }

        Toast.makeText(this, "Thanh toán thành công với $selectedText", Toast.LENGTH_LONG).show()
    }
}
