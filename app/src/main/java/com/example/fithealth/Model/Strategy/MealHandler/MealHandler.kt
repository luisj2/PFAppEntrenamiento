package com.example.fithealth.Model.Strategy.MealHandler

import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.Model.Database.Tables.Entitys.Meal

interface MealHandler {
    fun changeMealByFoodList(meal: Meal, foodListToAdd: List<Food>): Meal
}