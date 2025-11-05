package com.example.lab1

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomSpinnerAdapter(val context: Activity, val list: ArrayList<CustomSpinnerModel>) :
    BaseAdapter() {

    private class ViewHolder(view: View) {
        // Ánh xạ View từ layout_custom_spinner
        val image = view.findViewById<ImageView>(R.id.imageViewLogo)
        val name = view.findViewById<TextView>(R.id.txtTenHangXe)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any? {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var view = convertView
        val holder: ViewHolder

        view = context.layoutInflater.inflate(R.layout.layout_custom_spinner, null)
        holder = ViewHolder(view)

        holder.image.setImageResource(list[position].image)
        holder.name.text = list[position].name

        return view
    }

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        // Sử dụng lại logic của getView cho Dropdown View
        return getView(position, convertView, parent)
    }
}