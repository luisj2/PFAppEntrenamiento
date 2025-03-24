package com.example.fithealth.View.ReyclerAdapters.DiffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.fithealth.Model.DataClass.Routine

class RoutineDiffCallback(
    private val oldList: List<Routine?>,
    private val newList: List<Routine?>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition]?.routineId == newList[newItemPosition]?.routineId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}