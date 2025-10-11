package com.example.lesson2

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.lesson2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bt11.setOnClickListener {
            startActivity(Intent(this, BT11Activity::class.java))
        }

        binding.bt12.setOnClickListener {
            startActivity(Intent(this, BT12Activity::class.java))
        }

        binding.bt13.setOnClickListener {
            startActivity(Intent(this, BT13Activity::class.java))
        }

        binding.bt14.setOnClickListener {
            startActivity(Intent(this, BT14Activity::class.java))
        }

        binding.bt15.setOnClickListener {
            startActivity(Intent(this, BT15Activity::class.java))
        }

        binding.bt16.setOnClickListener {
            startActivity(Intent(this, BT16Activity::class.java))
        }
    }
}