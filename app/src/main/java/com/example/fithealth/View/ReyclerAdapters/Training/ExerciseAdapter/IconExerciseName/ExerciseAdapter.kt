package com.example.fithealth.View.ReyclerAdapters.Training.ExerciseAdapter.IconExerciseName

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.View.ReyclerAdapters.DiffUtils.ExerciseDiffCallback
import com.example.fithealth.databinding.ItemExerciseBinding

class ExerciseAdapter(
    private var exerciseList: MutableList<Exercise?>,
    private val setMessageVisibility: (Boolean) -> Unit
) :
    RecyclerView.Adapter<ExerciseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemExerciseBinding.inflate(layoutInflater, parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun getItemCount(): Int = exerciseList.size

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(exerciseList[position])
    }

    fun updateList (newList : List<Exercise?>){
        val diffCallback = ExerciseDiffCallback(exerciseList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        exerciseList = newList.toMutableList()

        setMessageVisibility.invoke(newList.isEmpty())

        diffResult.dispatchUpdatesTo(this)
    }


    fun addExerciseToList (exercise : Exercise){
        exerciseList.add(exercise)
        setMessageVisibility.invoke(false)
        notifyItemInserted(exerciseList.size-1)
    }

    fun getExerciseList() : List<Exercise?> = exerciseList
}