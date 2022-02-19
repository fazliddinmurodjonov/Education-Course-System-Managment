package com.example.Groups

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentGroupsAddStudentBinding
import com.example.db.PdpDb
import com.example.models.Student


class GroupsAddStudentFragment : Fragment() {
    lateinit var binding: FragmentGroupsAddStudentBinding
    lateinit var pdpDb: PdpDb
    lateinit var datePickerDialog: DatePickerDialog
    var groupId: Int? = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        pdpDb = PdpDb(requireContext())
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
            val notEmpty = notEmpty(firstname)
            val notEmpty1 = notEmpty(lastname)
            val notEmpty2 = notEmpty(fatherSName)
            val notEmpty3 = notEmpty(time)

            if (firstname != " " && lastname != " " && fatherSName != " " && time != " " && notEmpty
                && notEmpty1 && notEmpty2 && notEmpty3
            ) {
                val student = Student(firstname, lastname, fatherSName, time, groupId)
                pdpDb.insertStudent(student)
                val group = pdpDb.getGroupById(groupId!!)
                group.studentSCount++
                pdpDb.updateGroup(group)
                findNavController().popBackStack()
            }
        }
        return binding.root
    }

    private fun notEmpty(text: String): Boolean {
        var textBol = false
        for (c in text) {
            if (c != ' ') {
                textBol = true
                break
            }
        }
        return textBol
    }
}