package com.example.fithealth.Model.FoodApi

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodIdSearch.Food as FoodById
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food as FoodByName

class FatSecretRepository(private val api: FatSecretApi) : FatSecretQuerys {

    companion object {
        private const val ERROR = "food_api_error"
    }

    override suspend fun searchFood(foodName: String): List<FoodByName> {
        return withContext(Dispatchers.IO) {
            try {
                api.searchFood(foodName = foodName).await().foods.food
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                emptyList()
            }
        }
    }

    override suspend fun searchFoodById(foodId: String): FoodById? {
        return withContext(Dispatchers.IO){
            try {
                api.searchFoodById(id = foodId).await().food
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                null
            }
        }
    }

}