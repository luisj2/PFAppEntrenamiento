package com.example.fithealth.Model.FoodApi

import com.example.fithealth.Model.DataClass.FoodApiResources.FoodIdSearch.FoodIdSearchResponse
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.FoodNameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FatSecretApi {
    @GET("foods/search/v1")
    fun searchFood(
        @Query("method") method: String = "foods.search.v3",
        @Query("search_expression") foodName: String,
        @Query("max_results") maxResults: Int = 5,
        @Query("format") format: String = "json",
        @Query("region") region: String = "ES",
        @Query("language") language: String = "es"
    ): Call<FoodNameResponse>


    @GET("food/v4")
    fun searchFoodById (
        @Query("method") method : String = "food.get.v4",
        @Query("format") format: String = "json",
        @Query("food_id") id : String
    )  : Call<FoodIdSearchResponse>



}