package com.example.Courses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentCoursesAboutCourseBinding
import com.example.db.PdpDb

class CoursesAboutCourseFragment : Fragment() {

    lateinit var binding: FragmentCoursesAboutCourseBinding
    lateinit var pdpDb: PdpDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCoursesAboutCourseBinding.inflate(inflater, container, false)
        pdpDb = PdpDb(requireContext())
        val courseId = arguments?.getInt("course_id")
        val course = pdpDb.getCourseById(courseId!!)
        binding.courseName.text = course.name?.replace("\n", " ")
        binding.courseAbout.text = course.about
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.addStudent.setOnClickListener {
            val bundleOf = bundleOf("course_id" to courseId)
            findNavController().navigate(R.id.coursesAddStudentFragment, bundleOf)
        }
        return binding.root

    }

}