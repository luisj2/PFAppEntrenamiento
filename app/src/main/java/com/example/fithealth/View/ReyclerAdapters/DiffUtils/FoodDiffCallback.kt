package com.example.fithealth.View.ReyclerAdapters.DiffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food

class FoodDiffCallback(
    private val oldList: List<Food>,
    private val newList: List<Food>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].food_id == newList[newItemPosition].food_id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].brand_name == newList[newItemPosition].brand_name


}