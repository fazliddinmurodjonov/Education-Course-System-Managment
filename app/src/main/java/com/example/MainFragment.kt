package com.example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.buttonCourses.setOnClickListener {
            findNavController().navigate(R.id.coursesAllCoursesFragment)
        }
        binding.buttonMentors.setOnClickListener {
            findNavController().navigate(R.id.mentorsAllCoursesFragment)
        }
        binding.buttonGroups.setOnClickListener {
            findNavController().navigate(R.id.groupsAllCoursesFragment)
        }
        return binding.root
    }


}