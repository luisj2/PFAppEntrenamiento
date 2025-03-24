package com.example.fithealth.View.ReyclerAdapters.Training.ExerciseAdapter.CreateRoutineExerciseList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.View.ReyclerAdapters.DiffUtils.ExerciseDiffCallback
import com.example.fithealth.databinding.ItemAddExerciseRoutineBinding

class AddExeriseToRoutineAdapter(
    private var exerciseList: List<Exercise?>,
    private val onRemoveExercise: (Exercise) -> Unit,
    private val onModifiyNoMessagesVisibility: (Boolean) -> Unit
)
    : RecyclerView.Adapter<AddExerciseToRoutineViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddExerciseToRoutineViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAddExerciseRoutineBinding.inflate(layoutInflater,parent,false)
        return AddExerciseToRoutineViewHolder(binding)
    }

    override fun getItemCount(): Int = exerciseList.size

    override fun onBindViewHolder(holder: AddExerciseToRoutineViewHolder, position: Int) {
        val exercise = exerciseList[position]
        holder.bind(exercise,onRemoveExercise)
    }

    fun removeExercise(exercise : Exercise){
    updateList(exerciseList.minus(exercise))
    onModifiyNoMessagesVisibility(exerciseList.isEmpty())
    }

    fun updateList (newList : List<Exercise?>){
        val diffCallback = ExerciseDiffCallback(exerciseList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        exerciseList = newList.toMutableList()
        onModifiyNoMessagesVisibility(exerciseList.isEmpty())

        diffResult.dispatchUpdatesTo(this)
    }
}