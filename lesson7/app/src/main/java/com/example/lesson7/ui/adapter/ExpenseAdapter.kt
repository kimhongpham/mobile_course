package com.example.lesson7.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson7.R
import com.example.lesson7.data.model.ExpenseModel
import com.example.lesson7.databinding.ExpenseItemBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter(
    private val onEditClick: (ExpenseModel) -> Unit,
    private val onDeleteClick: (ExpenseModel) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private var expenses = listOf<ExpenseModel>()

    class ExpenseViewHolder(val binding: ExpenseItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ExpenseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val currentExpense = expenses[position]

        holder.binding.tvTitle.text = currentExpense.title
        holder.binding.tvAmount.text = NumberFormat.getCurrencyInstance().format(currentExpense.amount)
        holder.binding.tvCategory.text = currentExpense.category
        holder.binding.tvDate.text =
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(currentExpense.date))

        holder.binding.ivCategoryIcon.setImageResource(
            when (currentExpense.category.lowercase(Locale.getDefault())) {
                "food" -> R.drawable.ic_food
                "transport" -> R.drawable.ic_bus
                else -> R.drawable.ic_wallet
            }
        )

        // Click ngắn để chỉnh sửa
        holder.itemView.setOnClickListener {
            onEditClick(currentExpense)
        }

        // Click dài để xóa
        holder.itemView.setOnLongClickListener {
            onDeleteClick(currentExpense)
            true
        }

    }

    override fun getItemCount(): Int = expenses.size

    fun setExpense(list: List<ExpenseModel>) {
        expenses = list
        notifyDataSetChanged()
    }
}