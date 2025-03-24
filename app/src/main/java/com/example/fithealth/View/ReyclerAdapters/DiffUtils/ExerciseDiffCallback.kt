package com.example.fithealth.View.ReyclerAdapters.DiffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.fithealth.Model.DataClass.Exercise

class ExerciseDiffCallback(
    private val oldList: List<Exercise?>,
    private val newList: List<Exercise?>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition]?.exerciseId == newList[newItemPosition]?.exerciseId


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}
