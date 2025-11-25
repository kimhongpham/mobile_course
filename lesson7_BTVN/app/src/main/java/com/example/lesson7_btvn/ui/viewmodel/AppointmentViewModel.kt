package com.example.lesson7_btvn.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.lesson7_btvn.data.model.Appointment
import com.example.lesson7_btvn.repository.AppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

class AppointmentViewModel(private val repository: AppointmentRepository) : ViewModel() {

    // Flow danh sách lịch hẹn gốc
    private val allAppointmentsFlow: StateFlow<List<Appointment>> = repository.appointments

    // StateFlow lưu trạng thái filter
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState

    // LiveData filteredAppointments, kết hợp allAppointmentsFlow + filterState
    val filteredAppointments: LiveData<List<Appointment>> = combine(
        allAppointmentsFlow,
        _filterState
    ) { appointments, filters ->
        filterList(appointments, filters)
    }.asLiveData()

    // Data class lưu trạng thái filter
    data class FilterState(
        val fromTimeMillis: Long? = null,
        val toTimeMillis: Long? = null
    )

    // Hàm lọc danh sách theo filter
    private fun filterList(list: List<Appointment>, filters: FilterState): List<Appointment> {
        return list.filter { appointment ->
            val matchesFrom = filters.fromTimeMillis?.let { appointment.startTimeMillis >= it } ?: true
            val matchesTo = filters.toTimeMillis?.let { appointment.startTimeMillis <= it } ?: true
            matchesFrom && matchesTo
        }
    }

    // Cập nhật filter từ thời gian
    fun setFilterFrom(timeMillis: Long?) {
        _filterState.update { it.copy(fromTimeMillis = timeMillis) }
    }

    fun setFilterTo(timeMillis: Long?) {
        _filterState.update { it.copy(toTimeMillis = timeMillis) }
    }

    // Thêm lịch hẹn mới
    fun addAppointment(name: String, location: String, timeMillis: Long, imageUrl: String) {
        if (name.isBlank() || location.isBlank() || timeMillis == 0L) return
        val newAppointment = Appointment(
            name = name.trim(),
            location = location.trim(),
            startTimeMillis = timeMillis,
            imageUrl = imageUrl.trim()
        )
        repository.addAppointment(newAppointment)
    }

    // Xóa lịch hẹn
    fun deleteAppointment(appointmentId: String) {
        repository.deleteAppointment(appointmentId)
    }
}
