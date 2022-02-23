package com.example.Groups

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.adapters.GroupRecyclerViewAdapter
import com.example.adapters.SpinnerMentorAdapter
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.CustomDialogGroupEditBinding
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentGroupsViewPagerElementBinding
import com.example.room.Database.PdpDatabase
import com.example.room.Entity.Groups
import com.example.room.Entity.Mentor
import com.example.room.Entity.Student
import com.example.util.Empty
import com.example.utils.Constant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val GROUP_LESSON = "groups"
private const val COURSE_ID = "course_id"

class GroupsViewPagerElementFragment : Fragment() {
    private var groupLesson: Int? = null
    private var courseId: Int? = null
    lateinit var binding: FragmentGroupsViewPagerElementBinding
    lateinit var groupRecyclerViewAdapter: GroupRecyclerViewAdapter
    lateinit var pdpDb: PdpDatabase
    lateinit var gson: Gson
    val type = object : TypeToken<ArrayList<Int>>() {}.type
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupLesson = it.getInt(GROUP_LESSON)
            courseId = it.getInt(COURSE_ID)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        gson = Gson()
        pdpDb = PdpDatabase.getInstance(requireContext())
        val groupList = loadList()
        binding = FragmentGroupsViewPagerElementBinding.inflate(inflater, container, false)
        groupRecyclerViewAdapter = GroupRecyclerViewAdapter(groupList)
        binding.groupsRV.adapter = groupRecyclerViewAdapter
        groupRecyclerViewAdapter.setOnItemDelete(object : GroupRecyclerViewAdapter.OnItemDelete {
            override fun onClick(group: Groups, position: Int) {
                val studentList = ArrayList<Student>()
                studentList.addAll(pdpDb.StudentWithGroupDao()
                    .getStudentsByGroup(group.groupId!!).students)

                for (student in studentList) {
                    pdpDb.StudentDao().deleteStudent(student)
                }
                groupList.remove(group)
                groupRecyclerViewAdapter.notifyItemRemoved(groupList.size)
                groupRecyclerViewAdapter.notifyItemRangeRemoved(position, groupList.size)
                pdpDb.GroupsDao().deleteGroup(group)
            }

        })
        groupRecyclerViewAdapter.setOnItemView(object : GroupRecyclerViewAdapter.OnItemView {
            override fun onClick(group: Groups, position: Int) {
                val bundleOf = bundleOf("group_id" to group.groupId)
                if (group.lessonStart == 0) {
                    findNavController().navigate(R.id.groupsAboutCourseGroupDetailsFragment,
                        bundleOf)
                } else {
                    findNavController().navigate(R.id.groupAboutCourseGroupStartedFragment,
                        bundleOf)
                }

            }

        })
        groupRecyclerViewAdapter.setOnItemEdit(object : GroupRecyclerViewAdapter.OnItemEdit {
            @SuppressLint("NotifyDataSetChanged")
            override fun onClick(group: Groups, position: Int) {
                val spinnerMentorAdapter: SpinnerMentorAdapter
                val dialog = Dialog(requireContext())
                val dialogView =
                    CustomDialogGroupEditBinding.inflate(LayoutInflater.from(requireContext()),
                        null,
                        false)
                dialog.setContentView(dialogView.root)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val mentorList = ArrayList<Mentor>()
                var mentor = Mentor()
                mentor.firstname = "Select"
                mentor.lastname = " mentor"
                mentorList.add(mentor)
                mentorList.addAll(pdpDb.MentorDao().getAllMentors(courseId!!))
                spinnerMentorAdapter = SpinnerMentorAdapter(mentorList)
                dialogView.mentorListSpinner.adapter = spinnerMentorAdapter
                var position = 0

                for (mentor in mentorList) {
                    if (mentor.mentorId == group.groupMentorId) {
                        position = mentorList.indexOf(mentor)
                        break
                    }
                }
                dialogView.groupName.setText(group.name)
                dialogView.time.setText(group.time)
                var timeOfBegin = ""
                var timeOfEnd = ""
                dialogView.mentorListSpinner.setSelection(position)
                dialogView.clock.setOnClickListener {
                    val timePickerDialogBegin =
                        TimePickerDialog(requireContext(),
                            { view, hourOfDay, minute ->
                                timeOfBegin = "$hourOfDay:$minute"
                                val timePickerDialogEnd = TimePickerDialog(requireContext(),
                                    { view, hourOfDay, minute ->

                                        timeOfEnd = "$hourOfDay:$minute"
                                        dialogView.time.setText("$timeOfBegin - $timeOfEnd")
                                    }, 24, 60, true)
                                timePickerDialogEnd.updateTime(hourOfDay, minute)
                                timePickerDialogEnd.show()
                            }, 24, 60, true)

                    timePickerDialogBegin.updateTime(8, 30)
                    timePickerDialogBegin.show()
                }

                dialogView.editTv.setOnClickListener {
                    val groupName = dialogView.groupName.text.toString()
                    val groupTime = dialogView.time.text.toString()
                    val groupNameBol = Empty.empty(groupName)
                    val groupTimeBol = Empty.empty(groupTime)
                    val groupNameSpace = Empty.space(groupName)
                    val groupTimeSpace = Empty.space(groupTime)
                    val bol = groupNameBol && groupTimeBol
                    val space = groupNameSpace && groupTimeSpace
                    var uniqueGroup = true
                    val groupList = ArrayList<Groups>()
                    groupList.addAll(pdpDb.CourseWithGroupsDao()
                        .getGroupsByCourse(courseId!!).groups)

                    if (groupList.size != 1) {
                        for (group in groupList) {
                            if (group.name == groupName) {
                                uniqueGroup = false
                            }
                        }
                    }

                    if (bol && space && uniqueGroup) {
                        group.name = groupName
                        group.time = groupTime
                        val selectedItem = dialogView.mentorListSpinner.selectedItemPosition
                        if (position != selectedItem) {
                            group.groupMentorId =
                                mentorList[dialogView.mentorListSpinner.selectedItemPosition].mentorId
                        }
                        pdpDb.GroupsDao().updateGroup(group)
                        groupRecyclerViewAdapter.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                }
                dialogView.cancelTv.setOnClickListener { dialog.dismiss() }
                dialog.show()

            }

        })
        return binding.root
    }

    private fun loadList(): ArrayList<Groups> {
        val groupList = ArrayList<Groups>()
        groupList.addAll(pdpDb.GroupsDao().getGroupsByLessonStartCourse(groupLesson!!, courseId!!))
        return groupList
    }


    companion object {

        fun newInstance(groupLesson: Int, courseId: Int) =
            GroupsViewPagerElementFragment().apply {
                arguments = Bundle().apply {
                    putInt(GROUP_LESSON, groupLesson)
                    putInt(COURSE_ID, courseId)
                }
            }
    }
}