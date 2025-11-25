package com.example.lesson7_btvn.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.work.WorkManager
import com.example.lesson7_btvn.data.model.Appointment
import com.example.lesson7_btvn.worker.NotificationWorker.Companion.scheduleNotification
import com.google.gson.Gson
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow

class AppointmentRepository(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("appointments_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()
    private val APPOINTMENTS_KEY = "appointments_list"

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    init {
        loadAppointments()
    }

    private fun loadAppointments() {
        val json = prefs.getString(APPOINTMENTS_KEY, null)
        val type = object : com.google.gson.reflect.TypeToken<List<Appointment>>() {}.type
        val list: List<Appointment> = json?.let { gson.fromJson(it, type) } ?: emptyList()
        _appointments.value = list
    }

    private fun saveAppointments(list: List<Appointment>) {
        val json = gson.toJson(list)
        prefs.edit().putString(APPOINTMENTS_KEY, json).apply()
        _appointments.value = list
    }

    fun addAppointment(appointment: Appointment) {
        val currentList = _appointments.value.toMutableList()
        currentList.add(0, appointment)
        saveAppointments(currentList)

        // Lên lịch thông báo
        scheduleNotification(context, appointment)
    }

    fun deleteAppointment(appointmentId: String) {
        val currentList = _appointments.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == appointmentId }

        if (index != -1) {
            val appointment = currentList[index]
            currentList.removeAt(index)
            saveAppointments(currentList)

            // Hủy lịch thông báo
            WorkManager.getInstance(context).cancelAllWorkByTag(appointment.id)
        }
    }
}
