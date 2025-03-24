package com.example.fithealth.View.ReyclerAdapters.Training.RotineAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.Routine
import com.example.fithealth.View.ReyclerAdapters.DiffUtils.RoutineDiffCallback
import com.example.fithealth.databinding.ItemRoutineBinding

class RoutineAdapter(private var routineList: MutableList<Routine?> = mutableListOf(),private val onSelectRoutine : ((Routine) -> Unit)? = null,private val onDeselectRoutine : ((Routine) ->Unit)? = null) :
    RecyclerView.Adapter<RoutineViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRoutineBinding.inflate(inflater, parent, false)
        return RoutineViewHolder(binding)
    }

    override fun getItemCount(): Int = routineList.size

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        holder.bind(routineList[position],onSelectRoutine,onDeselectRoutine)
    }

    fun updateList(newList: List<Routine?>) {
        val diffCallback = RoutineDiffCallback(routineList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        routineList = newList.toMutableList()

        diffResult.dispatchUpdatesTo(this)
    }
}