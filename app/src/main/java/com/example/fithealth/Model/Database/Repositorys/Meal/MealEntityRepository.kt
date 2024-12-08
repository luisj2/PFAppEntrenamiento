package com.example.fithealth.Model.Database.Repositorys.Meal

import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.Model.Database.Tables.Entitys.Meal
import java.time.LocalDate

interface MealEntityRepository {
    //INSERT
    suspend fun insertMeal (meal : Meal) : Boolean

    //GET
    suspend fun getMealByUserIdAndDate (date : LocalDate) : Meal?

    //UPDATE
    suspend fun updateMeal (meal : Meal) : Boolean

    //DELETE
    suspend fun clearFoodListFromMeal (date : LocalDate, mealType : String) : Boolean

    suspend fun removeFoodFromMeal (date : LocalDate,mealType : String,food : Food) : Boolean

}