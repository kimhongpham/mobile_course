package com.example.btvn_lab4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class AddFolderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_add_folder)

        val tvHeader = findViewById<TextView>(R.id.tvHeaderTitle)
        tvHeader.text = "Thêm thư mục mới"
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        val edtTitle = findViewById<EditText>(R.id.edtTitle)
        val edtDescription = findViewById<EditText>(R.id.edtDescription)

        // Xử lý nút Lưu (Thêm mới)
        btnSave.setOnClickListener {
            val newTitle = edtTitle.text.toString()
            val newDesc = edtDescription.text.toString()

            if (newTitle.isNotEmpty()) {
                // Tạo ID tạm thời (trong thực tế sẽ do DB tạo)
                val newId = System.currentTimeMillis().toInt()
                val newFolder = FolderModel(newId, newTitle, newDesc)

                // Truyền dữ liệu mới trở lại Activity A
                val resultIntent = Intent().apply {
                    putExtra("NEW_FOLDER", newFolder)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish() // Tắt Activity C
            } else {
                // Hiển thị lỗi
                edtTitle.error = "Tên thư mục không được để trống"
            }
        }

        // Xử lý nút Hủy
        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}