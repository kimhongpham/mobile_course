package com.example.btvn_lab4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FolderAdapter(
    private val listFolders: MutableList<FolderModel>,
    private val clickListener: (FolderModel, Int) -> Unit
) : RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_folder, parent, false)
        return FolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folder = listFolders[position]
        holder.tvTitle.text = folder.title
        holder.tvDescription.text = folder.description

        holder.itemView.setOnClickListener {
            clickListener(folder, position) // Xử lý click và truyền vị trí
        }
    }

    override fun getItemCount(): Int = listFolders.size

    // Hàm cập nhật dữ liệu sau khi chỉnh sửa/thêm mới
    fun updateItem(position: Int, newFolder: FolderModel) {
        listFolders[position] = newFolder
        notifyItemChanged(position)
    }

    fun addItem(newFolder: FolderModel) {
        listFolders.add(newFolder)
        notifyItemInserted(listFolders.size - 1)
    }
}