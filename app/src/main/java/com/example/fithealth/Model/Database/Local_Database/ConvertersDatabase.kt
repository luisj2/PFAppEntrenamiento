package com.example.fithealth.Model.Database

import androidx.room.TypeConverter
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.Model.DataClass.Routine
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConvertersDatabase {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val gson = Gson()

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @TypeConverter
    fun toLocalDateTime(dateString: String?): LocalDateTime? {
        return dateString?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) }
    }

    @TypeConverter
    fun fromString(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, formatter) }
    }

    @TypeConverter
    fun dateToString(date: LocalDate?): String? {
        return date?.format(formatter)
    }


    @TypeConverter
    fun fromFoodList(foods: List<Food>?): String? {
        return gson.toJson(foods)
    }

    @TypeConverter
    fun toFoodList(foodString: String?): List<Food>? {
        return foodString?.let {
            val type = object : TypeToken<List<Food>>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromRoutineList(routines: List<Routine?>?): String? {
        return routines?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toRoutineList(routinesString: String?): List<Routine?>? {
        return routinesString?.let {
            val type = object : TypeToken<List<Routine>>() {}.type
            gson.fromJson(it, type)
        }
    }


}