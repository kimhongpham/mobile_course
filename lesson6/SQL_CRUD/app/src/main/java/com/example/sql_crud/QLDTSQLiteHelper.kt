package com.example.sql_crud

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QLDTSQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, CmmVariable.DB_NAME, null, CmmVariable.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        // Bảng Lớp
        val createLop = """
            CREATE TABLE ${Lop.TABLE_NAME} (
                ${Lop.COLUMN_MALOP} TEXT PRIMARY KEY,
                ${Lop.COLUMN_TENLOP} TEXT
            )
        """.trimIndent()

        // Bảng Sinh viên với FK
        val createSV = """
            CREATE TABLE ${SinhVien.TABLE_NAME} (
                ${SinhVien.COLUMN_MASV} TEXT PRIMARY KEY,
                ${SinhVien.COLUMN_TENSV} TEXT,
                ${SinhVien.COLUMN_MALOP} TEXT,
                FOREIGN KEY(${SinhVien.COLUMN_MALOP}) REFERENCES ${Lop.TABLE_NAME}(${Lop.COLUMN_MALOP})
            )
        """.trimIndent()

        db?.execSQL(createLop)
        db?.execSQL(createSV)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop cả hai bảng để tránh lỗi FK
        db?.execSQL("DROP TABLE IF EXISTS ${SinhVien.TABLE_NAME}")
        db?.execSQL("DROP TABLE IF EXISTS ${Lop.TABLE_NAME}")
        onCreate(db)
    }

    fun insertLop(lop: Lop): Long {
        val values = ContentValues().apply {
            put(Lop.COLUMN_MALOP, lop.maLop)
            put(Lop.COLUMN_TENLOP, lop.tenLop)
        }
        return writableDatabase.insert(Lop.TABLE_NAME, null, values)
    }

    fun updateLop(lop: Lop): Int {
        val values = ContentValues().apply {
            put(Lop.COLUMN_TENLOP, lop.tenLop)
        }
        return writableDatabase.update(
            Lop.TABLE_NAME,
            values,
            "${Lop.COLUMN_MALOP} = ?",
            arrayOf(lop.maLop)
        )
    }

    fun deleteLop(maLop: String): Int {
        return writableDatabase.delete(
            Lop.TABLE_NAME,
            "${Lop.COLUMN_MALOP} = ?",
            arrayOf(maLop)
        )
    }

    fun getAllLop(): ArrayList<Lop> {
        val list = ArrayList<Lop>()
        val cursor: Cursor = readableDatabase.rawQuery("SELECT * FROM ${Lop.TABLE_NAME}", null)
        if (cursor.moveToFirst()) {
            do {
                val maLop = cursor.getString(cursor.getColumnIndexOrThrow(Lop.COLUMN_MALOP))
                val tenLop = cursor.getString(cursor.getColumnIndexOrThrow(Lop.COLUMN_TENLOP))
                list.add(Lop(maLop, tenLop))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun searchLop(query: String): ArrayList<Lop> {
        val list = ArrayList<Lop>()
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${Lop.TABLE_NAME} WHERE ${Lop.COLUMN_MALOP} LIKE ? OR ${Lop.COLUMN_TENLOP} LIKE ?",
            arrayOf("%$query%", "%$query%")
        )
        if (cursor.moveToFirst()) {
            do {
                val maLop = cursor.getString(cursor.getColumnIndexOrThrow(Lop.COLUMN_MALOP))
                val tenLop = cursor.getString(cursor.getColumnIndexOrThrow(Lop.COLUMN_TENLOP))
                list.add(Lop(maLop, tenLop))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun insertSV(sv: SinhVien): Long {
        val values = ContentValues().apply {
            put(SinhVien.COLUMN_MASV, sv.maSV)
            put(SinhVien.COLUMN_TENSV, sv.tenSV)
            put(SinhVien.COLUMN_MALOP, sv.maLop)
        }
        return writableDatabase.insert(SinhVien.TABLE_NAME, null, values)
    }

    fun updateSV(sv: SinhVien): Int {
        val values = ContentValues().apply {
            put(SinhVien.COLUMN_TENSV, sv.tenSV)
            put(SinhVien.COLUMN_MALOP, sv.maLop)
        }
        return writableDatabase.update(
            SinhVien.TABLE_NAME,
            values,
            "${SinhVien.COLUMN_MASV} = ?",
            arrayOf(sv.maSV)
        )
    }

    fun deleteSV(maSV: String): Int {
        return writableDatabase.delete(
            SinhVien.TABLE_NAME,
            "${SinhVien.COLUMN_MASV} = ?",
            arrayOf(maSV)
        )
    }

    fun getAllSVByLop(maLop: String): ArrayList<SinhVien> {
        val list = ArrayList<SinhVien>()
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${SinhVien.TABLE_NAME} WHERE ${SinhVien.COLUMN_MALOP} = ?",
            arrayOf(maLop)
        )
        if (cursor.moveToFirst()) {
            do {
                val sv = SinhVien(
                    cursor.getString(cursor.getColumnIndexOrThrow(SinhVien.COLUMN_MASV)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SinhVien.COLUMN_TENSV)),
                    cursor.getString(cursor.getColumnIndexOrThrow(SinhVien.COLUMN_MALOP))
                )
                list.add(sv)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}
