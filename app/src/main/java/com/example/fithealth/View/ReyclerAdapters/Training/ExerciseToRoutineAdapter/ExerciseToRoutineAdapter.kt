package com.example.fithealth.View.ReyclerAdapters.Training.ExerciseToRoutineAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.View.ReyclerAdapters.DiffUtils.ExerciseDiffCallback
import com.example.fithealth.databinding.ItemExerciseToRoutineBinding

class ExerciseToRoutineAdapter(
    private var exerciseList: List<Exercise?>,
    private val exerciseSelected: (Exercise) -> Unit,
    private val exerciseDeselected: (Exercise) -> Unit
) :
    RecyclerView.Adapter<ExerciseToRoutineViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseToRoutineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemExerciseToRoutineBinding.inflate(inflater, parent, false)
        return ExerciseToRoutineViewHolder(binding)
    }

    override fun getItemCount(): Int = exerciseList.size

    override fun onBindViewHolder(holder: ExerciseToRoutineViewHolder, position: Int) {
        holder.bind(exerciseList[position],exerciseSelected,exerciseDeselected)
    }

    fun updateList (newList : List<Exercise?>){
        val diffCallback = ExerciseDiffCallback(exerciseList,newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        exerciseList = newList

        diffResult.dispatchUpdatesTo(this)
    }

    fun getExerciseList() : List<Exercise?> = exerciseList
}