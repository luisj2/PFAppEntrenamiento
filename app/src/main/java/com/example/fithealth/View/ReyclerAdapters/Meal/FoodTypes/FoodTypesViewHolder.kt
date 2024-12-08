package com.example.fithealth.View.ReyclerAdapters.Meal.FoodTypes

import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.databinding.ItemFoodTypeBinding

class FoodTypesViewHolder(private val binding : ItemFoodTypeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(food : Food){
        changeFoodName(food.food_name)
    }

    fun setupRemoveElementOnClick(food : Food, removeElementInDB : (food : Food) -> Unit,removeElementInList : (food : Food) ->Unit) {
        binding.apply {
            btnRemoveFood.setOnClickListener{
                removeElementInDB(food)
            }
        }
    }

    private fun changeFoodName(foodName: String) {
        binding.tvFoodName.text = foodName
    }
}