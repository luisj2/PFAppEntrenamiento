package com.example.fithealth.Model.Database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fithealth.Model.Database.Tables.Entitys.Meal
import java.time.LocalDate

@Dao
interface MealDao {
    //INSERTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal): Long

    //GET
    @Query(
        """
    SELECT m.*
    FROM meal m
    INNER JOIN user u
    ON m.userOwnerId = u.userId
    WHERE m.userOwnerId = :userId AND m.date = :date
        """
    )
    suspend fun getMealByUserIdAndDate(userId: String, date: LocalDate): Meal

    //UPDATE

    @Update
    fun updateMeal(meal : Meal) : Int



}