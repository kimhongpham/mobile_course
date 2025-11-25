package com.example.lesson7_btvn.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lesson7_btvn.R
import com.example.lesson7_btvn.data.model.Appointment

class AppointmentAdapter(
    private val context: Context,
    private val onDelete: (Appointment) -> Unit,
    private val onUpdate: (Appointment) -> Unit
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    private var appointments: List<Appointment> = emptyList()

    fun submitList(newAppointments: List<Appointment>) {
        appointments = newAppointments
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.bind(appointment)

        // CLICK NGẮN → SỬA
        holder.itemView.setOnClickListener {
            onUpdate(appointment)
        }

        // NHẤN GIỮ → XÓA
        holder.itemView.setOnLongClickListener {
            onDelete(appointment)
            true
        }
    }

    override fun getItemCount() = appointments.size

    inner class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_appointment_name)
        private val timeTextView: TextView = itemView.findViewById(R.id.tv_appointment_time)
        private val locationTextView: TextView = itemView.findViewById(R.id.tv_appointment_location)
        private val imageView: ImageView = itemView.findViewById(R.id.iv_appointment_image)

        fun bind(appointment: Appointment) {
            nameTextView.text = appointment.name
            timeTextView.text = appointment.displayDateTime
            locationTextView.text = appointment.location

            // Load ảnh từ URL bằng Glide
            Glide.with(context)
                .load(appointment.imageUrl)
                .placeholder(R.drawable.ic_placeholder_person) // Placeholder mặc định
                .error(R.drawable.ic_error_image) // Ảnh lỗi
                .circleCrop() // Bo tròn ảnh đại diện
                .into(imageView)
        }
    }
}
