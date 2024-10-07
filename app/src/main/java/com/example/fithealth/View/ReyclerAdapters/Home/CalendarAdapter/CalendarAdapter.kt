package com.example.fithealth.View.ReyclerAdapters.Home.CalendarAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.databinding.ItemCalendarDayBinding

class CalendarAdapter(private val dayList: List<String>) :
    RecyclerView.Adapter<CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding =
            ItemCalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int = dayList.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(dayList[position])
    }
}