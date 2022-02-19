package com.example.Groups

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.adapters.SpinnerDaysAdapter
import com.example.adapters.SpinnerMentorAdapter
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentGroupsAddGroupBinding
import com.example.db.PdpDb
import com.example.models.Group
import com.example.models.Mentor
import com.example.utils.Constant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GroupsAddGroupFragment : Fragment() {

    lateinit var binding: FragmentGroupsAddGroupBinding
    lateinit var pdpDb: PdpDb
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

        pdpDb = PdpDb(requireContext())
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
            var groupNameBol = false
            val groupName: String = binding.addGroupName.text.toString()
            for (c in groupName) {
                if (c != ' ') {
                    groupNameBol = true
                    break
                }
            }
            var timeClockBol = false
            val timeClock: String = binding.addTime.text.toString()
            for (c in timeClock) {
                if (c != ' ') {
                    timeClockBol = true
                    break
                }
            }
            val mentorPosition = binding.chooseMentorSpinner.selectedItemPosition
            val dayPosition = binding.addDaysSpinner.selectedItemPosition

            var uniqueGroup = true
            val query =
                "select *from ${Constant.GROUP_TABLE} WHERE ${Constant.GROUP_COURSE_ID}  = $courseId"
            val allGroupList = pdpDb.getAllGroup(query)
            for (group in allGroupList) {
                if (group.name == groupName) {
                    uniqueGroup = false
                }
            }
            if (groupNameBol && groupName != " " && timeClockBol && timeClock != " " && mentorPosition != 0 && dayPosition != 0 && uniqueGroup) {

                var mentor = mentorList[mentorPosition]
                val days = loadDaysList[dayPosition]

                val group = Group(groupName, timeClock, days, 0, courseId, mentor.id, 0)
                pdpDb.insertGroup(group)

                val query =
                    "select *from ${Constant.GROUP_TABLE} WHERE ${Constant.GROUP_MENTOR_ID}  = ${mentor.id}"
                val allGroup = pdpDb.getAllGroup(query)
                var groupIdList = ArrayList<Int>()

                for (group in allGroup) {
                    groupIdList.add(group.id!!)
                }
                val toJson = gson.toJson(groupIdList)
                mentor.groupIdList = toJson
//                var mentorGroupIdList: String = ""
//                if (mentor.groupIdList == "") {
//                    mentorGroupIdList = gson.toJson(groupIdList)
//
//                } else {
//                    val type = object : TypeToken<ArrayList<Int>>() {}.type
//                    val fromJsonList = gson.fromJson<ArrayList<Int>>(mentor.groupIdList, type)
//                    groupIdList.addAll(fromJsonList)
//                    mentorGroupIdList = gson.toJson(groupIdList)
//                }
//                mentor.groupIdList = mentorGroupIdList
                pdpDb.updateMentor(mentor)
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
        val allMentor = pdpDb.getAllMentor(courseId!!)
        var mentor = Mentor()
        mentor.firstname = "Select"
        mentor.lastname = "mentor"
        mentorList.add(mentor)
        mentorList.addAll(allMentor)
        return mentorList
    }
}