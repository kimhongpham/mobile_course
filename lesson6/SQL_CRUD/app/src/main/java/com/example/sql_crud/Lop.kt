package com.example.sql_crud

data class Lop(val maLop: String, val tenLop: String) {
    companion object {
        const val TABLE_NAME = "Lop"
        const val COLUMN_MALOP = "maLop"
        const val COLUMN_TENLOP = "tenLop"
    }

    override fun toString(): String {
        return "$maLop - $tenLop"
    }
}
