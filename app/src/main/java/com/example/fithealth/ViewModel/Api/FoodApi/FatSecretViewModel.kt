package com.example.fithealth.ViewModel.FoodApi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.FoodApi.FatSecretRepository
import kotlinx.coroutines.launch
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodIdSearch.Food as FoodById
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food as FoodByName

class FatSecretViewModel(private val repository: FatSecretRepository) : ViewModel() {

    private val _FoodListResult = MutableLiveData<List<FoodByName>>()
    val foodListResult: LiveData<List<FoodByName>> get() = _FoodListResult

    private val _foodResult = MutableLiveData<FoodById?>()
    val foodResult: LiveData<FoodById?> get() = _foodResult

    private val _foodAgreeList = MutableLiveData<List<FoodByName>>()
    val foodAgreeList: LiveData<List<FoodByName>> get() = _foodAgreeList


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun searchFood(foodName: String) {
        viewModelScope.launch {
            changeLoadingTo(true)
            _FoodListResult.value = repository.searchFood(foodName)
            changeLoadingTo(false)
        }
    }

    fun searchFoodById(id: String) {
        viewModelScope.launch {
            changeLoadingTo(true)
            _foodResult.value = repository.searchFoodById(id)
            changeLoadingTo(false)
        }
    }

    fun addFoodToAgreeList(food: FoodByName) {
        val currentList = toMutableListOrEmpty(_foodAgreeList.value)
        if (!currentList.contains(food)) {
            currentList.add(food)
            _foodAgreeList.value = currentList
        }
    }

    fun removeAgreedFood(food: FoodByName) {
        val currentList = toMutableListOrEmpty(_foodAgreeList.value)
        if (currentList.contains(food)) {
            currentList.remove(food)
            _foodAgreeList.value = currentList
        }
    }

    fun clearAgreedFoodList() {
        val currentList = _foodAgreeList.value.orEmpty().toMutableList()
        currentList.clear()
        _foodAgreeList.value = currentList
    }

    fun isFoodInList(food: FoodByName): Boolean = _foodAgreeList.value?.contains(food) ?: false


    private fun <T> toMutableListOrEmpty(list: List<T>?): MutableList<T> =
        list?.toMutableList() ?: mutableListOf()


    private fun changeLoadingTo(status: Boolean) {
        _loading.value = status
    }
}