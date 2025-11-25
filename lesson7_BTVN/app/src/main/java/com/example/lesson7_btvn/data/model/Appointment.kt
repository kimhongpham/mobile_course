package com.example.lesson7_btvn.data.model

import java.text.SimpleDateFormat
import java.util.UUID
import java.util.Locale
import java.util.Date

data class Appointment(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val location: String,
    val startTimeMillis: Long,
    val imageUrl: String
) {
    val displayDateTime: String
        get() {
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("vi", "VN"))
            return formatter.format(Date(startTimeMillis))
        }
}