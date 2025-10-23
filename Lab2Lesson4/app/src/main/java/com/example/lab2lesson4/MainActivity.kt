package com.example.lab2lesson4

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.Toast

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

        // Ánh xạ GridView và gán Adapter (Chỉ thực hiện 1 lần)
        val gvBook = findViewById<GridView>(R.id.gvBook)
        val adapter = BookGridView(this, listBook)
        gvBook.adapter = adapter

        // Thêm sự kiện click cho từng Item
        gvBook.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Lấy dữ liệu của sách được click
            val selectedBook = listBook[position]

            // Hiển thị Toast thông báo tên sách
            Toast.makeText(
                this,
                "Bạn đã chọn: ${selectedBook.txtBookName}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}