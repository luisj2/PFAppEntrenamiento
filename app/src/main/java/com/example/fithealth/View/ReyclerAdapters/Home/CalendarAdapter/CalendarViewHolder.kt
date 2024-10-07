package com.example.fithealth.View.ReyclerAdapters.Home.CalendarAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.databinding.ItemCalendarDayBinding

class CalendarViewHolder(private val binding: ItemCalendarDayBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(day: String) {
        binding.tvCalendarDay.text = day
    }

}