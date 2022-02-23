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
import com.example.room.Database.PdpDatabase
import com.example.room.Entity.Groups
import com.example.room.Entity.Mentor
import com.example.util.Empty


class MentorsCourseMentorsFragment : Fragment() {

    lateinit var binding: FragmentMentorsCourseMentorsBinding
    lateinit var mentorRecyclerViewAdapter: MentorRecyclerViewAdapter
    lateinit var pdpDb: PdpDatabase
    lateinit var mentorList: ArrayList<Mentor>
    var courseId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        pdpDb = PdpDatabase.getInstance(requireContext())
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
                var groupList = ArrayList<Groups>()
                groupList.addAll(pdpDb.MentorWithGroupsDao()
                    .getGroupsByMentor(mentor.mentorId!!).groups)
                if (groupList.size == 0) {
                    pdpDb.MentorDao().deleteMentor(mentor)
                    mentorList.remove(mentor)
                    mentorRecyclerViewAdapter.notifyItemRemoved(mentorList.size)
                    mentorRecyclerViewAdapter.notifyItemRangeRemoved(position, mentorList.size)
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
                    val firstnameBol = Empty.empty(firstname)
                    val lastnameBol = Empty.empty(lastname)
                    val fatherSNameBol = Empty.empty(fatherSName)
                    val firstnameSpace = Empty.space(firstname)
                    val lastnameSpace = Empty.space(lastname)
                    val fatherSNameSpace = Empty.space(fatherSName)
                    var uniqueMentor = true
                    val allMentorList = ArrayList<Mentor>()
                    allMentorList.addAll(pdpDb.MentorDao().getAllMentors(courseId))
                    for (m in allMentorList) {
                        if (m.firstname == firstname && m.lastname == lastname) {
                            if (m.mentorId != mentor.mentorId) {
                                uniqueMentor = false
                            }
                        }
                    }
                    val bol = firstnameBol && lastnameBol && fatherSNameBol
                    val space = firstnameSpace && lastnameSpace && fatherSNameSpace
                    if (bol && space && uniqueMentor) {
                        mentor.firstname = firstname
                        mentor.lastname = lastname
                        mentor.fatherSName = fatherSName
                        pdpDb.MentorDao().updateMentor(mentor)
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
        mentorList = ArrayList()
        mentorList.addAll(pdpDb.CourseWithMentorsDao().getMentorsByCourse(courseId!!).mentors)
        mentorRecyclerViewAdapter = MentorRecyclerViewAdapter(mentorList)
        binding.allMentorsRV.adapter = mentorRecyclerViewAdapter
    }

    override fun onResume() {
        super.onResume()
    }

}