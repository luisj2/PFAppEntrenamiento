package com.example.fithealth.ViewModel.Local_Database.Meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithealth.Model.Database.Repositorys.Meal.MealEntityRepositoryImpl

class MealEntityViewModelFactory(private val repository: MealEntityRepositoryImpl) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealEntityViewModel::class.java)) {
            return MealEntityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}