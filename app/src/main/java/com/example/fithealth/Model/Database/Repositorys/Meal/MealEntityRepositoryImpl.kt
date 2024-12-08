package com.example.fithealth.Model.Database.Repositorys.Meal

import android.util.Log
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.Model.Database.Dao.MealDao
import com.example.fithealth.Model.Database.Tables.Entitys.Meal
import com.example.fithealth.mealTypes
import com.example.fithealth.userId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class MealEntityRepositoryImpl(private val mealDao: MealDao) : MealEntityRepository {

    companion object {
        private const val ERROR = "MealEntityError"
    }

    override suspend fun insertMeal(meal: Meal): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                mealDao.insertMeal(meal) > 0L
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun getMealByUserIdAndDate(date: LocalDate): Meal? {
        return withContext(Dispatchers.IO) {
            try {
                if (userId != null) mealDao.getMealByUserIdAndDate(userId!!, date)
                else throw IllegalStateException("No hay usuario Iniciado sesion")

            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                null
            }
        }
    }

    override suspend fun updateMeal(meal: Meal): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                mealDao.updateMeal(meal) > 0
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun clearFoodListFromMeal(
        date: LocalDate,
        mealType: String
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                if (userId != null) {
                    val meal = mealDao.getMealByUserIdAndDate(userId!!, date)
                    val updateMeal = removeFoodTypeList(mealType, meal)
                    mealDao.updateMeal(updateMeal)
                } else throw IllegalStateException("No hay usuario iniciado sesión")
                true
            } catch (e: IllegalStateException) {
                Log.e(ERROR, "Estado ilegal: ${e.message}", e)
                false
            } catch (e: Exception) {
                Log.e(ERROR, "Error inesperado: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun removeFoodFromMeal(
        date: LocalDate,
        mealType: String,
        food: Food
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                if (userId != null) {
                    val meal = mealDao.getMealByUserIdAndDate(userId!!, date)
                    val updateMeal = removeElementFromFoodTypeList(mealType, meal, food)
                    mealDao.updateMeal(updateMeal)
                } else throw IllegalStateException("No hay usuario iniciado sesión")
                true
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    private fun removeElementFromFoodTypeList(mealType: String, meal: Meal, food: Food): Meal {
        val updateList: MutableList<Food>? = when (mealType) {
            mealTypes[0] -> meal.breakfastMealList.toMutableList()
            mealTypes[1] -> meal.lunchMealList.toMutableList()
            mealTypes[2] -> meal.snackMealList.toMutableList()
            mealTypes[3] -> meal.dinnerMealList.toMutableList()
            else -> null
        }
        updateList?.remove(food)

        return meal.apply {
            when (mealType) {
                mealTypes[0] -> breakfastMealList = updateList!!
                mealTypes[1] -> lunchMealList = updateList!!
                mealTypes[2] -> snackMealList = updateList!!
                mealTypes[3] -> dinnerMealList = updateList!!
            }
        }

    }


    private fun removeFoodTypeList(mealType: String, meal: Meal): Meal {
        return when (mealType) {
            mealTypes[0] -> {
                meal.breakfastMealList = emptyList()
                meal
            }

            mealTypes[1] -> {
                meal.lunchMealList = emptyList()
                meal
            }

            mealTypes[2] -> {
                meal.snackMealList = emptyList()
                meal
            }

            mealTypes[3] -> {
                meal.dinnerMealList = emptyList()
                meal
            }

            else -> meal
        }
    }


}