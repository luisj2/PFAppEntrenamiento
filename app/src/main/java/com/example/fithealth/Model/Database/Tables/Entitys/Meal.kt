package com.example.fithealth.Model.Database.Tables.Entitys

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import java.time.LocalDate

@Entity(
    tableName = "meal",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userOwnerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Meal(
    @PrimaryKey val date: LocalDate,
    var userOwnerId: String = "",
    var breakfastMealList: List<Food> = emptyList(),
    var lunchMealList: List<Food> = emptyList(),
    var snackMealList: List<Food> = emptyList(),
    var dinnerMealList: List<Food> = emptyList()
)
