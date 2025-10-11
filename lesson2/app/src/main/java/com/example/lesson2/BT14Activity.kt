package com.example.lesson2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson2.databinding.ActivityBt14Binding
import java.util.ArrayList

class BT14Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBt14Binding
    private lateinit var nameList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBt14Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Khởi tạo ArrayList và ArrayAdapter
        nameList = ArrayList()
        nameList.add("teu")
        nameList.add("ty")
        nameList.add("bin")

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            nameList
        )

        // Gán Adapter cho ListView
        binding.listViewNames.adapter = adapter

        // 2. Xử lý sự kiện khi nhấn nút "Nhập"
        binding.btnInsert.setOnClickListener {
            val name = binding.etNameInput.text.toString().trim()
            if (name.isNotBlank()) {
                nameList.add(0, name) // Thêm tên vào đầu danh sách
                adapter.notifyDataSetChanged() // Thông báo cho Adapter cập nhật ListView
                binding.etNameInput.text.clear() // Xóa trường nhập liệu
                Toast.makeText(this, "Đã thêm: $name", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng nhập tên.", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Xử lý sự kiện khi click vào một item trong danh sách
        binding.listViewNames.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = nameList[position]
            binding.tvSelectionInfo.text = "position : $position; value = $selectedItem"
        }
    }
}