package com.cis.itiapi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyAdapter(fragmentActivity:FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    val fragments= arrayOf(FragmentA(),FragmentB())
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}