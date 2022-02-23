package com.example.Mentors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentMentorsAddMentorBinding
import com.example.room.Database.PdpDatabase
import com.example.room.Entity.Mentor
import com.example.util.Empty


class MentorsAddMentorFragment : Fragment() {

    lateinit var binding: FragmentMentorsAddMentorBinding
    lateinit var pdpDb: PdpDatabase
    val courseId = arguments?.getInt("courseId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentMentorsAddMentorBinding.inflate(inflater, container, false)
        pdpDb = PdpDatabase.getInstance(requireContext())
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addButton.setOnClickListener {
            val lastname = binding.EditTextLastname.text.toString()
            val firstname = binding.EditTextFirstname.text.toString()
            val fathersName = binding.EditTextFathersName.text.toString()
            val firstnameBol = Empty.empty(firstname)
            val lastnameBol = Empty.empty(lastname)
            val fatherSNameBol = Empty.empty(fathersName)
            val firstnameSpace = Empty.space(firstname)
            val lastnameSpace = Empty.space(lastname)
            val fatherSNameSpace = Empty.space(fathersName)
            var uniqueMentor = true
            val bol = firstnameBol && lastnameBol && fatherSNameBol
            val space = firstnameSpace && lastnameSpace && fatherSNameSpace
            val courseId = arguments?.getInt("courseId")

            val allMentorList = ArrayList<Mentor>()
            allMentorList.addAll(pdpDb.CourseWithMentorsDao()
                .getMentorsByCourse(courseId!!).mentors)

            for (mentor in allMentorList) {
                if (mentor.firstname == firstname && mentor.lastname == lastname) {
                    uniqueMentor = false
                }
            }
            if (bol && space && uniqueMentor) {
                val mentor = Mentor(firstname, lastname, fathersName, courseId)
                pdpDb.MentorDao().insertMentor(mentor)
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

}