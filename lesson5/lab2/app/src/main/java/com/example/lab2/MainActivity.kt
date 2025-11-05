package com.example.lab2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var spnrFragmentSelector: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spnrFragmentSelector = findViewById(R.id.spnrFragmentSelector)

        setupSpinner()

        if (savedInstanceState == null) {
            replaceFragment(Fragment1())
        }
    }

    private fun setupSpinner() {
        val fragmentNames = listOf("Fragment 1", "Fragment 2")

        // Sử dụng ArrayAdapter mặc định của Android
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item, // Layout cho Item trong Dropdown
            fragmentNames
        )
        // Gán layout cho Spinner khi không mở dropdown (phần hiển thị trên Activity)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnrFragmentSelector.adapter = adapter

        // Xử lý sự kiện khi chọn Fragment
        spnrFragmentSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // position: 0 là Fragment 1, 1 là Fragment 2
                when (position) {
                    0 -> replaceFragment(Fragment1())
                    1 -> replaceFragment(Fragment2())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    /**
     * Hàm thay thế Fragment trong FrameLayout
     */
    private fun replaceFragment(fragment: Fragment) {
        // Sử dụng FragmentManager để quản lý Transaction
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment) // Thay thế Fragment cũ bằng Fragment mới
            .commit() // Thực hiện giao dịch
    }
}