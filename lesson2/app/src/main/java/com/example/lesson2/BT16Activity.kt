package com.example.lesson2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson2.databinding.ActivityBt16Binding

class BT16Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBt16Binding
    private lateinit var nhanVienList: ArrayList<NhanVien>
    private lateinit var adapter: NhanVienAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBt16Binding.inflate(layoutInflater)
        setContentView(binding.root)

        nhanVienList = ArrayList()
        // Thêm dữ liệu mẫu
        nhanVienList.add(NhanVien("m1", "NGuyen Thi Teo", true))
        nhanVienList.add(NhanVien("m2", "Tran Van Ty", false))

        // Khởi tạo Custom Adapter
        adapter = NhanVienAdapter(this, R.layout.list_item_nhanvien, nhanVienList)
        binding.lvNhanVien.adapter = adapter

        // Xử lý sự kiện khi nhấn nút "Nhập NV"
        binding.btnNhapNV.setOnClickListener {
            insertNhanVien()
        }

        // Xử lý sự kiện khi click vào một item (ví dụ: hiển thị Toast)
        binding.lvNhanVien.setOnItemClickListener { parent, view, position, id ->
            val nv = nhanVienList[position]
            Toast.makeText(this, "Đã chọn: ${nv.tenNV} (Mã: ${nv.maNV})", Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertNhanVien() {
        val maNV = binding.etMaNV.text.toString().trim()
        val tenNV = binding.etTenNV.text.toString().trim()
        val isChinhThuc = binding.rbChinhThuc.isChecked

        if (maNV.isBlank() || tenNV.isBlank()) {
            Toast.makeText(this, "Vui lòng nhập Mã NV và Tên NV.", Toast.LENGTH_SHORT).show()
            return
        }

        val newNV = NhanVien(maNV, tenNV, isChinhThuc)
        nhanVienList.add(newNV)
        adapter.notifyDataSetChanged() // Cập nhật ListView

        // Xóa trường nhập liệu
        binding.etMaNV.text.clear()
        binding.etTenNV.text.clear()

        Toast.makeText(this, "Đã thêm Nhân viên: $tenNV", Toast.LENGTH_SHORT).show()
    }
}