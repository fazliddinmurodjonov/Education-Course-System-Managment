package com.example.Groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.adapters.CourseRecyclerViewAdapter
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentGroupsAllCoursesBinding
import com.example.room.Database.PdpDatabase
import com.example.room.Entity.Course

class GroupsAllCoursesFragment : Fragment() {
    lateinit var binding: FragmentGroupsAllCoursesBinding
    lateinit var pdpDb: PdpDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGroupsAllCoursesBinding.inflate(inflater, container, false)
        pdpDb = PdpDatabase.getInstance(requireContext())
        val courseList = ArrayList<Course>()
        courseList.addAll(pdpDb.CourseDao().getAllCourse())
        var courseRecyclerViewAdapter = CourseRecyclerViewAdapter(courseList)
        binding.allCoursesInGroupsRV.adapter = courseRecyclerViewAdapter

        courseRecyclerViewAdapter.setOnMyItemClickListener(object :
            CourseRecyclerViewAdapter.OnMyItemClickListener {
            override fun onClick(course: Course) {
                val bundleOf = bundleOf("course_id" to course.courseId)
                findNavController().navigate(R.id.groupsAboutCourseGroupFragment, bundleOf)
            }

        })
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
}