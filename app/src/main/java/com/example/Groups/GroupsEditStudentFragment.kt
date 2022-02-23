package com.example.Groups

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentGroupsEditStudentBinding
import com.example.room.Database.PdpDatabase
import com.example.util.Empty


class GroupsEditStudentFragment : Fragment() {

    lateinit var binding: FragmentGroupsEditStudentBinding
    lateinit var pdpDb: PdpDatabase
    lateinit var datePickerDialog: DatePickerDialog
    var studentID: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        pdpDb = PdpDatabase.getInstance(requireContext())
        binding = FragmentGroupsEditStudentBinding.inflate(inflater, container, false)
        studentID = arguments?.getInt("student_id")
        val student = pdpDb.StudentDao().getStudentById(studentID!!)
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
                student.firstname = firstname
                student.lastname = lastname
                student.fatherSName = fatherSName
                student.time = time
                pdpDb.StudentDao().updateStudent(student)
                findNavController().popBackStack()
            }
        }
        return binding.root
    }

}