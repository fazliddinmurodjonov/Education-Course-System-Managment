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
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentGroupsAboutCourseGroupDetailsBinding
import com.example.db.PdpDb
import com.example.room.entity.Student
import com.example.utils.Constant

class GroupsAboutCourseGroupDetailsFragment : Fragment() {

    lateinit var binding: FragmentGroupsAboutCourseGroupDetailsBinding
    private var groupId: Int = 0
    lateinit var pdpDb: PdpDb
    var query: String = ""
    lateinit var studentRecyclerViewAdapter: StudentRecyclerViewAdapter
    lateinit var studentList: ArrayList<Student>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGroupsAboutCourseGroupDetailsBinding.inflate(inflater, container, false)

        pdpDb = PdpDb(requireContext())
        groupId = arguments?.getInt("group_id")!!
        val group = pdpDb.getGroupById(groupId)
        query =
            "select *from ${Constant.STUDENT_TABLE} WHERE ${Constant.STUDENT_GROUP_ID} = $groupId"
        studentList = pdpDb.getAllStudent(query)
        binding.groupNameToolBar.text = group.name
        binding.groupName.text = group.name
        binding.numberOfStudents.text = group.studentSCount.toString()
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
                    pdpDb.deleteStudent(student)
                    group.studentSCount = group.studentSCount - 1
                    pdpDb.updateGroup(group)
                    binding.numberOfStudents.text = group.studentSCount.toString()
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
        binding.addStudentToGroup.setOnClickListener {
            val bundleOf = bundleOf("group_id" to groupId)
            findNavController().navigate(R.id.groupsAddStudentFragment, bundleOf)
        }
        binding.startLessonToGroup.setOnClickListener {

            val allStudentList = pdpDb.getAllStudent(query)
            if (allStudentList.size != 0) {
                group.lessonStart = 1
                pdpDb.updateGroup(group)
                findNavController().popBackStack()
            }


        }

        return binding.root
    }


}