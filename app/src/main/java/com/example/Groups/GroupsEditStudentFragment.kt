package com.example.Groups

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentGroupsEditStudentBinding
import com.example.db.PdpDb


class GroupsEditStudentFragment : Fragment() {

    lateinit var binding: FragmentGroupsEditStudentBinding
    lateinit var pdpDb: PdpDb
    lateinit var datePickerDialog: DatePickerDialog
    var studentID: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        pdpDb = PdpDb(requireContext())
        binding = FragmentGroupsEditStudentBinding.inflate(inflater, container, false)
        studentID = arguments?.getInt("student_id")
        val student = pdpDb.getStudentById(studentID!!)
        binding.FirstnameEdit.setText(student.firstname)
        binding.LastnameEdit.setText(student.lastname)
        binding.FathersNameEdit.setText(student.fatherSName)
        binding.DateEdit.setText(student.time)
        binding.dateCalendar.setOnClickListener {
            datePickerDialog = DatePickerDialog(requireContext())
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                binding.DateEdit.setText("$dayOfMonth/${month + 1}/$year")
            }
            datePickerDialog.show()
        }
        binding.buttonEdit.setOnClickListener {
            val firstname = binding.FirstnameEdit.text.toString()
            val lastname = binding.LastnameEdit.text.toString()
            val fatherSName = binding.FathersNameEdit.text.toString()
            val time = binding.DateEdit.text.toString()
            val firstnameBol = notEmpty(firstname)
            val lastnameBol = notEmpty(lastname)
            val fatherSNameBol = notEmpty(fatherSName)
            val timeBol = notEmpty(time)
            if (firstname != " " && lastname != " " && fatherSName != " " && time != " " && firstnameBol && lastnameBol && fatherSNameBol && timeBol) {
                student.firstname = firstname
                student.lastname = lastname
                student.fatherSName = fatherSName
                student.time = time
                pdpDb.updateStudent(student)
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