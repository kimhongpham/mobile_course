package com.example.lab2lesson4

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class BookGridView (val activity: Activity, val listBook: List<BookModel>)
    : ArrayAdapter<BookModel>(activity, R.layout.layout_item) {

    override fun getCount(): Int {
        // Trả về số lượng item trong list
        return listBook.size
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view: View = convertView ?: activity.layoutInflater.inflate(R.layout.layout_item, parent, false)

        val imgBook = view.findViewById<ImageView>(R.id.imgBook)
        val txtBookName = view.findViewById<TextView>(R.id.txtBookName)

        val book = listBook[position]

        if (book.imgBook != 0) {
            imgBook.setImageResource(book.imgBook)
        } else {
            Glide.with(activity)
                .load(book.urlBook)
                .placeholder(R.drawable.harrypotter1)
                .into(imgBook)
        }

        // Gán tên sách
        txtBookName.text = book.txtBookName

        return view
    }
}