package com.example.lab3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Danh sách các Fragment sẽ được hiển thị
    private val fragments = listOf(
        Tab1Fragment(),
        Tab2Fragment(),
        Tab3Fragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}