package com.example.lesson7_btvn.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson7_btvn.repository.AppointmentRepository
import androidx.lifecycle.ViewModelProvider

class AppointmentViewModelFactory(private val repository: AppointmentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppointmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppointmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// Hàm mở rộng để kết hợp hai LiveData
fun <T, K, R> LiveData<T>.combineWith(
    liveData2: LiveData<K>,
    combiner: (T?, K?) -> R
): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(this) {
        result.value = combiner(it, liveData2.value)
    }
    result.addSource(liveData2) {
        result.value = combiner(this.value, it)
    }
    return result
}