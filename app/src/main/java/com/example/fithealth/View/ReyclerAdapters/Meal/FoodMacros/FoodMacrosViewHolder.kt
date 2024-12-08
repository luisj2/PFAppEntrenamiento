package com.example.fithealth.View.ReyclerAdapters.Meal.FoodMacros

import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodIdSearch.Serving
import com.example.fithealth.databinding.ItemMealMacrosBinding

class FoodMacrosViewHolder(private val binding: ItemMealMacrosBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Serving) {
        changeCalories(removeDecimals(item.calories))
        changeProtein(removeDecimals(item.protein))
        changeSugar(removeDecimals(item.sugar))
        changeFat(removeDecimals(item.fat))
        changeFiber(removeDecimals(item.fiber))
        changeIron(removeDecimals(item.iron))
        changeCarbohydrate(removeDecimals(item.carbohydrate))
    }

    private fun changeCarbohydrate(carbohydrate: String) {
        binding.tvCarbohydrate.text = carbohydrate
    }

    private fun changeIron(iron: String) {
        binding.tvIron.text = iron
    }

    private fun changeFiber(fiber: String) {
        binding.tvFiber.text = fiber
    }

    private fun changeFat(fat: String) {
        binding.tvFat.text = fat
    }

    private fun changeSugar(sugar: String) {
        binding.tvSugar.text = sugar
    }

    private fun changeProtein(protein: String) {
        binding.tvProtein.text = protein
    }

    private fun changeCalories(calories: String) {
        binding.tvCalories.text = calories
    }

    private fun removeDecimals(text: String): String {
        return try {
            text.toDouble().toInt().toString()
        } catch (e: Exception) {
            ""
        }
    }
}