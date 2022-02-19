package com.example.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.androiddatabaselesson3pdpuz.databinding.ItemSpinnerBinding

class SpinnerDaysAdapter(var list :ArrayList<String>) : BaseAdapter() {

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): String {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding =
            ItemSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        binding.itemTv.text = list[position]
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