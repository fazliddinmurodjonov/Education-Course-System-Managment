package com.example.Mentors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.adapters.CourseRecyclerViewAdapter
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentMentorsAllCoursesBinding
import com.example.db.PdpDb
import com.example.room.entity.Course


class MentorsAllCoursesFragment : Fragment() {

    lateinit var binding: FragmentMentorsAllCoursesBinding
    lateinit var pdpDb: PdpDb
    lateinit var courseRecyclerViewAdapter: CourseRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMentorsAllCoursesBinding.inflate(inflater, container, false)
        pdpDb = PdpDb(requireContext())
        val courseList = pdpDb.getAllCourse()
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        courseRecyclerViewAdapter = CourseRecyclerViewAdapter(courseList)
        binding.allCoursesInMentorsRV.adapter = courseRecyclerViewAdapter
        courseRecyclerViewAdapter.setOnMyItemClickListener(object :
            CourseRecyclerViewAdapter.OnMyItemClickListener {
            override fun onClick(course: Course) {
                val bundleOf = bundleOf("course_id" to course.id, "course_name" to course.name)
                findNavController().navigate(R.id.mentorsCourseMentorsFragment, bundleOf)
            }

        })
        return binding.root
    }

}