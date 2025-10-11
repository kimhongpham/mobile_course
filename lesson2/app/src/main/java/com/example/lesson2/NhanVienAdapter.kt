package com.example.lesson2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class NhanVienAdapter(context: Context, resource: Int, objects: ArrayList<NhanVien>) :
    ArrayAdapter<NhanVien>(context, resource, objects) {

    // Phương thức tạo và điền dữ liệu vào mỗi hàng
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            // Lấy LayoutInflater từ Context
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            // Ánh xạ layout list_item_nhanvien
            itemView = inflater.inflate(R.layout.list_item_nhanvien, parent, false)
        }

        // Lấy đối tượng NhânVien tại vị trí hiện tại
        val nhanVien = getItem(position)

        // Ánh xạ các TextView từ list_item_nhanvien.xml
        val tvMaTenNV = itemView!!.findViewById<TextView>(R.id.tvMaTenNV)
        val tvLoaiLuong = itemView.findViewById<TextView>(R.id.tvLoaiLuong)

        // Đổ dữ liệu vào các TextView
        if (nhanVien != null) {
            tvMaTenNV.text = "${nhanVien.maNV} - ${nhanVien.tenNV}"

            val loaiNVText = if (nhanVien.isChinhThuc) "FullTime" else "PartTime"
            tvLoaiLuong.text = "-->$loaiNVText=${String.format("%.1f", nhanVien.luong)}"
        }

        return itemView
    }
}