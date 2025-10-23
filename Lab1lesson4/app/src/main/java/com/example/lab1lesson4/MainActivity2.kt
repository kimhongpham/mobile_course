package com.example.lab1lesson4

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val btnClose: Button = findViewById(R.id.btn_close_2)
        btnClose.setOnClickListener {
            finish()
        }

        showToast("MainActivity2: onCreate()")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.d("Lifecycle", message)
    }

    override fun onStart() {
        super.onStart()
        showToast("${this::class.simpleName}: onStart()")
    }

    override fun onResume() {
        super.onResume()
        showToast("${this::class.simpleName}: onResume()")
    }

    override fun onPause() {
        super.onPause()
        showToast("${this::class.simpleName}: onPause()")
    }

    override fun onStop() {
        super.onStop()
        showToast("${this::class.simpleName}: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        showToast("${this::class.simpleName}: onDestroy()")
    }

    override fun onRestart() {
        super.onRestart()
        showToast("${this::class.simpleName}: onRestart()")
    }
}