package com.example.Groups

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.adapters.SpinnerDaysAdapter
import com.example.adapters.SpinnerMentorAdapter
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentGroupsAddGroupBinding
import com.example.room.Database.PdpDatabase
import com.example.room.Entity.Groups
import com.example.room.Entity.Mentor
import com.example.util.Empty
import com.example.utils.Constant
import com.google.gson.Gson


class GroupsAddGroupFragment : Fragment() {

    lateinit var binding: FragmentGroupsAddGroupBinding
    lateinit var pdpDb: PdpDatabase
    lateinit var gson: Gson
    var timeOfBegin: String = ""
    var timeOfEnd: String = ""
    var courseId: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        courseId = arguments?.getInt("course_id")!!

        pdpDb = PdpDatabase.getInstance(requireContext())
        gson = Gson()
        binding = FragmentGroupsAddGroupBinding.inflate(inflater, container, false)
        val mentorList = loadMentor()
        val spinnerMentorAdapter = SpinnerMentorAdapter(mentorList)
        binding.chooseMentorSpinner.adapter = spinnerMentorAdapter
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.clock.setOnClickListener {
            val timePickerDialogBegin =
                TimePickerDialog(requireContext(),
                    { view, hourOfDay, minute ->
                        timeOfBegin = "$hourOfDay:$minute"
                        val timePickerDialogEnd = TimePickerDialog(requireContext(),
                            { view, hourOfDay, minute ->

                                timeOfEnd = "$hourOfDay:$minute"
                                binding.addTime.setText("$timeOfBegin - $timeOfEnd")
                            }, 24, 60, true)
                        timePickerDialogEnd.updateTime(hourOfDay, minute)
                        timePickerDialogEnd.show()
                    }, 24, 60, true)

            timePickerDialogBegin.updateTime(8, 30)
            timePickerDialogBegin.show()

        }
        val loadDaysList = loadDays()
        val spinnerDaysAdapter = SpinnerDaysAdapter(loadDaysList)
        binding.addDaysSpinner.adapter = spinnerDaysAdapter
        // Add a group is beginning here
        binding.addGroup.setOnClickListener {

            val groupName: String = binding.addGroupName.text.toString()
            val groupNameBol = Empty.empty(groupName)
            val timeClock: String = binding.addTime.text.toString()
            val timeClockBol = Empty.empty(timeClock)
            val groupNameSpace = Empty.space(groupName)
            val timeClockSpace = Empty.space(timeClock)
            val bol = groupNameBol && timeClockBol
            val space = groupNameSpace && timeClockSpace
            val mentorPosition = binding.chooseMentorSpinner.selectedItemPosition
            val dayPosition = binding.addDaysSpinner.selectedItemPosition
            var uniqueGroup = true

            val allGroupList = ArrayList<Groups>()
            allGroupList.addAll(pdpDb.CourseWithGroupsDao().getGroupsByCourse(courseId).groups)
            for (group in allGroupList) {
                if (group.name == groupName) {
                    uniqueGroup = false
                }
            }
            if (bol && space && mentorPosition != 0 && dayPosition != 0 && uniqueGroup) {

                var mentor = mentorList[mentorPosition]
                val days = loadDaysList[dayPosition]

                val group = Groups(groupName, timeClock, days, mentor.mentorId, courseId, 0, 0)
                pdpDb.GroupsDao().insertGroup(group)
                findNavController().popBackStack()
            }
        }
        return binding.root
    }

    private fun loadDays(): ArrayList<String> {
        val timeList = ArrayList<String>()
        timeList.add("Choose days")
        timeList.add("Odd days of the week")
        timeList.add("Couple days of the week")
        return timeList
    }

    private fun loadMentor(): ArrayList<Mentor> {
        val mentorList = ArrayList<Mentor>()
        val allMentor = pdpDb.CourseWithMentorsDao().getMentorsByCourse(courseId).mentors
        var mentor = Mentor()
        mentor.firstname = "Select"
        mentor.lastname = "mentor"
        mentorList.add(mentor)
        mentorList.addAll(allMentor)
        return mentorList
    }
}