package com.example.Mentors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.FragmentMentorsAddMentorBinding
import com.example.db.PdpDb
import com.example.models.Mentor


class MentorsAddMentorFragment : Fragment() {

    lateinit var binding: FragmentMentorsAddMentorBinding
    lateinit var pdpDb: PdpDb
    val courseId = arguments?.getInt("courseId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMentorsAddMentorBinding.inflate(inflater, container, false)
        pdpDb = PdpDb(requireContext())
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.addButton.setOnClickListener {
            val lastname = binding.EditTextLastname.text.toString()
            val firstname = binding.EditTextFirstname.text.toString()
            val fathersName = binding.EditTextFathersName.text.toString()
            var lastnameBol = false
            var firstnameBol = false
            var fathersNameBol = false

            for (c in lastname) {

                if (c != ' ') {
                    lastnameBol = true
                    break
                }
            }
            for (c in firstname) {
                if (c != ' ') {
                    firstnameBol = true
                }
            }
            for (c in fathersName) {
                if (c != ' ') {
                    fathersNameBol = true
                }
            }
            var uniqueMentor = true
            val courseId = arguments?.getInt("courseId")
            val allMentorList = pdpDb.getAllMentor(courseId!!)
            for (mentor in allMentorList) {
                if (mentor.firstname == firstname && mentor.lastname == lastname) {
                    uniqueMentor = false
                }
            }
            if (lastname != " " && firstname != " " && fathersName != " " && lastnameBol && firstnameBol && fathersNameBol && uniqueMentor) {
                val mentor = Mentor(firstname, lastname, fathersName, courseId, "")
                pdpDb.insertMentor(mentor)
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

}