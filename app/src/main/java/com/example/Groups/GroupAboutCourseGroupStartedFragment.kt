package com.example.Groups

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.adapters.StudentRecyclerViewAdapter
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentGroupAboutCourseGroupStartedBinding
import com.example.room.Database.PdpDatabase
import com.example.room.Entity.Student
import com.example.utils.Constant

class GroupAboutCourseGroupStartedFragment : Fragment() {

    lateinit var binding: FragmentGroupAboutCourseGroupStartedBinding
    private var groupId: Int = 0
    lateinit var pdpDb: PdpDatabase
    lateinit var studentRecyclerViewAdapter: StudentRecyclerViewAdapter
    lateinit var studentList: ArrayList<Student>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGroupAboutCourseGroupStartedBinding.inflate(inflater, container, false)
        pdpDb = PdpDatabase.getInstance(requireContext())
        groupId = arguments?.getInt("group_id")!!
        val group = pdpDb.GroupsDao().getGroupById(groupId)
        studentList.addAll(pdpDb.StudentWithGroupDao().getStudentsByGroup(groupId).students)
        binding.groupNameToolBar.text = group.name
        binding.groupName.text = group.name
        binding.numberOfStudents.text = group.studentCount.toString()
        binding.time.text = group.time
        studentRecyclerViewAdapter = StudentRecyclerViewAdapter(studentList)
        binding.studentRV.adapter = studentRecyclerViewAdapter
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        studentRecyclerViewAdapter.setOnItemDelete(object :
            StudentRecyclerViewAdapter.OnItemDelete {
            override fun onClick(student: Student, position: Int) {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setMessage("Do you want to delete this student?")
                alertDialog.setCancelable(true)
                alertDialog.setPositiveButton("Yes"
                ) { _, _ ->
                    studentList.remove(student)
                    studentRecyclerViewAdapter.notifyItemRemoved(studentList.size)
                    studentRecyclerViewAdapter.notifyItemRangeRemoved(position, studentList.size)
                    pdpDb.StudentDao().deleteStudent(student)
                    group.studentCount = group.studentCount!! - 1
                    pdpDb.GroupsDao().updateGroup(group)
                    binding.numberOfStudents.text = group.studentCount.toString()
                }
                alertDialog.setNegativeButton("No"
                ) { _, _ -> }
                alertDialog.show()
            }

        })

        studentRecyclerViewAdapter.setOnItemEdit(object :
            StudentRecyclerViewAdapter.OnItemEdit {
            override fun onClick(student: Student, position: Int) {
                val bundleOf = bundleOf("student_id" to student.id)
                findNavController().navigate(R.id.groupsEditStudentFragment, bundleOf)
            }

        })

        return binding.root
    }

}