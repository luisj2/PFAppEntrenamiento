package com.example.fithealth.View.Fragments.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fithealth.Model.Utils.ExtensionUtils.moveToActivity
import com.example.fithealth.View.Activitys.Home.SelectRoutineActivity
import com.example.fithealth.ViewModel.Local_Database.Day.DayEntityViewModel
import com.example.fithealth.ViewModel.Local_Database.Day.DayEntityViewModelBuilder
import com.example.fithealth.databinding.FragmentDayDescriptionBinding


//Probar funcionamiento
class DayDescriptionFragment : Fragment() {

    private var _binding :FragmentDayDescriptionBinding? = null
    private val binding get() = _binding!!

    private val dayViewModel : DayEntityViewModel by viewModels {
        DayEntityViewModelBuilder.getDayEntityViewModelFactory(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDayDescriptionBinding.inflate(layoutInflater,container,false)

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        setupOnClicks()
        setupObservers()
        changeNoItemsMealVisibility(true)
        changeNoItemsTrainingVisibility(true)
        dayViewModel.get
    }

    private fun setupObservers() {
        dayViewModel.apply {

        }
    }

    private fun setupOnClicks() {
        binding.apply {
            btnAddRoutineInDay.setOnClickListener { moveToActivity(SelectRoutineActivity::class.java) }
        }
    }

    private fun changeNoItemsTrainingVisibility(visible:Boolean){
        changeViewVisibility(binding.tvNoTrainingItems,visible)
    }

    private fun changeNoItemsMealVisibility(visible : Boolean){
        changeViewVisibility(binding.tvNoMealItems,visible)
    }

    private fun changeViewVisibility(view: View,visible : Boolean){
        view.visibility = if(visible) View.VISIBLE else View.GONE
    }


}