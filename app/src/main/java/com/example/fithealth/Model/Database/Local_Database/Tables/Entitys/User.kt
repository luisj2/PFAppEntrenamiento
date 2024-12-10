package com.example.fithealth.Model.Database.Tables.Entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = false) var userId : String = "",
    var userName : String = "",
    var email : String="",
    var password : String="",
    var uniqueName : String="",
    var icon : Int = 0
)