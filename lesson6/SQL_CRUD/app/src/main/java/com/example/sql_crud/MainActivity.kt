package com.example.sql_crud

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sql_crud.databinding.ActivityMainBinding
import databaseForCopyFromAsset.CopyDBHelper
import android.content.Intent
import android.text.TextWatcher
import android.text.Editable

class MainActivity : AppCompatActivity() {
    private lateinit var sqliteHelper: QLDTSQLiteHelper
    private lateinit var listLopHoc: ArrayList<Lop>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Khởi tạo View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Copy DB và Khởi tạo Helper
        copyDatabaseFromAsset()
        initQLDTDatabase()

        // Tải danh sách lớp ban đầu
        loadLopListView()

        // Gán sự kiện cho các nút và ListView
        addEvents()
    }

    private fun addEvents() {
        // Sự kiện click nút THÊM
        binding.btnThem.setOnClickListener {
            val maLop = binding.edtMaLop.text.toString().trim()
            val tenLop = binding.edtTenLop.text.toString().trim()
            if (maLop.isNotEmpty() && tenLop.isNotEmpty()) {
                val lop = Lop(maLop, tenLop)
                insertLop(lop)
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ Mã và Tên Lớp", Toast.LENGTH_SHORT).show()
            }
        }

        // Sự kiện click nút XÓA
        binding.btnXoa.setOnClickListener {
            val maLop = binding.edtMaLop.text.toString().trim()
            if (maLop.isNotEmpty()) {
                deleteLop(maLop)
            } else {
                Toast.makeText(this, "Vui lòng nhập Mã Lớp cần XÓA", Toast.LENGTH_SHORT).show()
            }
        }

        // Sự kiện click nút SỬA
        binding.btnSua.setOnClickListener {
            val maLop = binding.edtMaLop.text.toString().trim()
            val tenLop = binding.edtTenLop.text.toString().trim()
            if (maLop.isNotEmpty() && tenLop.isNotEmpty()) {
                updateLop(Lop(maLop, tenLop))
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ Mã và Tên Lớp để SỬA", Toast.LENGTH_SHORT).show()
            }
        }

        // Sự kiện click nút LOAD/REFRESH
        binding.btnRefresh.setOnClickListener {
            loadLopListView()
            Toast.makeText(this, "Đã tải lại danh sách Lớp", Toast.LENGTH_SHORT).show()
        }

        // Sự kiện click Item trong ListView
        binding.lvLop.setOnItemLongClickListener { _, _, position, _ ->
            val maLop = listLopHoc[position].maLop
            val intent = Intent(this, SinhVienActivity::class.java)
            intent.putExtra("MA_LOP", maLop)
            startActivity(intent)
            true
        }

        binding.edtSearchLop.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadLopListView(s.toString().trim())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Xử lý logic THÊM
    private fun insertLop(lop: Lop) {
        if (sqliteHelper.insertLop(lop) >= 0) { // <-- Dùng kết quả trả về để check PK
            Toast.makeText(this, "Thêm Lớp ${lop.maLop} thành công", Toast.LENGTH_SHORT).show()
            loadLopListView()
        } else {
            Toast.makeText(this, "Thêm thất bại. Có thể Mã Lớp đã tồn tại.", Toast.LENGTH_LONG).show() // <-- Thông báo lỗi PK
        }
    }

    // Xử lý logic SỬA
    private fun updateLop(lop: Lop) {
        val rowsAffected = sqliteHelper.updateLop(lop)
        if (rowsAffected > 0) {
            Toast.makeText(this, "Sửa Lớp ${lop.maLop} thành công", Toast.LENGTH_SHORT).show()
            loadLopListView()
        } else {
            Toast.makeText(this, "Sửa thất bại. Mã Lớp không tồn tại.", Toast.LENGTH_LONG).show()
        }
    }

    // Xử lý logic XÓA
    private fun deleteLop(maLop: String) {
        sqliteHelper.deleteLop(maLop)
        Toast.makeText(this, "Đã Xóa Lớp $maLop", Toast.LENGTH_SHORT).show()
        loadLopListView()
    }

    // Hàm tải và hiển thị danh sách Lớp lên ListView, hỗ trợ tìm kiếm
    private fun loadLopListView(searchQuery: String = "") {
        // Nếu có query, gọi hàm tìm kiếm trong Helper
        listLopHoc = if (searchQuery.isNotEmpty()) {
            sqliteHelper.searchLop(searchQuery) // Hàm search cần thêm trong Helper
        } else {
            sqliteHelper.getAllLop()
        }

        // Tạo danh sách chuỗi hiển thị
        val listLopDisplay = listLopHoc.map { it.toString() }

        // Khởi tạo và gán Adapter cho ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listLopDisplay)
        binding.lvLop.adapter = adapter
    }

    // Khởi tạo QLDTSQLiteHelper
    private fun initQLDTDatabase() {
        sqliteHelper = QLDTSQLiteHelper(this)
    }

    // Copy database từ asset
    private fun copyDatabaseFromAsset() {
        val db = CopyDBHelper(this)
        db.openDatabase()
    }
}
