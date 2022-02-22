package com.example.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddatabaselesson3pdpuz.databinding.ItemStudentBinding
import com.example.room.entity.Student

class StudentRecyclerViewAdapter(var studentList: ArrayList<Student>) :
    RecyclerView.Adapter<StudentRecyclerViewAdapter.StudentViewHolder>() {
    lateinit var adapterEdit: OnItemEdit
    lateinit var adapterDelete: OnItemDelete

    interface OnItemEdit {
        fun onClick(student: Student, position: Int)
    }

    interface OnItemDelete {
        fun onClick(student: Student, position: Int)
    }

    fun setOnItemEdit(itemEdit: OnItemEdit) {
        adapterEdit = itemEdit
    }

    fun setOnItemDelete(itemDelete: OnItemDelete) {
        adapterDelete = itemDelete
    }


    inner class StudentViewHolder(var binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(student: Student, position: Int) {
            binding.firstname.text = student.firstname
            binding.lastname.text = student.lastname
            binding.fathersName.text = student.fatherSName
            binding.edit.setOnClickListener {
                adapterEdit.onClick(student, position)
            }
            binding.delete.setOnClickListener {
                adapterDelete.onClick(student, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(ItemStudentBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        holder.onBind(student, position)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}