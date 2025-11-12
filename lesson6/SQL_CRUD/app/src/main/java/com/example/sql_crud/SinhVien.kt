package com.example.sql_crud

class SinhVien(
    var maSV: String = "",
    var tenSV: String = "",
    var maLop: String = ""
) {
    companion object {
        const val TABLE_NAME = "SinhVien"
        const val COLUMN_MASV = "masv"
        const val COLUMN_TENSV = "tensv"
        const val COLUMN_MALOP = "malop"
    }

    override fun toString(): String {
        return "$maSV - $tenSV ($maLop)"
    }
}
