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
import com.example.room.Database.PdpDatabase
import com.example.room.Entity.Groups
import com.example.room.Entity.Mentor
import com.example.room.Entity.Student
import com.example.util.Empty
import com.example.utils.Constant


class CoursesAddStudentFragment : Fragment() {

    lateinit var binding: FragmentCoursesAddStudentBinding
    lateinit var pdpDb: PdpDatabase
    lateinit var datePickerDialog: DatePickerDialog
    lateinit var spinnerMentorList: ArrayList<Mentor>
    lateinit var spinnerGroupList: ArrayList<Groups>
    lateinit var spinnerGroupAdapter: SpinnerGroupAdapter
    var courseId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCoursesAddStudentBinding.inflate(inflater, container, false)
        pdpDb = PdpDatabase.getInstance(requireContext())
        courseId = arguments?.getInt("course_id")!!
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
                    val group = Groups()
                    group.name = "Select group"
                    spinnerGroupList.add(group)
                    val mentorId =
                        spinnerMentorList[binding.chooseMentorSpinner.selectedItemPosition].mentorId
                    spinnerGroupList.addAll(pdpDb.GroupsDao()
                        .getGroupsByMentor(mentorId!!, courseId!!, 0))
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
            val lastnameBol = Empty.empty(lastname)
            val firstnameBol = Empty.empty(firstname)
            val fatherSNameBol = Empty.empty(fatherSName)
            val dateBol = Empty.empty(date)
            val lastnameSpace = Empty.space(lastname)
            val firstnameSpace = Empty.space(firstname)
            val fathersNameSpace = Empty.space(fatherSName)
            val dateSpace = Empty.space(date)
            val bol = lastnameBol && firstnameBol && fatherSNameBol && dateBol
            val space = lastnameSpace && firstnameSpace && fathersNameSpace && dateSpace
            if (bol && space && mentorPosition != 0 && groupPosition != 0) {
                val student = Student(firstname,
                    lastname,
                    fatherSName,
                    date,
                    spinnerGroupList[groupPosition].groupId)
                val group = spinnerGroupList[groupPosition]
                if (group.studentCount == null) {
                    group.studentCount = 1
                } else {
                    var studentCount = group.studentCount!!
                    ++studentCount
                    group.studentCount = studentCount
                }
                pdpDb.GroupsDao().updateGroup(group)
                pdpDb.StudentDao().insertStudent(student)
                findNavController().popBackStack(R.id.coursesAllCoursesFragment, false)
            }
        }

        return binding.root
    }

    private fun loadMentor(): ArrayList<Mentor> {
        val mentorList = ArrayList<Mentor>()
        var mentor = Mentor()
        mentor.firstname = "Select"
        mentor.lastname = "mentor"
        mentorList.add(mentor)
        mentorList.addAll(pdpDb.CourseWithMentorsDao().getMentorsByCourse(courseId).mentors)
        return mentorList
    }


}