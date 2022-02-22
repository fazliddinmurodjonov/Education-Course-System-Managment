package com.example.Mentors

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.adapters.MentorRecyclerViewAdapter
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.CustomDialogMentorEditBinding
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentMentorsCourseMentorsBinding
import com.example.db.PdpDb
import com.example.room.entity.Mentor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MentorsCourseMentorsFragment : Fragment() {

    lateinit var binding: FragmentMentorsCourseMentorsBinding
    lateinit var mentorRecyclerViewAdapter: MentorRecyclerViewAdapter
    lateinit var pdpDb: PdpDb
    lateinit var mentorList: ArrayList<Mentor>
    var courseId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        pdpDb = PdpDb(requireContext())
        courseId = arguments?.getInt("course_id")!!
        binding = FragmentMentorsCourseMentorsBinding.inflate(inflater, container, false)
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        val courseName = arguments?.getString("course_name")
        val replaceName = courseName!!.replace("\n", " ")

        binding.courseName.text = replaceName
        binding.addMentor.setOnClickListener {
            val bundleOf = bundleOf("courseId" to courseId)
            findNavController().navigate(R.id.mentorsAddMentorFragment, bundleOf)
        }
        loadMentorRV()
        mentorRecyclerViewAdapter.setOnItemDelete(object :
            MentorRecyclerViewAdapter.OnItemDelete {
            override fun onClick(mentor: Mentor, position: Int) {
                var gson = Gson()
                var groupList = ArrayList<Int>()
                val groups = mentor.groupIdList
                if (groups != "") {
                    val type = object : TypeToken<ArrayList<Int>>() {}.type
                    groupList = gson.fromJson<ArrayList<Int>>(groups, type)
                }

                if (groupList.size == 0) {
                    mentorList.remove(mentor)
                    mentorRecyclerViewAdapter.notifyItemRemoved(mentorList.size)
                    mentorRecyclerViewAdapter.notifyItemRangeRemoved(position, mentorList.size)
                    pdpDb.deleteMentor(mentor)
                } else {

                }
            }

        })
        mentorRecyclerViewAdapter.setOnItemEdit(object : MentorRecyclerViewAdapter.OnItemEdit {
            override fun onClick(mentor: Mentor, position: Int) {
                val dialog = Dialog(requireContext())
                val mentorItem =
                    CustomDialogMentorEditBinding.inflate(LayoutInflater.from(requireContext()),
                        null,
                        false)
                mentorItem.mentorFirstname.setText(mentor.firstname.toString())
                mentorItem.mentorLastname.setText(mentor.lastname.toString())
                mentorItem.mentorFatherName.setText(mentor.fatherSName.toString())

                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(mentorItem.root)
                mentorItem.cancelTv.setOnClickListener {
                    dialog.dismiss()
                }
                mentorItem.saveTv.setOnClickListener {
                    var firstname = mentorItem.mentorFirstname.text.toString()
                    var lastname = mentorItem.mentorLastname.text.toString()
                    var fatherSName = mentorItem.mentorFatherName.text.toString()
                    var bolFirst = false
                    var bolLast = false
                    var bolFat = false
                    for (c in firstname) {
                        if (c != ' ') {
                            bolFirst = true
                            break
                        }
                    }
                    for (c in lastname) {
                        if (c != ' ') {
                            bolLast = true
                            break
                        }
                    }
                    for (c in fatherSName) {
                        if (c != ' ') {
                            bolFat = true
                            break
                        }
                    }
                    var uniqueMentor = true
                    val allMentorList = pdpDb.getAllMentor(courseId)
                    for (m in allMentorList) {
                        if (m.firstname == firstname && m.lastname == lastname) {
                            if (m.id != mentor.id) {
                                uniqueMentor = false
                            }
                        }
                    }
                    if (firstname != " " && lastname != " " && fatherSName != " " && bolFirst && bolLast && bolFat && uniqueMentor) {
                        mentor.firstname = firstname
                        mentor.lastname = lastname
                        mentor.fatherSName = fatherSName
                        pdpDb.updateMentor(mentor)
                        mentorList[position] = mentor
                        mentorRecyclerViewAdapter.notifyItemChanged(position)
                        dialog.dismiss()
                    }

                }
                dialog.show()
            }
        })
        return binding.root
    }

    private fun loadMentorRV() {
        mentorList = pdpDb.getAllMentor(courseId)
        mentorRecyclerViewAdapter = MentorRecyclerViewAdapter(mentorList)
        binding.allMentorsRV.adapter = mentorRecyclerViewAdapter
    }

    override fun onResume() {
        super.onResume()
    }

}