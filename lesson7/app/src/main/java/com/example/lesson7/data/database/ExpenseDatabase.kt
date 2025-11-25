package com.example.lesson7.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lesson7.data.dao.ExpenseDao
import com.example.lesson7.data.model.ExpenseModel

@Database (entities = [ExpenseModel::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao() : ExpenseDao //Room sẽ tự động  hàm override

    companion object {
        // Ghi giá trị thay đổi vào bộ nhớ chính (thay vì cache)
        // để tất cả các Thread đều thấy giá trị mới.
        @Volatile
        private var INSTANCE : ExpenseDatabase? = null

        fun getDatabase(context: Context) : ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "buoi_7_room_expense_database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
