package com.example.btvn_lab4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EditFolderActivity : AppCompatActivity() {

    private lateinit var originalFolder: FolderModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_add_folder)

        val tvHeader = findViewById<TextView>(R.id.tvHeaderTitle)
        tvHeader.text = "Chỉnh sửa thư mục"
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        val edtTitle = findViewById<EditText>(R.id.edtTitle)
        val edtDescription = findViewById<EditText>(R.id.edtDescription)

        // Nhận dữ liệu từ Activity A
        originalFolder = intent.getParcelableExtra("FOLDER_DATA") ?: return

        // Hiển thị dữ liệu lên EditText
        edtTitle.setText(originalFolder.title)
        edtDescription.setText(originalFolder.description)

        // Xử lý nút Lưu
        btnSave.setOnClickListener {
            // Lấy dữ liệu mới
            val newTitle = edtTitle.text.toString()
            val newDesc = edtDescription.text.toString()

            // Cập nhật model
            val updatedFolder = originalFolder.copy(title = newTitle, description = newDesc)

            // Truyền dữ liệu trở lại Activity A
            val resultIntent = Intent().apply {
                putExtra("UPDATED_FOLDER", updatedFolder)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Tắt Activity B
        }

        // Xử lý nút Hủy
        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}