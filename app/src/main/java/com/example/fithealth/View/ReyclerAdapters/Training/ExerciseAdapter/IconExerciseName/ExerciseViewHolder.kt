package com.example.fithealth.View.ReyclerAdapters.Training.ExerciseAdapter.IconExerciseName

import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.R
import com.example.fithealth.databinding.ItemExerciseBinding
import com.example.fithealth.exercisesTypesImagesMap

class ExerciseViewHolder(private val binding: ItemExerciseBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(exercise: Exercise?) {
        if(exercise != null) changeFieldsWithExercise(exercise) else changeFieldsWithNull()
    }

    private fun changeFieldsWithNull() {
        binding.apply {
            tvExerciseName.text = "???"
            ivExerciseImage.setImageResource(R.drawable.ic_ejercicio_predeterminado)
        }
    }

    private fun changeFieldsWithExercise(exercise: Exercise) {
        changeExerciseName(exercise.exerciseName)
        changeExerciseImage(exercise.exerciseType)
    }

    private fun changeExerciseImage(exerciseType: String) {
        val image = exercisesTypesImagesMap[exerciseType] ?: R.drawable.ic_ejercicio_predeterminado
        binding.ivExerciseImage.setImageResource(image)
    }


    private fun changeExerciseName(exerciseName: String) {
        binding.tvExerciseName.text = exerciseName
    }
}