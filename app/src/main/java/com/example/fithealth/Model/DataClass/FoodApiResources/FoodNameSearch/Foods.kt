package com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch

data class Foods(
    val food: List<Food>,
    val max_results: String,
    val page_number: String,
    val total_results: String
)