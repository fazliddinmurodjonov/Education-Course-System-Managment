package com.example.Groups

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentGroupsAddStudentBinding
import com.example.room.Database.PdpDatabase
import com.example.room.Entity.Student
import com.example.util.Empty


class GroupsAddStudentFragment : Fragment() {
    lateinit var binding: FragmentGroupsAddStudentBinding
    lateinit var pdpDb: PdpDatabase
    lateinit var datePickerDialog: DatePickerDialog
    var groupId: Int? = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        pdpDb = PdpDatabase.getInstance(requireContext())
        groupId = arguments?.getInt("group_id")
        binding = FragmentGroupsAddStudentBinding.inflate(inflater, container, false)
        binding.dateCalendar.setOnClickListener {
            datePickerDialog = DatePickerDialog(requireContext())
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                binding.addDateToThisGroup.setText("$dayOfMonth/${month + 1}/$year")
            }
            datePickerDialog.show()
        }
        binding.addStudentToThisGroup.setOnClickListener {
            val firstname = binding.addFirstnameToThisGroup.text.toString()
            val lastname = binding.addLastnameToThisGroup.text.toString()
            val fatherSName = binding.addFathersNameToThisGroup.text.toString()
            val time = binding.addDateToThisGroup.text.toString()
            val firstnameBol = Empty.empty(firstname)
            val lastnameBol = Empty.empty(lastname)
            val fatherSNameBol = Empty.empty(fatherSName)
            val timeBol = Empty.empty(time)
            val firstnameSpace = Empty.space(firstname)
            val lastnameSpace = Empty.space(lastname)
            val fatherSNameSpace = Empty.space(fatherSName)
            val timeSpace = Empty.space(time)
            val bol = firstnameBol && lastnameBol && fatherSNameBol && timeBol
            val space = firstnameSpace && lastnameSpace && fatherSNameSpace && timeSpace
            if (bol && space) {
                val student = Student(firstname, lastname, fatherSName, time, groupId)
                pdpDb.StudentDao().insertStudent(student)
                val group = pdpDb.GroupsDao().getGroupById(groupId!!)
                group.studentCount = group.studentCount!! + 1
                pdpDb.GroupsDao().updateGroup(group)
                findNavController().popBackStack()
            }
        }
        return binding.root
    }
}