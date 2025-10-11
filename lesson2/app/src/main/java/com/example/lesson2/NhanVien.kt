package com.example.lesson2

data class NhanVien(
    val maNV: String,
    val tenNV: String,
    val isChinhThuc: Boolean // true: Chính thức, false: Thời vụ
) {
    // Thuộc tính tính toán Lương
    val luong: Double
        get() = if (isChinhThuc) 500.0 else 150.0

    // Phương thức tùy chỉnh để hiển thị trên ListView (Nếu không dùng Custom Adapter)
    override fun toString(): String {
        val loaiNV = if (isChinhThuc) "FullTime" else "PartTime"
        return "$maNV - $tenNV -- >$loaiNV=$luong"
    }
}