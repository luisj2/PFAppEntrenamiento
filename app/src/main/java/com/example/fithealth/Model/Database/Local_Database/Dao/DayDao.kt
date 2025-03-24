package com.example.fithealth.Model.Database.Local_Database.Dao

import DayEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(day : DayEntity) : Long


    @Update
    suspend fun updateDay(dayEntity : DayEntity)


    @Query("SELECT * FROM dayEntity WHERE dayId = :dayId")
    suspend fun getDayById(dayId : String) : DayEntity?


}