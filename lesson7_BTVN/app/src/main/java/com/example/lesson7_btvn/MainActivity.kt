package com.example.lesson7_btvn

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson7_btvn.ui.adapter.AppointmentAdapter
import com.example.lesson7_btvn.data.model.Appointment
import com.example.lesson7_btvn.repository.AppointmentRepository
import com.example.lesson7_btvn.ui.viewmodel.AppointmentViewModel
import com.example.lesson7_btvn.ui.viewmodel.AppointmentViewModelFactory
import com.example.lesson7_btvn.worker.NotificationWorker
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: AppointmentViewModel
    private lateinit var appointmentAdapter: AppointmentAdapter
    private lateinit var rvAppointments: RecyclerView
    private lateinit var btnAdd: Button
    private lateinit var btnFilterFrom: Button
    private lateinit var btnFilterTo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo Repository và ViewModel
        val repository = AppointmentRepository(applicationContext)
        viewModel = ViewModelProvider(
            this,
            AppointmentViewModelFactory(repository)
        )[AppointmentViewModel::class.java]

        setupUI()
        setupListeners()
        setupObservers()

        // Khởi tạo kênh thông báo
        NotificationWorker.createNotificationChannel(this)

        // Dữ liệu mẫu (Tùy chọn)
        if (repository.appointments.value.isEmpty()) {
            val now = Calendar.getInstance().timeInMillis
            val tomorrow = now + TimeUnit.DAYS.toMillis(1)
            viewModel.addAppointment(
                "Họp dự án A",
                "Văn phòng 101",
                tomorrow,
                "https://placehold.co/100x100/38bdf8/white?text=A"
            )
            viewModel.addAppointment(
                "Ăn trưa với John",
                "Nhà hàng",
                tomorrow + TimeUnit.HOURS.toMillis(2),
                "https://placehold.co/100x100/fbbf24/white?text=J"
            )
        }
    }

    private fun setupUI() {
        rvAppointments = findViewById(R.id.rv_appointments)
        btnAdd = findViewById(R.id.btn_add_appointment)
        btnFilterFrom = findViewById(R.id.btn_filter_from)
        btnFilterTo = findViewById(R.id.btn_filter_to)

        appointmentAdapter = AppointmentAdapter(
            this,
            onDelete = { appointment ->
                showDeleteConfirmationDialog(appointment)
            },
            onUpdate = { appointment ->
                showUpdateAppointmentDialog(appointment)
            }
        )

        rvAppointments.layoutManager = LinearLayoutManager(this)
        rvAppointments.adapter = appointmentAdapter
    }

    private fun setupListeners() {
        btnAdd.setOnClickListener { showAddAppointmentDialog() }
        btnFilterFrom.setOnClickListener { pickDateTimeFilter(true) }
        btnFilterTo.setOnClickListener { pickDateTimeFilter(false) }
    }

    private fun setupObservers() {
        viewModel.filteredAppointments.observe(this) { appointments ->
            appointmentAdapter.submitList(appointments)
        }

        viewModel.filterState.asLiveData().observe(this) { filters ->
            val formatter = SimpleDateFormat("dd/MM/yy HH:mm", Locale("vi", "VN"))

            btnFilterFrom.text = filters.fromTimeMillis?.let { formatter.format(Date(it)) } ?: "Từ: Chọn giờ"
            btnFilterTo.text = filters.toTimeMillis?.let { formatter.format(Date(it)) } ?: "Đến: Chọn giờ"
        }
    }

    private fun showDeleteConfirmationDialog(appointment: Appointment) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận Xóa Lịch Hẹn")
            .setMessage("Bạn có chắc chắn muốn xóa lịch hẹn với '${appointment.name}' vào lúc ${appointment.displayDateTime}?")
            .setPositiveButton("Xóa") { dialog, _ ->
                viewModel.deleteAppointment(appointment.id)
                Toast.makeText(this, "Đã xóa lịch hẹn.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Không") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun pickDateTimeFilter(isFromFilter: Boolean) {
        val calendar = Calendar.getInstance()
        val currentMillis = if (isFromFilter) viewModel.filterState.value.fromTimeMillis else viewModel.filterState.value.toTimeMillis
        currentMillis?.let { calendar.timeInMillis = it }

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)

            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar.set(year, month, day)
                val finalTime = calendar.timeInMillis
                if (isFromFilter) viewModel.setFilterFrom(finalTime) else viewModel.setFilterTo(finalTime)
                Toast.makeText(this, "Đã áp dụng bộ lọc.", Toast.LENGTH_SHORT).show()
            }

            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        TimePickerDialog(
            this,
            timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            DateFormat.is24HourFormat(this)
        ).show()
    }

    private fun showAddAppointmentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_appointment, null)
        val dialog = AlertDialog.Builder(this).setView(dialogView).create()

        val etName: EditText = dialogView.findViewById(R.id.et_name)
        val btnSelectDateTime: Button = dialogView.findViewById(R.id.btn_select_date_time)
        val etLocation: EditText = dialogView.findViewById(R.id.et_location)
        val etImageUrl: EditText = dialogView.findViewById(R.id.et_image_url)
        val btnSave: Button = dialogView.findViewById(R.id.btn_save_appointment)

        var selectedTimeMillis: Long = 0L
        val calendar = Calendar.getInstance()

        btnSelectDateTime.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)

                val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    calendar.set(year, month, day)
                    selectedTimeMillis = calendar.timeInMillis
                    val formatter = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale("vi", "VN"))
                    btnSelectDateTime.text = formatter.format(Date(selectedTimeMillis))
                }

                DatePickerDialog(
                    this,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            TimePickerDialog(
                this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this)
            ).show()
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val location = etLocation.text.toString().trim()
            val imageUrl = etImageUrl.text.toString().trim()

            if (name.isEmpty() || location.isEmpty() || selectedTimeMillis == 0L) {
                Toast.makeText(this, "Vui lòng nhập đủ Họ Tên, Địa điểm và Ngày Giờ.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.addAppointment(name, location, selectedTimeMillis, imageUrl)
            Toast.makeText(this, "Đã thêm lịch hẹn mới!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showUpdateAppointmentDialog(appointment: Appointment) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_appointment, null)
        val dialog = AlertDialog.Builder(this).setView(dialogView).create()

        val etName: EditText = dialogView.findViewById(R.id.et_name)
        val btnSelectDateTime: Button = dialogView.findViewById(R.id.btn_select_date_time)
        val etLocation: EditText = dialogView.findViewById(R.id.et_location)
        val etImageUrl: EditText = dialogView.findViewById(R.id.et_image_url)
        val btnSave: Button = dialogView.findViewById(R.id.btn_save_appointment)

        var selectedTimeMillis = appointment.startTimeMillis
        val formatter = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale("vi", "VN"))

        // Gán dữ liệu cũ
        etName.setText(appointment.name)
        etLocation.setText(appointment.location)
        etImageUrl.setText(appointment.imageUrl)
        btnSelectDateTime.text = formatter.format(Date(selectedTimeMillis))

        val calendar = Calendar.getInstance().apply {
            timeInMillis = selectedTimeMillis
        }

        btnSelectDateTime.setOnClickListener {
            val timeSet = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)

                val dateSet = DatePickerDialog.OnDateSetListener { _, y, m, d ->
                    calendar.set(y, m, d)
                    selectedTimeMillis = calendar.timeInMillis
                    btnSelectDateTime.text = formatter.format(Date(selectedTimeMillis))
                }

                DatePickerDialog(
                    this, dateSet,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            TimePickerDialog(
                this, timeSet,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this)
            ).show()
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val location = etLocation.text.toString().trim()
            val imageUrl = etImageUrl.text.toString().trim()

            if (name.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Xóa cũ + thêm mới (vì ID random)
            viewModel.deleteAppointment(appointment.id)
            viewModel.addAppointment(name, location, selectedTimeMillis, imageUrl)

            Toast.makeText(this, "Đã cập nhật lịch hẹn!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
}
