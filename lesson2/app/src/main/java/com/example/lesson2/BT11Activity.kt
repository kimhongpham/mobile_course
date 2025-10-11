package com.example.lesson2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson2.databinding.ActivityBt11Binding

class BT11Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBt11Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBt11Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            binding.button1.text = "Button 1 Clicked!"
        }
    }
}