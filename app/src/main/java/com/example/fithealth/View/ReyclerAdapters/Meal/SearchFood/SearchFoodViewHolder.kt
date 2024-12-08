package com.example.fithealth.View.ReyclerAdapters.Meal.SearchFood

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.R
import com.example.fithealth.View.Activitys.Meal.FoodInfoActivity
import com.example.fithealth.databinding.ItemFoodSearchBinding

class SearchFoodViewHolder(private val binding: ItemFoodSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        private const val FOOD_ID = "food_id"
    }

    fun bind(food: Food) {
        changeFoodImage()
        changeFoodName(food.food_name)
        changeFoodType(food.food_type)
        moveToFoodInfoActivity(food.food_id)
    }

    fun oncheckMealSelectedListener(
        food: Food,
        checkAgreeMeal: (food: Food) -> Unit,
        uncheckDeleteMeal: (food: Food) -> Unit
    ) {
        binding.checkboxAgreeMeal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) checkAgreeMeal(food)
            else uncheckDeleteMeal(food)
        }
    }

    fun shouldCheckFood(food: Food, shouldCheckFoodItem: (food: Food) -> Boolean) {
        if(shouldCheckFoodItem(food)) binding.checkboxAgreeMeal.isChecked = true
    }


    private fun moveToFoodInfoActivity(foodId: String) {
        binding.root.setOnClickListener {
            val intent = Intent(getContext(), FoodInfoActivity::class.java).apply {
                putExtra(FOOD_ID, foodId)
            }
            getContext().startActivity(intent)
        }
    }


    private fun changeFoodType(foodType: String) {
        binding.tvFoodType.text = foodType
    }

    private fun changeFoodName(foodName: String) {
        binding.tvFoodName.text = foodName
    }

    private fun changeFoodImage() {
        Glide.with(binding.root.context)
            .load(R.drawable.default_meal_image)
            .into(binding.ivFoodImage)

    }

    private fun getContext() = binding.root.context


}