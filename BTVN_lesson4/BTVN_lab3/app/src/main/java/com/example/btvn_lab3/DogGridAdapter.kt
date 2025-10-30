package com.example.btvn_lab3

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView

class DogGridAdapter (val activity: Activity, val listDogs: List<DogModel>)
    : ArrayAdapter<DogModel>(activity, R.layout.layout_dog_item) {

    override fun getCount(): Int {
        return listDogs.size
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view: View = convertView ?: activity.layoutInflater.inflate(R.layout.layout_dog_item, parent, false)

        // Ánh xạ ImageView
        val imgDog = view.findViewById<ImageView>(R.id.imgDog)

        val dog = listDogs[position]

        // Gán hình ảnh
        imgDog.setImageResource(dog.imageId)

        return view
    }
}