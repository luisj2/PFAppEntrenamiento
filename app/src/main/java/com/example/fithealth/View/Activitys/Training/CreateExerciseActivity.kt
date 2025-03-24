package com.example.fithealth.View.Activitys.Training

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.R
import com.example.fithealth.View.SpinnerAdapters.SpinnerAdapterCustom
import com.example.fithealth.ViewModel.Firebase.Firestore.Exercise.ExerciseFirestoreViewModel
import com.example.fithealth.ViewModel.Firebase.Firestore.Exercise.ExerciseFirestoreViewModelBuilder
import com.example.fithealth.databinding.ActivityCreateExerciseBinding
import com.example.fithealth.exerciseTypes
import com.example.fithealth.userId

class CreateExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateExerciseBinding

    private val exerciseViewModel: ExerciseFirestoreViewModel by viewModels {
        ExerciseFirestoreViewModelBuilder.getExerciseFirestoreViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        setupExerciseTypeSpinner()
        setupOnClick()
        setupObservers()
    }

    private fun setupExerciseTypeSpinner() {
        val adapter = SpinnerAdapterCustom(this, getTypeExercisesOptions())
        binding.spinnerExerciseType.adapter = adapter

    }


    private fun setupObservers() {
        exerciseViewModel.apply {
            insertExerciseStatus.observe(this@CreateExerciseActivity) { status ->
                if (status) exerciseAdded()
                else exerciseNotAdded()

            }
        }
    }

    private fun exerciseNotAdded() {
        toast("Ejercicio no añadido")
    }

    private fun exerciseAdded() {
        binding.etExerciseName.text?.clear()
        toast("Ejercicio añadido correctamente")
    }

    private fun setupOnClick() {
        binding.apply {
            btnBack.setOnClickListener { finish() }
            btnSaveExercise.setOnClickListener {
                val exercise = createExerciseByFields()
                exerciseViewModel.insertExercise(exercise)
            }
        }
    }

    private fun createExerciseByFields(): Exercise {
        binding.apply {
            val exerciseId = getUniqueExerciseId()
            val exerciseName = etExerciseName.text.toString()
            val exerciseType = getExerciseTypeSelectedByExerciseTypeSpinner()
            return Exercise(exerciseId, exerciseName, exerciseType)
        }
    }

    private fun getExerciseTypeSelectedByExerciseTypeSpinner(): String {
        binding.apply {
            val spinnerAdapter = spinnerExerciseType.adapter as SpinnerAdapterCustom
            val position = spinnerExerciseType.selectedItemPosition
            return spinnerAdapter.getTypeByPosition(position)
        }
    }

    private fun getUniqueExerciseId(): String {
        val userPrefix = userId?.take(5) ?: ""
        val uniqueSuffix = System.currentTimeMillis().toString().takeLast(4)
        return "$userPrefix-$uniqueSuffix"
    }

    private fun getTypeExercisesOptions(): List<Pair<String, Int>> =
        listOf(
            Pair(exerciseTypes[0], R.drawable.ic_corazon),
            Pair(exerciseTypes[1], R.drawable.ic_fuerza),
            Pair(exerciseTypes[2], R.drawable.ic_hipertrofia),
            Pair(exerciseTypes[3], R.drawable.ic_velocidad)
        )
}