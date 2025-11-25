package com.example.lesson7.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lesson7.data.db.ExpenseDatabase
import com.example.lesson7.data.model.ExpenseModel
import com.example.lesson7.data.repository.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val expenseRepository: ExpenseRepository
    val allExpenses: LiveData<List<ExpenseModel>>
    val totalExpense: LiveData<Double>

    init {
        val dao = ExpenseDatabase.getDatabase(application).expenseDao()
        expenseRepository = ExpenseRepository(dao)
        allExpenses = expenseRepository.allExpenses
        totalExpense = expenseRepository.totalExpenses
    }

    fun insertExpense(expenseModel: ExpenseModel) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.insertExpense(expenseModel)
        }
    }

    fun deleteExpense(expenseModel: ExpenseModel) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.deleteExpense(expenseModel)
        }
    }

    // Hàm cập nhật chi tiêu
    fun updateExpense(expenseModel: ExpenseModel) {
        viewModelScope.launch(Dispatchers.IO) {
            expenseRepository.updateExpense(expenseModel)
        }
    }
}