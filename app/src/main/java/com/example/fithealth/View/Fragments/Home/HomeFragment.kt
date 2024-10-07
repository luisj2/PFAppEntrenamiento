package com.example.fithealth.View.Fragments.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fithealth.Model.Utils.CalendarHelper
import com.example.fithealth.R
import com.example.fithealth.View.ReyclerAdapters.Home.CalendarAdapter.CalendarAdapter
import com.example.fithealth.databinding.FragmentHomeBinding
import java.time.LocalDate

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        setupRecyclers()
        setupButtons()
        binding.tvDayMonth.text = CalendarHelper.monthYearFromSelectedDate()
    }

    private fun setupButtons() {
        binding.apply {
            ibPreviousMonthButton.setOnClickListener {
                updateToPreviousMonth()
            }

            ibForwardMonthButton.setOnClickListener {
                updateToNextMonth()
            }
        }
    }

    private fun updateToNextMonth() {
        CalendarHelper.plusMonthSelectedDate()
        updateMonthView()
    }

    private fun updateToPreviousMonth() {
        CalendarHelper.minusMonthSelectedDate()
        updateMonthView()
    }

    private fun setupRecyclers() {
        binding.rvCalendar.apply {
            layoutManager = GridLayoutManager(context, 7)
            adapter = getCalendarAdapter()
        }
    }

    private fun getCalendarAdapter() = CalendarAdapter(CalendarHelper.getDaysInMonthArray())

    private fun updateMonthView() {
        binding.apply {
            tvDayMonth.text = CalendarHelper.monthYearFromSelectedDate()
            rvCalendar.adapter = getCalendarAdapter()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}