package com.example.fithealth.Model.FoodApi

import com.example.fithealth.Model.DataClass.FoodApiResources.FoodIdSearch.Food as FoodById
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food as FoodByName

interface FatSecretQuerys {
    suspend fun searchFood(foodName : String) : List<FoodByName>

    suspend fun searchFoodById (id : String) : FoodById?
}