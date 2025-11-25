package com.example.btvn_lab1_recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lab2lesson4.BookRecyclerAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listBook = mutableListOf<BookModel>()

        listBook.add(BookModel(0,
            "https://upload.wikimedia.org/wikipedia/en/thumb/d/d7/Harry_Potter_character_poster.jpg/250px-Harry_Potter_character_poster.jpg"
            , "Harry Potter - hình Internet"))

        listBook.add(BookModel(R.drawable.harrypotter1, "", "Harry Potter - tập 1"))
        listBook.add(BookModel(R.drawable.harrypotter2, "", "Harry Potter - tập 2"))
        listBook.add(BookModel(R.drawable.harrypotter3, "", "Harry Potter - tập 3"))
        listBook.add(BookModel(R.drawable.harrypotter4, "", "Harry Potter - tập 4"))
        listBook.add(BookModel(R.drawable.harrypotter5, "", "Harry Potter - tập 5"))
        listBook.add(BookModel(R.drawable.harrypotter6, "", "Harry Potter - tập 6"))
        listBook.add(BookModel(R.drawable.harrypotter7, "", "Harry Potter - tập 7"))
        listBook.add(BookModel(R.drawable.harrypotter8, "", "Harry Potter - tập 8"))

        // Ánh xạ RecyclerView
        val rvBook = findViewById<RecyclerView>(R.id.rvBook)

        // Thiết lập Layout Manager (2 cột)
        rvBook.layoutManager = GridLayoutManager(this, 2)

        // Khởi tạo Adapter và xử lý sự kiện click
        val adapter = BookRecyclerAdapter(this, listBook) { selectedBook ->
            // Lambda function xử lý click
            Toast.makeText(
                this,
                "Bạn đã chọn: ${selectedBook.txtBookName}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // 4. Gán Adapter
        rvBook.adapter = adapter
    }
}