package com.example.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.ItemMentorBinding
import com.example.room.Entity.Mentor

class MentorRecyclerViewAdapter(var list: ArrayList<Mentor>) :
    RecyclerView.Adapter<MentorRecyclerViewAdapter.MyViewHolder>() {
    lateinit var adapterEdit: OnItemEdit
    lateinit var adapterDelete: OnItemDelete

    interface OnItemEdit {
        fun onClick(mentor: Mentor, position: Int)
    }
    interface OnItemDelete {
        fun onClick(mentor: Mentor, position: Int)
    }

    fun setOnItemEdit(itemEdit: OnItemEdit) {
        adapterEdit = itemEdit
    }

    fun setOnItemDelete(itemDelete: OnItemDelete) {
        adapterDelete = itemDelete
    }

    inner class MyViewHolder(var binding: ItemMentorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(mentor: Mentor, position: Int) {
            binding.firstname.text = mentor.firstname
            binding.lastname.text = mentor.lastname
            binding.fathersName.text = mentor.fatherSName
            binding.edit.setImageResource(R.drawable.ic_edit)
            binding.delete.setImageResource(R.drawable.ic_delete)
            binding.edit.setOnClickListener {
                adapterEdit.onClick(mentor, position)
            }
            binding.delete.setOnClickListener {
                adapterDelete.onClick(mentor, position)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemMentorBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mentor = list[position]
        holder.onBind(mentor, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}