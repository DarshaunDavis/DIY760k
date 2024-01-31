package com.lislal.diy760

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3 // Number of tabs

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LeftFragment()    // LeftFragment as the first tab
            1 -> MainFragment()    // MainFragment as the second (central) tab
            else -> RightFragment() // RightFragment as the third tab
        }
    }
}
