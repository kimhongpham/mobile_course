package com.example.lesson7.data.repository

import androidx.lifecycle.LiveData
import com.example.lesson7.data.dao.ExpenseDao
import com.example.lesson7.data.model.ExpenseModel

class ExpenseRepository (private val expenseDao: ExpenseDao) {
    val allExpenses: LiveData<List<ExpenseModel>> = expenseDao.getAllExpenses()
    val totalExpenses: LiveData<Double> = expenseDao.getTotalExpense()

    suspend fun insertExpense(expense: ExpenseModel) {
        expenseDao.insertExpense(expense)
    }
    suspend fun deleteExpense(expense: ExpenseModel) {
        expenseDao.deleteExpense(expense)
    }
    suspend fun updateExpense(expense: ExpenseModel) {
        expenseDao.updateExpense(expense)
    }
}
