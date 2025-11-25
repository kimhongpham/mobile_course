package com.example.btvn_lab4

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var folderAdapter: FolderAdapter
    private val listFolders = mutableListOf<FolderModel>()

    // Dùng để lưu vị trí (position) của item đang được chỉnh sửa
    private var editingPosition: Int = -1

    // Activity Result Launcher cho Chỉnh sửa (A -> B)
    private val editFolderLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedFolder = result.data?.getParcelableExtra<FolderModel>("UPDATED_FOLDER")
            if (updatedFolder != null && editingPosition != -1) {
                folderAdapter.updateItem(editingPosition, updatedFolder)
            }
            editingPosition = -1 // Reset
        }
    }

    // Activity Result Launcher cho Thêm mới (A -> C)
    private val addFolderLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newFolder = result.data?.getParcelableExtra<FolderModel>("NEW_FOLDER")
            if (newFolder != null) {
                folderAdapter.addItem(newFolder)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Cần có RecyclerView

        setupInitialData() // Tạo dữ liệu mẫu

        // Setup RecyclerView
        val rvFolders = findViewById<RecyclerView>(R.id.rvFolders)
        folderAdapter = FolderAdapter(listFolders) { folder, position ->
            // Xử lý click item -> Chuyển sang Activity B (Chỉnh sửa)
            editingPosition = position // Lưu vị trí item
            val intent = Intent(this, EditFolderActivity::class.java).apply {
                putExtra("FOLDER_DATA", folder)
                putExtra("FOLDER_POSITION", position)
            }
            editFolderLauncher.launch(intent)
        }

        rvFolders.layoutManager = LinearLayoutManager(this)
        rvFolders.adapter = folderAdapter

        // Xử lý nút Thêm -> Chuyển sang Activity C (Thêm mới)
        val btnAdd = findViewById<TextView>(R.id.btnAdd) // Hoặc Button
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddFolderActivity::class.java)
            addFolderLauncher.launch(intent)
        }
    }

    private fun setupInitialData() {
        listFolders.add(FolderModel(1, "Tổng hợp tin tức thời sự", "Tổng hợp tin tức sự nóng hổi nhất, của tất cả các báo nổi nhất hiện nay"))
        listFolders.add(FolderModel(2, "Do It Yourself", "Sơn tường nhà, mẹo vặt qua điện thoại hay hay"))
        listFolders.add(FolderModel(3, "Cảm hứng sáng tạo", "Khơi nguồn ý tưởng và sáng tạo mỗi ngày"))
        listFolders.add(FolderModel(4, "Ẩm thực bốn phương", "Khám phá món ngon Việt Nam và quốc tế"))
        listFolders.add(FolderModel(5, "Công nghệ mới", "Cập nhật xu hướng AI, thiết bị di động, và phần mềm"))
        listFolders.add(FolderModel(6, "Sức khỏe & Đời sống", "Các bài viết hữu ích về sức khỏe thể chất và tinh thần"))
        listFolders.add(FolderModel(7, "Du lịch khám phá", "Chia sẻ trải nghiệm và địa điểm thú vị trong nước và quốc tế"))
        listFolders.add(FolderModel(8, "Thời trang & Phong cách", "Mẹo phối đồ, xu hướng thời trang mới nhất"))
        listFolders.add(FolderModel(9, "Giải trí mỗi ngày", "Phim ảnh, âm nhạc, và những câu chuyện thú vị"))
        listFolders.add(FolderModel(10, "Kinh doanh & Đầu tư", "Phân tích thị trường, mẹo tài chính cá nhân"))
    }
}