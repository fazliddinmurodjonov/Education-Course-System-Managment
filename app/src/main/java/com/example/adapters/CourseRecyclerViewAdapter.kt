package com.example.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddatabaselesson3pdpuz.databinding.ItemCourseBinding
import com.example.room.Entity.Course

class CourseRecyclerViewAdapter(var list: ArrayList<Course>) :
    RecyclerView.Adapter<CourseRecyclerViewAdapter.MyViewHolder>() {
    lateinit var adapterListener: OnMyItemClickListener

    interface OnMyItemClickListener {
        fun onClick(course: Course)
    }

    fun setOnMyItemClickListener(listener: OnMyItemClickListener) {
        adapterListener = listener

    }

    inner class MyViewHolder(var binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(course: Course) {
            binding.courseName.text = course.name
            binding.root.setOnClickListener {
                adapterListener?.onClick(course)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemCourseBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val course = list[position]
        holder.onBind(course)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}