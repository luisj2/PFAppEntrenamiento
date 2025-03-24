package com.example.fithealth.View.ReyclerAdapters.Home.CalendarAdapter

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.databinding.ItemCalendarDayBinding

class CalendarViewHolder(private val binding: ItemCalendarDayBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(day: String, isCurrentDay: Boolean) {
        binding.tvCalendarDay.text = day

        if (isCurrentDay) todayBackground()
    }



    private fun todayBackground() {
        binding.root.setBackgroundColor(Color.BLUE)
    }

}