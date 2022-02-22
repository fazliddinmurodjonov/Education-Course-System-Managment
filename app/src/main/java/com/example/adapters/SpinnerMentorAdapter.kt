package com.example.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import com.example.androiddatabaselesson3pdpuz.databinding.ItemSpinnerBinding
import com.example.room.entity.Mentor

class SpinnerMentorAdapter(var list: ArrayList<Mentor>) : BaseAdapter(),SpinnerAdapter {

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Mentor {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "ClickableViewAccessibility")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding =
            ItemSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.itemTv.text = "${list[position].firstname}  ${list[position].lastname}"
        if (position == 0) {
            binding.itemTv.setTextColor(Color.GRAY)
        }
        var itemView: View
        if (convertView == null) {
            itemView = binding.root
        } else {
            itemView = convertView
        }
        isEnabled(position)

        return itemView
    }
}