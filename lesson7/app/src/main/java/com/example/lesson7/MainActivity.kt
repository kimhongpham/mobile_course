package com.example.lesson7

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.core.view.WindowCompat
import com.example.lesson7.data.model.ExpenseModel
import com.example.lesson7.databinding.ActivityMainBinding
import com.example.lesson7.databinding.DialogAddExpenseBinding
import com.example.lesson7.ui.adapter.ExpenseAdapter
import com.example.lesson7.ui.viewmodel.ExpenseViewModel
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ExpenseAdapter
    private val viewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ExpenseAdapter(
            onEditClick = { expense ->
                showEditOrCreateExpenseDialog(expense) // Click: Mở dialog chỉnh sửa
            },
            onDeleteClick = { expense ->
                AlertDialog.Builder(this)
                    .setTitle("Delete Expense")
                    .setMessage("Are you sure you want to delete '${expense.title}'?")
                    .setPositiveButton("Yes") { _, _ -> viewModel.deleteExpense(expense) }
                    .setNegativeButton("No", null)
                    .show()
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Thêm Long Click Listener cho RecyclerView để xử lý xóa
        viewModel.allExpenses.observe(this) { list ->
            adapter.setExpense(list)
        }

        // Observe danh sách expenses
        viewModel.allExpenses.observe(this) { list ->
            adapter.setExpense(list)
        }

        // Observe tổng tiền, xử lý null
        viewModel.totalExpense.observe(this) { total ->
            val safeTotal = total ?: 0.0
            binding.tvTotal.text = NumberFormat.getCurrencyInstance().format(safeTotal)
        }

        // Thêm expense mới
        binding.fabAdd.setOnClickListener {
            showEditOrCreateExpenseDialog(null) // null để tạo mới
        }
    }

    // Gộp hàm thêm và sửa thành một
    private fun showEditOrCreateExpenseDialog(expense: ExpenseModel?) {
        val dialogBinding = DialogAddExpenseBinding.inflate(layoutInflater)
        val isEditing = expense != null

        // Điền dữ liệu nếu là chế độ chỉnh sửa
        if (isEditing) {
            dialogBinding.etTitle.setText(expense!!.title)
            dialogBinding.etAmount.setText(expense.amount.toString())
            dialogBinding.etCategory.setText(expense.category)
        }

        val dialogTitle = if (isEditing) "Edit Expense" else "Add Expense"
        val positiveButtonText = if (isEditing) "Save" else "Add"

        val dialog = AlertDialog.Builder(this)
            .setTitle(dialogTitle)
            .setView(dialogBinding.root)
            .setPositiveButton(positiveButtonText) { _, _ ->
                val title = dialogBinding.etTitle.text.toString().trim()
                val amountText = dialogBinding.etAmount.text.toString().trim()
                val category = dialogBinding.etCategory.text.toString().trim()

                if (title.isEmpty()) {
                    dialogBinding.etTitle.error = "Title required"
                    return@setPositiveButton
                }

                val amount = amountText.toDoubleOrNull() ?: 0.0

                // Sử dụng lại id và date nếu là chỉnh sửa, ngược lại tạo mới
                val newOrUpdatedExpense = if (isEditing) {
                    expense!!.copy(
                        title = title,
                        amount = amount,
                        category = if (category.isEmpty()) "Other" else category
                    )
                } else {
                    ExpenseModel(
                        title = title,
                        amount = amount,
                        category = if (category.isEmpty()) "Other" else category,
                        date = System.currentTimeMillis()
                    )
                }

                if (isEditing) {
                    // Gọi ViewModel update
                    viewModel.updateExpense(newOrUpdatedExpense)
                } else {
                    // Gọi ViewModel insert
                    viewModel.insertExpense(newOrUpdatedExpense)
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}