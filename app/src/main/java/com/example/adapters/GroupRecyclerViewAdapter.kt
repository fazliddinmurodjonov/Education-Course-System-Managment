package com.example.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddatabaselesson3pdpuz.R
import com.example.androiddatabaselesson3pdpuz.databinding.ItemGroupBinding
import com.example.models.Group
import com.example.models.Mentor

class GroupRecyclerViewAdapter(var list: ArrayList<Group>) :
    RecyclerView.Adapter<GroupRecyclerViewAdapter.MyViewHolder>() {
    lateinit var adapterEdit: OnItemEdit
    lateinit var adapterDelete: OnItemDelete
    lateinit var adapterView: OnItemView

    interface OnItemEdit {
        fun onClick(group: Group, position: Int)
    }

    interface OnItemDelete {
        fun onClick(group: Group, position: Int)
    }

    interface OnItemView {
        fun onClick(group: Group, position: Int)

    }

    fun setOnItemEdit(itemEdit: OnItemEdit) {
        adapterEdit = itemEdit
    }

    fun setOnItemDelete(itemDelete: OnItemDelete) {
        adapterDelete = itemDelete
    }

    fun setOnItemView(itemView: OnItemView) {
        adapterView = itemView
    }


    inner class MyViewHolder(val binding: ItemGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(group: Group, position: Int) {
            binding.groupName.text = group.name.toString()
            binding.numberOfStudents.text = group.studentSCount.toString()
            binding.alter.setImageResource(R.drawable.ic_edit)
            binding.delete.setImageResource(R.drawable.ic_delete)
            binding.view.setImageResource(R.drawable.ic_view)
            binding.alter.setOnClickListener {
                adapterEdit.onClick(group, position)
            }
            binding.delete.setOnClickListener {
                adapterDelete.onClick(group, position)
            }
            binding.view.setOnClickListener {
                adapterView.onClick(group, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemGroupBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val group = list[position]
        holder.onBind(group, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}