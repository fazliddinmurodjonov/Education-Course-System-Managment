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
import com.example.db.PdpDb
import com.example.room.entity.Group
import com.example.room.entity.Mentor
import com.example.utils.Constant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val GROUP_LESSON = "groups"
private const val COURSE_ID = "course_id"

class   GroupsViewPagerElementFragment : Fragment() {
    private var groupLesson: Int? = null
    private var courseId: Int? = null
    lateinit var binding: FragmentGroupsViewPagerElementBinding
    lateinit var groupRecyclerViewAdapter: GroupRecyclerViewAdapter
    lateinit var pdpDb: PdpDb
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
        pdpDb = PdpDb(requireContext())
        val groupList = loadList()
        binding = FragmentGroupsViewPagerElementBinding.inflate(inflater, container, false)
        groupRecyclerViewAdapter = GroupRecyclerViewAdapter(groupList)
        binding.groupsRV.adapter = groupRecyclerViewAdapter
        groupRecyclerViewAdapter.setOnItemDelete(object : GroupRecyclerViewAdapter.OnItemDelete {
            override fun onClick(group: Group, position: Int) {

                val query =
                    "select *from ${Constant.STUDENT_TABLE} WHERE ${Constant.STUDENT_GROUP_ID} = ${group.id}"
                val studentList = pdpDb.getAllStudent(query)
                for (student in studentList) {
                    pdpDb.deleteStudent(student)
                }
//                var mentor = pdpDb.getMentorById(group.mentorId!!)
//                val groupIdList = mentor.groupIdList
//
//                var idList = gson.fromJson<ArrayList<Int>>(groupIdList, type)
//                idList.remove(group.id)
//                if (idList.size == 0) {
//                    mentor.groupIdList = ""
//                } else {
//                    val toJson = gson.toJson(idList)
//                    mentor.groupIdList = toJson
//                }
//                pdpDb.updateMentor(mentor)
                val mentorOld = pdpDb.getMentorById(group.mentorId!!)
                var mentorOldGroupIdList =
                    gson.fromJson<ArrayList<Int>>(mentorOld.groupIdList, type)
                mentorOldGroupIdList.remove(group.id)
                if (mentorOldGroupIdList.size == 0) {
                    mentorOld.groupIdList = ""
                } else {
                    val toJson = gson.toJson(mentorOldGroupIdList)
                    mentorOld.groupIdList = toJson
                }
                pdpDb.updateMentor(mentorOld)
                groupList.remove(group)
                groupRecyclerViewAdapter.notifyItemRemoved(groupList.size)
                groupRecyclerViewAdapter.notifyItemRangeRemoved(position, groupList.size)
                pdpDb.deleteGroup(group)
            }

        })
        groupRecyclerViewAdapter.setOnItemView(object : GroupRecyclerViewAdapter.OnItemView {
            override fun onClick(group: Group, position: Int) {
                val bundleOf = bundleOf("group_id" to group.id)
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
            override fun onClick(group: Group, position: Int) {
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
                mentorList.addAll(pdpDb.getAllMentor(group.courseId!!))

                spinnerMentorAdapter = SpinnerMentorAdapter(mentorList)
                dialogView.mentorListSpinner.adapter = spinnerMentorAdapter
                var position = 0
                for (mentor in mentorList) {
                    if (mentor.id == group.mentorId) {
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
                    val groupNameBol = notEmpty(groupName)
                    val groupTimeBol = notEmpty(groupTime)

                    var uniqueGroup = true
                    val query =
                        "select *from ${Constant.GROUP_TABLE} WHERE ${Constant.GROUP_COURSE_ID}  = $courseId"
                    val allGroupList = pdpDb.getAllGroup(query)
                    if (allGroupList.size != 1) {
                        for (group in allGroupList) {
                            if (group.name == groupName) {
                                uniqueGroup = false
                            }
                        }
                    }

                    if (groupName != " " && groupTime != " " && groupNameBol && groupTimeBol && uniqueGroup) {
                        group.name = groupName
                        group.time = groupTime
                        val selectedItem = dialogView.mentorListSpinner.selectedItemPosition
                        if (position != selectedItem) {
                            group.mentorId =
                                mentorList[dialogView.mentorListSpinner.selectedItemPosition].id
                            val mentorOld = mentorList[position]
                            var mentorOldGroupIdList =
                                gson.fromJson<ArrayList<Int>>(mentorOld.groupIdList, type)
                            mentorOldGroupIdList.remove(group.id) ///
                            if (mentorOldGroupIdList.size == 0) {
                                mentorOld.groupIdList = ""
                            } else {
                                val toJson = gson.toJson(mentorOldGroupIdList)
                                mentorOld.groupIdList = toJson
                            }
                            pdpDb.updateMentor(mentorOld)
                            val mentorNew = mentorList[selectedItem]
                            if (mentorNew.groupIdList == "") {
                                var list = ArrayList<Int>()
                                list.add(group.id!!)
                                val toJson = gson.toJson(list)
                                mentorNew.groupIdList = toJson
                            } else {
                                var list =
                                    gson.fromJson<ArrayList<Int>>(mentorNew.groupIdList, type)
                                list.add(group.id!!)
                                val toJson = gson.toJson(list)
                                mentorNew.groupIdList = toJson
                            }
                            pdpDb.updateMentor(mentorNew)
                        }
                        pdpDb.updateGroup(group)
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

    private fun loadList(): ArrayList<Group> {
        val query =
            "select *from ${Constant.GROUP_TABLE} WHERE ${Constant.GROUP_LESSON} = $groupLesson AND ${Constant.GROUP_COURSE_ID}  = $courseId"
        return pdpDb.getAllGroup(query)
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