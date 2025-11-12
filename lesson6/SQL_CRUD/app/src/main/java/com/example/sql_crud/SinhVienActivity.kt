package com.example.sql_crud

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sql_crud.databinding.ActivitySinhvienBinding

class SinhVienActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySinhvienBinding
    private lateinit var sqliteHelper: QLDTSQLiteHelper
    private lateinit var listSV: ArrayList<SinhVien>
    private lateinit var listLop: ArrayList<Lop>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySinhvienBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqliteHelper = QLDTSQLiteHelper(this)

        // Luôn load danh sách lớp trước
        loadLopSpinner()

        // Nếu được truyền mã lớp từ Intent
        val maLop = intent.getStringExtra("MA_LOP")
        if (maLop != null) {
            setSelectedLop(maLop)
            loadSinhVien(maLop)
        } else if (listLop.isNotEmpty()) {
            // Nếu không có mã lớp, mặc định chọn lớp đầu tiên
            loadSinhVien(listLop[0].maLop)
        }

        addEvents()
    }

    /** Chọn đúng lớp trong Spinner **/
    private fun setSelectedLop(maLop: String) {
        val index = listLop.indexOfFirst { it.maLop == maLop }
        if (index >= 0) binding.spnLop.setSelection(index)
    }

    /** Load danh sách lớp **/
    private fun loadLopSpinner() {
        listLop = sqliteHelper.getAllLop()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listLop)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnLop.adapter = adapter
    }

    // Gán sự kiện cho các nút
    private fun addEvents() {
        binding.spnLop.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val maLop = listLop[pos].maLop
                loadSinhVien(maLop)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        // Nút thêm sinh viên
        binding.btnThemSV.setOnClickListener {
            val maSV = binding.edtMaSV.text.toString().trim()
            val tenSV = binding.edtTenSV.text.toString().trim()
            val maLop = (binding.spnLop.selectedItem as Lop).maLop

            if (maSV.isEmpty() || tenSV.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sqliteHelper.insertSV(SinhVien(maSV, tenSV, maLop))
            Toast.makeText(this, "Đã thêm sinh viên $tenSV", Toast.LENGTH_SHORT).show()
            loadSinhVien(maLop)
        }

        // Khi click vào 1 SV trong danh sách
        binding.lvSV.setOnItemClickListener { _, _, position, _ ->
            val sv = listSV[position]
            binding.edtMaSV.setText(sv.maSV)
            binding.edtTenSV.setText(sv.tenSV)
            setSelectedLop(sv.maLop)
        }

        /** ✏️ Nút sửa **/
        binding.btnSuaSV.setOnClickListener {
            val maSV = binding.edtMaSV.text.toString().trim()
            val tenSV = binding.edtTenSV.text.toString().trim()
            val maLop = (binding.spnLop.selectedItem as Lop).maLop
            val sv = SinhVien(maSV, tenSV, maLop)

            val rows = sqliteHelper.updateSV(sv)
            if (rows > 0) {
                Toast.makeText(this, "Đã sửa sinh viên $maSV", Toast.LENGTH_SHORT).show()
                loadSinhVien(maLop)
            } else {
                Toast.makeText(this, "Không tìm thấy sinh viên để sửa", Toast.LENGTH_SHORT).show()
            }
        }

        // Nút xóa
        binding.btnXoaSV.setOnClickListener {
            val maSV = binding.edtMaSV.text.toString().trim()
            if (maSV.isNotEmpty()) {
                sqliteHelper.deleteSV(maSV)
                Toast.makeText(this, "Đã xóa sinh viên $maSV", Toast.LENGTH_SHORT).show()
                val maLop = (binding.spnLop.selectedItem as Lop).maLop
                loadSinhVien(maLop)
            } else {
                Toast.makeText(this, "Nhập mã SV để xóa", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Load danh sách sinh viên theo lớp
    private fun loadSinhVien(maLop: String) {
        listSV = sqliteHelper.getAllSVByLop(maLop)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listSV)
        binding.lvSV.adapter = adapter
    }
}
