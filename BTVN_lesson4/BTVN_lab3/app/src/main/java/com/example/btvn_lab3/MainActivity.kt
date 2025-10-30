package com.example.btvn_lab3

import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Chuẩn bị Dữ liệu
        val listDogs = mutableListOf<DogModel>()

        // Tạo dữ liệu mẫu
        listDogs.add(DogModel(R.drawable.dog1, "Milu", "Jack Russell Terrier", 3))
        listDogs.add(DogModel(R.drawable.dog2, "Lulu", "Husky lai", 1))
        listDogs.add(DogModel(R.drawable.dog3, "Max", "Dalmatian", 5))
        listDogs.add(DogModel(R.drawable.dog4, "Bella", "Pitbull", 2))
        listDogs.add(DogModel(R.drawable.dog5, "Coffee", "Poodle", 4))
        listDogs.add(DogModel(R.drawable.dog6, "Ginger", "Corgi", 1))
        listDogs.add(DogModel(R.drawable.dog7, "Rocky", "Golden Retriever", 2))
        listDogs.add(DogModel(R.drawable.dog8, "Coco", "Shiba Inu", 3))
        listDogs.add(DogModel(R.drawable.dog9, "Buddy", "Beagle", 4))
        listDogs.add(DogModel(R.drawable.dog10, "Daisy", "Chihuahua", 1))
        listDogs.add(DogModel(R.drawable.dog11, "Lucky", "Labrador", 5))
        listDogs.add(DogModel(R.drawable.dog12, "Mango", "Samoyed", 2))

        // Gán Adapter cho GridView
        val gvDogs = findViewById<GridView>(R.id.gvDogs)
        val adapter = DogGridAdapter(this, listDogs)
        gvDogs.adapter = adapter

        // Thêm Tương Tác Click
        gvDogs.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedDog = listDogs[position]

            // Hiện Toast thông tin chi tiết
            val info = "Tên: ${selectedDog.name}, Giống: ${selectedDog.breed}, Tuổi: ${selectedDog.age}"
            Toast.makeText(this, info, Toast.LENGTH_LONG).show()
        }
    }
}