package com.example.fithealth.View.Fragments.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fithealth.Model.Utils.CalendarHelper
import com.example.fithealth.Model.Utils.ExtensionUtils.moveToActivity
import com.example.fithealth.View.Activitys.AccederAplicacion.LoginActivity
import com.example.fithealth.View.ReyclerAdapters.Home.CalendarAdapter.CalendarAdapter
import com.example.fithealth.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        setupOnclicks()
        updateMonthView()
        goCurrentMonth()
    }

    private fun setupOnclicks() {
        binding.apply {
            ibPreviousMonthButton.setOnClickListener {
                updateToPreviousMonth()
            }

            ibForwardMonthButton.setOnClickListener {
                updateToNextMonth()
            }
            tvDayMonth.setOnClickListener {
                goCurrentMonth()
            }
        }

    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        moveToActivity(LoginActivity::class.java)
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
        }
    }


    private fun updateMonthView() {
        binding.apply {
            tvDayMonth.text = CalendarHelper.monthYearFromSelectedDate()
            rvCalendar.adapter = CalendarAdapter()
        }

    }

    private fun goCurrentMonth() {
        CalendarHelper.selectedDateToCurrentDate()
        updateMonthView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}