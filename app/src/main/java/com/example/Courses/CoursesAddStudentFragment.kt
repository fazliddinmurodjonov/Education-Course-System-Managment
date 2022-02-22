package com.example.Courses

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.example.adapters.SpinnerGroupAdapter
import com.example.adapters.SpinnerMentorAdapter
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentCoursesAddStudentBinding
import com.example.db.PdpDb
import com.example.room.entity.Group
import com.example.room.entity.Mentor
import com.example.room.entity.Student
import com.example.utils.Constant


class CoursesAddStudentFragment : Fragment() {

    lateinit var binding: FragmentCoursesAddStudentBinding
    lateinit var pdpDb: PdpDb
    lateinit var datePickerDialog: DatePickerDialog
    lateinit var spinnerMentorList: ArrayList<Mentor>
    lateinit var spinnerGroupList: ArrayList<Group>
    lateinit var spinnerGroupAdapter: SpinnerGroupAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCoursesAddStudentBinding.inflate(inflater, container, false)
        pdpDb = PdpDb(requireContext())
        spinnerMentorList = ArrayList()
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        spinnerMentorList = loadMentor()
        val spinnerMentorAdapter = SpinnerMentorAdapter(spinnerMentorList)
        binding.chooseMentorSpinner.adapter = spinnerMentorAdapter

        binding.chooseMentorSpinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                if (position != 0) {
                    spinnerGroupList = ArrayList()
                    val group = Group()
                    group.name = "Select group"
                    spinnerGroupList.add(group)
                    val query =
                        "SELECT * FROM '${Constant.GROUP_TABLE}' WHERE ${Constant.GROUP_MENTOR_ID} = ${spinnerMentorList[binding.chooseMentorSpinner.selectedItemPosition].id}  AND ${Constant.GROUP_LESSON} = 0"
                    spinnerGroupList.addAll(loadGroup(query))
                    spinnerGroupAdapter = SpinnerGroupAdapter(spinnerGroupList)
                    binding.chooseGroupSpinner.adapter = spinnerGroupAdapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        })



        binding.datePickerImage.setOnClickListener {
            datePickerDialog = DatePickerDialog(requireContext())
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                binding.addDate.setText("$dayOfMonth/${month + 1}/$year")
            }
            datePickerDialog.show()
        }
        binding.addStudent.setOnClickListener {
            val lastname = binding.addLastname.text.toString()
            val firstname = binding.addFirstname.text.toString()
            val fatherSName = binding.addFathersName.text.toString()
            val date = binding.addDate.text.toString()
            val mentorPosition = binding.chooseMentorSpinner.selectedItemPosition
            val groupPosition = binding.chooseGroupSpinner.selectedItemPosition
            val lastnameBol = notEmpty(lastname)
            val firstnameBol = notEmpty(firstname)
            val fatherSNameBol = notEmpty(fatherSName)
            val dateBol = notEmpty(date)
            if (lastname != " " && firstname != " " && fatherSName != " " && date != " " && lastnameBol &&
                firstnameBol && fatherSNameBol && dateBol && mentorPosition != 0 && groupPosition != 0
            ) {
                val student = Student(firstname,
                    lastname,
                    fatherSName,
                    date,
                    spinnerGroupList[groupPosition].id)

                pdpDb.insertStudent(student)

                var group = pdpDb.getGroupById(spinnerGroupList[groupPosition].id!!)

                var studentCount = group.studentSCount
                group.lessonStart = 0
                studentCount++

                group.studentSCount = studentCount
                pdpDb.updateGroup(group)
                findNavController().popBackStack(R.id.coursesAllCoursesFragment, false)
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

    private fun loadGroup(query: String): ArrayList<Group> {
        val spinnerGroupList = ArrayList<Group>()
        val groupList = pdpDb.getAllGroup(query)
        spinnerGroupList.addAll(groupList)
        return spinnerGroupList
    }

    private fun loadMentor(): ArrayList<Mentor> {
        val mentorList = ArrayList<Mentor>()
        val courseId = arguments?.getInt("course_id")
        val allMentor = pdpDb.getAllMentor(courseId!!)
        var mentor = Mentor()
        mentor.firstname = "Select"
        mentor.lastname = "mentor"
        mentorList.add(mentor)
        mentorList.addAll(allMentor)
        return mentorList
    }


}