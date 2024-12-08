package com.example.fithealth.ViewModel.Local_Database.Meal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.Model.Database.Repositorys.Meal.MealEntityRepositoryImpl
import com.example.fithealth.Model.Database.Tables.Entitys.Meal
import com.example.fithealth.userId
import kotlinx.coroutines.launch
import java.time.LocalDate

class MealEntityViewModel(private val repository: MealEntityRepositoryImpl) : ViewModel() {


    private val _insertMealStatus = MutableLiveData<Boolean>()
    val insertMealStatus: LiveData<Boolean> get() = _insertMealStatus

    private val _mealByUserIdAndDate = MutableLiveData<Meal?>()
    val mealByUserIdAndDate: LiveData<Meal?> get() = _mealByUserIdAndDate

    private val _removeFoodStatus = MutableLiveData<Boolean>()
    val removeFoodStatus: LiveData<Boolean> get() = _removeFoodStatus

    private val _updateMealStatus = MutableLiveData<Boolean>()
    val updateMealStatus: LiveData<Boolean> get() = _updateMealStatus



    fun insertMeal(meal: Meal,onResult: ((Boolean) -> Unit)? = null) {
        viewModelScope.launch {
            userId?.let {userId->
                meal.userOwnerId = userId
                val result = repository.insertMeal(meal)
                _insertMealStatus.postValue(result)
                onResult?.invoke(result)
            }
        }
    }

    fun getMealByDate(date: LocalDate, onResult: ((Meal?) -> Unit)? = null) {
        viewModelScope.launch {
            val result = repository.getMealByUserIdAndDate(date)
            _mealByUserIdAndDate.postValue(result)
            Log.i("mealState","he hecho el get el valor es $result")
            onResult?.invoke(result)
        }
    }
    fun updateMeal (meal : Meal,onResult: ((Boolean) -> Unit)? = null){
        viewModelScope.launch {
            val result = repository.updateMeal(meal)
            _updateMealStatus.postValue(result)
            onResult?.invoke(result)
        }
    }


    fun clearFoodListFromMeal(date: LocalDate, mealType: String) {
        viewModelScope.launch {
            _removeFoodStatus.value = repository.clearFoodListFromMeal(date, mealType)
        }
    }

    fun removeFoodFromMeal(date: LocalDate, mealType: String, foodToRemove: Food) {
        viewModelScope.launch {
            repository.removeFoodFromMeal(date, mealType, foodToRemove)
        }
    }

}