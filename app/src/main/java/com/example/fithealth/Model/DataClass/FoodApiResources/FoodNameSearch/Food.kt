package com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch

data class Food(
    val brand_name: String,
    val food_description: String,
    val food_id: String,
    val food_name: String,
    val food_type: String,
    val food_url: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true //SON EL MISMO OBJETO
        val otherFood = other as? Food ?: return false
        return otherFood.food_id == food_id
    }
}