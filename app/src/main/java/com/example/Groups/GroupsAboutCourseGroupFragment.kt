package com.example.Groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.adapters.ViewPagerAdapter
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentGroupsAboutCourseGroupBinding
import com.example.db.PdpDb
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GroupsAboutCourseGroupFragment : Fragment() {
    lateinit var binding: FragmentGroupsAboutCourseGroupBinding
    lateinit var pdpDb: PdpDb
    lateinit var viewPagerList: ArrayList<Int>
    var currentItem: Int? = null
    var onResumeChecker = false
    lateinit var viewPagerAdapter: ViewPagerAdapter
    var courseId: Int = 0
    lateinit var loadTabLayoutList: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGroupsAboutCourseGroupBinding.inflate(inflater, container, false)
        pdpDb = PdpDb(requireContext())
        loadRV()

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = loadTabLayoutList[position]
        }.attach()
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.addGroup.setOnClickListener {
            currentItem = binding.viewPager.currentItem
            val bundleOf = bundleOf("course_id" to courseId)
            findNavController().navigate(R.id.groupsAddGroupFragment, bundleOf)
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                if (tab?.position != 0) {
                    binding.addGroup.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

                if (tab?.position != 0) {
                    binding.addGroup.visibility = View.INVISIBLE
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        return binding.root

    }

    private fun loadRV() {
        loadTabLayoutList = loadTabLayoutList()
        courseId = arguments?.getInt("course_id")!!
        viewPagerList = ArrayList<Int>()
        viewPagerList.add(1)
        viewPagerList.add(0)
        viewPagerAdapter = ViewPagerAdapter(viewPagerList, courseId!!, requireActivity())
        binding.viewPager.adapter = viewPagerAdapter
    }

    private fun loadTabLayoutList(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add("Opened groups")
        list.add("Opening groups")
        return list
    }

    override fun onResume() {
        super.onResume()
        if (onResumeChecker) {
            viewPagerAdapter = ViewPagerAdapter(viewPagerList, courseId!!, requireActivity())
            binding.viewPager.adapter = viewPagerAdapter
        }

    }

    override fun onPause() {
        super.onPause()
        onResumeChecker = false
    }


    override fun onDestroyView() {
        onResumeChecker = true
        super.onDestroyView()
    }


}