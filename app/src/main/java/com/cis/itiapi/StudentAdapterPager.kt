package com.cis.itiapi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class StudentAdapterPager(fragmentManager: FragmentManager,lifecycle: Lifecycle
):FragmentStateAdapter(fragmentManager,lifecycle) {
    val fragments= arrayOf(FragmentD(),FragmentF())
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}