package com.example.lesson7.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lesson7.data.model.ExpenseModel
import androidx.room.Delete
import androidx.room.Update

@Dao
interface ExpenseDao {
    //suspend cho thao tác db chỉ thực hiện 1 lần
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseModel)

    // Dùng fun trả về LiveData hoặc Flow cho các truy vấn
    @Query ("SELECT * FROM ExpenseModel ORDER BY id DESC")
    fun getAllExpenses(): LiveData<List<ExpenseModel>>

    @Query ("SELECT SUM(amount) FROM ExpenseModel")
    fun getTotalExpense(): LiveData<Double>

    @Query("SELECT * FROM ExpenseModel WHERE id = :expenseId")
    suspend fun getExpenseById(expenseId: Long): ExpenseModel

    @Delete
    suspend fun deleteExpense(expense: ExpenseModel)

    @Update
    suspend fun updateExpense(expense: ExpenseModel)
}
