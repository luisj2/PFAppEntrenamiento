package com.example.fithealth.View.ReyclerAdapters.Home.CalendarAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.Utils.CalendarHelper
import com.example.fithealth.databinding.ItemCalendarDayBinding

class CalendarAdapter :
    RecyclerView.Adapter<CalendarViewHolder>() {

    private val daysList = CalendarHelper.getDaysInMonthArray()
    private val todayDay = CalendarHelper.getTodayDay().toString()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding =
            ItemCalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int = daysList.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val dayItem = daysList[position]
        holder.bind(dayItem,isToday(dayItem))
    }

    private fun isToday(day: String): Boolean =
        CalendarHelper.isCurrentMonth() && day == todayDay

}