package com.example.fithealth.View.Fragments.Training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.Model.Utils.ExtensionUtils.moveToActivity
import com.example.fithealth.View.Activitys.Training.CreateExerciseActivity
import com.example.fithealth.View.ReyclerAdapters.Training.ExerciseAdapter.IconExerciseName.ExerciseAdapter
import com.example.fithealth.ViewModel.Firebase.Firestore.Exercise.ExerciseFirestoreViewModel
import com.example.fithealth.ViewModel.Firebase.Firestore.Exercise.ExerciseFirestoreViewModelBuilder
import com.example.fithealth.databinding.FragmentExerciseBinding


class ExerciseFragment : Fragment() {

    private var _binding: FragmentExerciseBinding? = null
    private val binding: FragmentExerciseBinding get() = _binding!!

    private val exerciseViewModel: ExerciseFirestoreViewModel by viewModels {
        ExerciseFirestoreViewModelBuilder.getExerciseFirestoreViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        setupExercisesRecycler()
        setupObservers()
        setupOnClick()
        exerciseViewModel.getAlluserExercises()
    }

    private fun setupObservers() {
        setupExerciseFirebaseObservers()
    }

    private fun     setupExerciseFirebaseObservers() {
        exerciseViewModel.apply {
            allExercises.observe(viewLifecycleOwner) { exerciseList ->
                if (exerciseList.isNotEmpty()) updateExerciseRecycler(exerciseList)
            }
        }
    }

    private fun updateExerciseRecycler(exerciseList: List<Exercise?>) {
        binding.rvExercises.apply {
            val exerciseAdapter = adapter as ExerciseAdapter
            exerciseAdapter.updateList(exerciseList)
        }
    }

    private fun setupExercisesRecycler() {
        binding.rvExercises.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ExerciseAdapter(mutableListOf(), ::setMessageVisibility)
        }
    }

    private fun setMessageVisibility (visible : Boolean){
        binding.tvNoExercisesAdded.visibility = if(visible) View.VISIBLE else View.GONE
    }

    private fun setupOnClick() {
        binding.apply {
            btnNavigateToCreateExerciseActivity.setOnClickListener {
                moveToActivity(
                    CreateExerciseActivity::class.java
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}