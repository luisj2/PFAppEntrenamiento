package com.example.fithealth.ViewModel.FoodApi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithealth.Model.FoodApi.FatSecretRepository

class FatSecretViewModelFactory(private val repository: FatSecretRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FatSecretViewModel::class.java)) return FatSecretViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}