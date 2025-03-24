package com.example.fithealth.View.ReyclerAdapters.Training.ExerciseAdapter.CreateRoutineExerciseList

import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.R
import com.example.fithealth.databinding.ItemAddExerciseRoutineBinding
import com.example.fithealth.exercisesTypesImagesMap

class AddExerciseToRoutineViewHolder(private val binding: ItemAddExerciseRoutineBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val maxExerciseSeries = 6
    fun bind(exercise: Exercise?,removeExercise : (Exercise) -> Unit) {
        if (exercise == null) completeFieldsWithNullInformation() else completeFieldsWithExercise(exercise,removeExercise)
    }

    private fun completeFieldsWithExercise(exercise: Exercise, removeExercise: (Exercise) -> Unit) {
        changeExerciseName(exercise.exerciseName)
        changeExerciseIcon(exercise.exerciseType)
        setupSeriesSpinner()
        setupRemoveExerciseOnClick(exercise, removeExercise)
    }

    private fun setupRemoveExerciseOnClick(exercise: Exercise, onRemoveExercise: (Exercise) -> Unit) {
        binding.btnDeleteExercise.setOnClickListener {
            onRemoveExercise(exercise)
        }
    }


    private fun setupSeriesSpinner() {
        val options = List(maxExerciseSeries) { "${it + 1} Serie/es" }

        val adapter = ArrayAdapter(binding.root.context,android.R.layout.simple_spinner_item,options)
        binding.spinnerSeriesNumber.adapter = adapter
    }

    private fun changeExerciseIcon(exerciseType: String) {
        val image = exercisesTypesImagesMap[exerciseType] ?: R.drawable.ic_ejercicio_predeterminado
        binding.ivExerciseImage.apply {
            setImageResource(image)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun changeExerciseName(exerciseName: String) {
        binding.tvExerciseName.text = exerciseName
    }

    private fun completeFieldsWithNullInformation() {
        binding.apply {
            ivExerciseImage.setImageResource(R.drawable.error_image)
            tvExerciseName.text = "???"
            btnDeleteExercise.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "Ha ocurrido un error con este ejercicio, inténtalo más tarde",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}