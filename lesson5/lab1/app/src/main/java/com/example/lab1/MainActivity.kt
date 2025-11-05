package com.example.lab1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.databinding.ActivityMainBinding
import com.example.lab1.CustomSpinnerModel
import com.example.lab1.CustomSpinnerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Khởi tạo List, Adapter và gán cho Spinner
        setupSpinner()
    }

    private fun setupSpinner() {
        val list = ArrayList<CustomSpinnerModel>()
        list.add(CustomSpinnerModel(R.drawable.bmw, "BMW"))
        list.add(CustomSpinnerModel(R.drawable.mercedes, "Mercedes"))
        list.add(CustomSpinnerModel(R.drawable.toyota, "Toyota"))
        list.add(CustomSpinnerModel(R.drawable.ferrari, "Ferrari"))
        list.add(CustomSpinnerModel(R.drawable.honda, "Honda"))
        list.add(CustomSpinnerModel(R.drawable.chevy, "Chevy"))
        list.add(CustomSpinnerModel(R.drawable.porsche, "Porsche"))
        list.add(CustomSpinnerModel(R.drawable.cadillac, "Cadillac"))

        val adapter = CustomSpinnerAdapter(this, list)
        binding.spnrMenuCustom.adapter = adapter

        addEvent(list)
    }

    private fun addEvent(list : ArrayList<CustomSpinnerModel>) {
        binding.spnrMenuCustom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Hiển thị Toast thông báo hãng xe vừa chọn
                Toast.makeText(this@MainActivity, "Vừa chọn " + list[position].name, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}