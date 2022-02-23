package com.example.Courses

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.adapters.CourseRecyclerViewAdapter
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.CustomDialogCourseAddBinding
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentCoursesAllCoursesBinding
import com.example.room.Database.PdpDatabase
import com.example.room.Entity.Course
import com.example.util.Empty

class CoursesAllCoursesFragment : Fragment() {
    lateinit var binding: FragmentCoursesAllCoursesBinding
    lateinit var pdpDb: PdpDatabase
    lateinit var courseRecyclerViewAdapter: CourseRecyclerViewAdapter
    lateinit var courseList: ArrayList<Course>

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCoursesAllCoursesBinding.inflate(inflater, container, false)
        pdpDb = PdpDatabase.getInstance(requireContext())
        loadRV()


        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.addCourse.setOnClickListener {
            val dialog = Dialog(requireContext())
            val dialogItemView =
                CustomDialogCourseAddBinding.inflate(LayoutInflater.from(requireContext()),
                    null,
                    false)
            dialog.setContentView(dialogItemView.root)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            dialogItemView.saveTv.setOnClickListener {
                var courseName = dialogItemView.courseName.text.toString()
                val courseAbout = dialogItemView.aboutCourse.text.toString()
                val courseNameBol = Empty.empty(courseName)
                val courseAboutBol = Empty.empty(courseAbout)
                val courseNameSpace = Empty.space(courseName)
                val courseAboutSpace = Empty.space(courseAbout)
                var uniqueCourse = true
                val allCourseList = ArrayList<Course>()
                allCourseList.addAll(pdpDb.CourseDao().getAllCourse())

                for (course in allCourseList) {
                    val name = course.name?.replace("\nDevelopment", "")
                    if (name == courseName) {
                        uniqueCourse = false
                        break
                    }
                }
                if (courseNameBol && courseAboutBol && courseNameSpace && courseAboutSpace && uniqueCourse) {
                    courseName += "\nDevelopment"

                    val course = Course(courseName, courseAbout)
                    pdpDb.CourseDao().insertCourse(course)
                    dialog.dismiss()
                    loadRV()
                    courseRecyclerViewAdapter.setOnMyItemClickListener(object :
                        CourseRecyclerViewAdapter.OnMyItemClickListener {
                        override fun onClick(course: Course) {
                            val bundleOf = bundleOf("course_id" to course.courseId)
                            findNavController().navigate(R.id.coursesAboutCourseFragment, bundleOf)
                        }
                    })
                    binding.allCoursesInCoursesRV.adapter = courseRecyclerViewAdapter

                }
            }
            dialogItemView.cancelTv.setOnClickListener {
                dialog.dismiss()
            }
        }
        courseRecyclerViewAdapter.setOnMyItemClickListener(object :
            CourseRecyclerViewAdapter.OnMyItemClickListener {
            override fun onClick(course: Course) {
                val bundleOf = bundleOf("course_id" to course.courseId)
                findNavController().navigate(R.id.coursesAboutCourseFragment, bundleOf)
            }

        })

        return binding.root
    }

    fun loadRV() {
        courseList = ArrayList()
        courseList.addAll(pdpDb.CourseDao().getAllCourse())
        courseRecyclerViewAdapter = CourseRecyclerViewAdapter(courseList)
        binding.allCoursesInCoursesRV.adapter = courseRecyclerViewAdapter
    }


    override fun onResume() {
        super.onResume()
    }
}