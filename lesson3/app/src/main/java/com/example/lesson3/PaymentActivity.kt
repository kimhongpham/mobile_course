package com.example.lesson3

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

        // --- Nhận tổng tiền từ Intent (đơn vị nghìn VNĐ) ---
        val totalFeeK = intent.getIntExtra("EXTRA_TOTAL_FEE_K", 0)
        val totalFeeVND = totalFeeK * 1000L

        // --- Hiển thị số tiền VND ---
        if (totalFeeVND > 0) {
            binding.tvFeeVND.text = String.format("Số tiền cần thanh toán: %,d VNĐ", totalFeeVND)
        } else {
            binding.tvFeeVND.text = "Số tiền cần thanh toán: 0 VNĐ"
        }

        // --- Cập nhật hình ảnh và tỷ giá mặc định (Bitcoin) ---
        updateImage(binding.rbBitcoin.text.toString())
        updateCryptoFee(totalFeeVND)

        // --- Khi thay đổi RadioButton ---
        binding.rgPaymentMethod.setOnCheckedChangeListener { _, checkedId ->
            val selectedMethod = when (checkedId) {
                binding.rbBitcoin.id -> binding.rbBitcoin.text.toString()
                binding.rbEthereum.id -> binding.rbEthereum.text.toString()
                else -> ""
            }
            updateImage(selectedMethod)
            updateCryptoFee(totalFeeVND)
        }

        // --- Nút "Thanh toán" (Giả lập thành công) ---
        binding.btnPayNow.setOnClickListener {
            handlePaymentSuccess()
        }

        // --- Nút "Exit" (Chỉ thoát) ---
        binding.imgBtnExit.setOnClickListener {
            finish()
        }
    }

    /**
     * Cập nhật hình ảnh ImageView theo hình thức thanh toán.
     */
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

    /**
     * Tính và hiển thị số lượng Crypto cần thanh toán.
     */
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

    /**
     * Xử lý nút Thanh toán thành công.
     */
    private fun handlePaymentSuccess() {
        val selectedText = when (binding.rgPaymentMethod.checkedRadioButtonId) {
            binding.rbBitcoin.id -> "Bitcoin"
            binding.rbEthereum.id -> "Ethereum"
            else -> "một hình thức chưa xác định"
        }

        Toast.makeText(this, "Thanh toán thành công với $selectedText", Toast.LENGTH_LONG).show()
    }
}
