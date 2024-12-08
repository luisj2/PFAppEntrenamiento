package com.example.fithealth.ViewModel.Local_Database.Meal

import android.content.Context
import com.example.fithealth.Model.Database.DatabaseBuilder
import com.example.fithealth.Model.Database.Repositorys.Meal.MealEntityRepositoryImpl

object MealEntityViewModelBuilder {
    fun getMealEntityViewModelFactory(context: Context): MealEntityViewModelFactory {
        val database = DatabaseBuilder.provideDatabase(context)
        val mealDao = database.getMealDao()
        val repository = MealEntityRepositoryImpl(mealDao)
        return MealEntityViewModelFactory(repository)
    }
}