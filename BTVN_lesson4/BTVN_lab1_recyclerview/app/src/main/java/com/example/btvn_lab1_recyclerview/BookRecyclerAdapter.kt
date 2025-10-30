package com.example.lab2lesson4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btvn_lab1_recyclerview.BookModel
import com.example.btvn_lab1_recyclerview.R

class BookRecyclerAdapter(
    private val context: Context,
    private val listBook: List<BookModel>,
    private val itemClick: (BookModel) -> Unit
) : RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder>() {

    // Định nghĩa ViewHolder
    class BookViewHolder(itemView: View, val itemClick: (BookModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val imgBook: ImageView = itemView.findViewById(R.id.imgBook)
        val txtBookName: TextView = itemView.findViewById(R.id.txtBookName)
    }

    // 1. Inflate Layout và tạo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return BookViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = listBook[position]

        // Xử lý hình ảnh (dùng Glide)
        if (book.imgBook != 0) {
            holder.imgBook.setImageResource(book.imgBook)
        } else {
            Glide.with(context)
                .load(book.urlBook)
                .into(holder.imgBook)
        }
        holder.txtBookName.text = book.txtBookName

        holder.itemView.setOnClickListener {
            itemClick(book) // Gọi lambda function đã truyền vào
        }
    }

    // Trả về số lượng Item
    override fun getItemCount(): Int = listBook.size
}