package com.example.fithealth.View.ReyclerAdapters.Training.ExerciseToRoutineAdapter

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.R
import com.example.fithealth.databinding.ItemExerciseToRoutineBinding
import com.example.fithealth.exercisesTypesImagesMap

//ARREGLAR EL TEMA DEL CODIGO DEL BIND QUE TENGO MEJOR HECHO EN CHAT GPT
class ExerciseToRoutineViewHolder(private val binding: ItemExerciseToRoutineBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var isBtnSelected = false

    fun bind(
        exercise: Exercise?,
        exerciseSelected: (Exercise) -> Unit,
        exerciseDeselected: (Exercise) -> Unit
    ) {
        if (exercise == null) {
            compleateNullFields()
            return
        }

        compleateFieldsWithExerciseInfo(
            exercise,
            exerciseSelected = exerciseSelected,
            exerciseDeselected = exerciseDeselected
        )

    }

    private fun compleateFieldsWithExerciseInfo(
        exercise: Exercise,
        exerciseSelected: (Exercise) -> Unit,
        exerciseDeselected: (Exercise) -> Unit
    ) {
        binding.apply {
            ivExerciseType.setImageResource(
                exercisesTypesImagesMap[exercise.exerciseType] ?: R.drawable.error_image
            )
            tvExerciseName.text = exercise.exerciseName
            btnAddToRoutine.setOnClickListener {
                if (isBtnSelected) handleDeselectExercise(exerciseDeselected, exercise)
                else handleSelectExercise(exercise, exerciseSelected)

            }
        }
    }

    private fun handleSelectExercise(
        exercise: Exercise,
        exerciseSelected: (Exercise) -> Unit
    ) {
        exerciseSelected.invoke(exercise)
        changeButtonToSelected()
    }

    private fun handleDeselectExercise(
        exerciseDeselected: (Exercise) -> Unit,
        exercise: Exercise
    ) {
        exerciseDeselected.invoke(exercise)
        changeButtonToDeselected()
    }

    private fun changeButtonToDeselected() {
        binding.btnAddToRoutine.apply {
            isBtnSelected = false
            text = "+"
            background.setTint(ContextCompat.getColor(context, R.color.verdeAceptar))
        }
    }

    private fun changeButtonToSelected() {
        binding.btnAddToRoutine.apply {
            isBtnSelected = true
            text = "-"
            background.setTint(ContextCompat.getColor(context, R.color.rojoRechazar))
        }
    }

    private fun compleateNullFields() {
        binding.apply {
            ivExerciseType.setImageResource(R.drawable.error_image)
            tvExerciseName.text = "???"
            btnAddToRoutine.setOnClickListener {
                Toast.makeText(
                    root.context,
                    "Ha habido un error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}