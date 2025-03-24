package com.example.fithealth.View.Fragments.Training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithealth.Model.DataClass.Routine
import com.example.fithealth.Model.Utils.ExtensionUtils.moveToActivity
import com.example.fithealth.View.Activitys.Training.CreateRoutineActivity
import com.example.fithealth.View.ReyclerAdapters.Training.RotineAdapter.RoutineAdapter
import com.example.fithealth.ViewModel.Firebase.Firestore.Routines.RoutinesViewModel
import com.example.fithealth.ViewModel.Firebase.Firestore.Routines.RoutinesViewModelBuilder
import com.example.fithealth.databinding.FragmentRoutinesBinding


class RoutinesFragment : Fragment() {

    private var _binding: FragmentRoutinesBinding? = null
    private val binding: FragmentRoutinesBinding get() = _binding!!

    private val routineViewModel: RoutinesViewModel by viewModels {
        RoutinesViewModelBuilder.getRoutinesViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutinesBinding.inflate(inflater, container, false)

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        setupRoutineRecycler()
        setupOnClicks()
        setupRoutinesObserver()
        routineViewModel.getAllRoutines()
    }

    private fun setupOnClicks() {
        binding.apply {
            btnNavigateToCreateRoutineActivity.setOnClickListener { moveToActivity(CreateRoutineActivity::class.java) }
        }
    }

    private fun setupRoutinesObserver() {
        routineViewModel.apply {
            allRoutine.observe(viewLifecycleOwner) { routineList ->
                changeNoItemsVisibility(routineList.isEmpty())
                if (routineList.isNotEmpty()) updateRecyclerList(routineList)
            }
        }
    }

    private fun changeNoItemsVisibility(visible: Boolean) {
        binding.tvNoElementsMessage.visibility = if (visible) View.VISIBLE else View.GONE   
    }

    private fun updateRecyclerList(routineList: List<Routine?>) {
        val routineAdapter = binding.rvRoutinesList.adapter as? RoutineAdapter
        routineAdapter?.updateList(routineList)
    }

    private fun setupRoutineRecycler() {
        binding.rvRoutinesList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = RoutineAdapter(mutableListOf())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}