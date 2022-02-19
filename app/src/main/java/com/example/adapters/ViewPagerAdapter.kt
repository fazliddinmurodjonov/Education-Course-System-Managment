package com.example.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.Groups.GroupsViewPagerElementFragment

class ViewPagerAdapter(var list: ArrayList<Int>,var courseId :Int,fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return GroupsViewPagerElementFragment.newInstance(list[position],courseId)
    }

}