package com.example.lesson7_btvn.worker

import android.content.Context
import androidx.work.WorkerParameters
import androidx.work.Worker
import android.os.Build
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import android.widget.Toast
import com.example.lesson7_btvn.data.model.Appointment
import com.example.lesson7_btvn.MainActivity

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val appointmentName =
            inputData.getString("APPOINTMENT_NAME") ?: return Result.failure()
        val appointmentTime =
            inputData.getString("APPOINTMENT_TIME") ?: return Result.failure()
        val notificationId = inputData.getString("APPOINTMENT_ID")?.hashCode() ?: 0

        showNotification(applicationContext, notificationId, appointmentName, appointmentTime)
        return Result.success()
    }

    private fun showNotification(context: Context, id: Int, title: String, time: String) {
        val channelId = "appointment_channel"
        val channelName = "Lịch hẹn cá nhân"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("SẮP ĐẾN LỊCH HẸN: $title")
            .setContentText("Lịch hẹn bắt đầu lúc $time (30 phút nữa).")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(id, notification)
    }

    companion object {
        private const val NOTIFICATION_TIME_MINUTES = 30L

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "appointment_channel"
                val channelName = "Lịch hẹn cá nhân"
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                )
                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
        }

        fun scheduleNotification(context: Context, appointment: Appointment) {
            val delayMillis = appointment.startTimeMillis -
                    System.currentTimeMillis() -
                    TimeUnit.MINUTES.toMillis(NOTIFICATION_TIME_MINUTES)

            if (delayMillis > 0) {
                val inputData = androidx.work.Data.Builder()
                    .putString("APPOINTMENT_NAME", appointment.name)
                    .putString("APPOINTMENT_TIME", appointment.displayDateTime)
                    .putString("APPOINTMENT_ID", appointment.id)
                    .build()

                val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                    .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
                    .addTag(appointment.id)
                    .setInputData(inputData)
                    .build()

                WorkManager.getInstance(context).enqueue(workRequest)
            } else {
                Toast.makeText(
                    context,
                    "Lịch hẹn quá gần, không thể lên lịch thông báo.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
