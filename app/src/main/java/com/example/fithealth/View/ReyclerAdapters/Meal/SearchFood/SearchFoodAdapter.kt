package com.example.fithealth.View.ReyclerAdapters.Meal.SearchFood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.View.ReyclerAdapters.DiffUtils.FoodDiffCallback
import com.example.fithealth.databinding.ItemFoodSearchBinding

class SearchFoodAdapter(
    private var foodList: List<Food>,
    private val onCheckAgreeMeal: (food: Food) -> Unit,
    private val onUncheckDeleteMeal: (food: Food) -> Unit,
    private val shouldCheckFoodItem: (food: Food) -> Boolean
) :
    RecyclerView.Adapter<SearchFoodViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFoodViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            ItemFoodSearchBinding.inflate(layoutInflater, parent, false)

        return SearchFoodViewHolder(binding)
    }

    override fun getItemCount(): Int = foodList.size

    override fun onBindViewHolder(holder: SearchFoodViewHolder, position: Int) {
        val foodItem = foodList[position]
        holder.bind(foodItem)
        holder.oncheckMealSelectedListener(foodItem, onCheckAgreeMeal, onUncheckDeleteMeal)
        holder.shouldCheckFood(foodItem, shouldCheckFoodItem)
    }

    fun updateList(newList: List<Food>) {
        val diffCallback = FoodDiffCallback(foodList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        foodList = newList

        diffResult.dispatchUpdatesTo(this)
    }
}