package com.example.fithealth.ViewModel.Local_Database.Day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithealth.Model.Database.Local_Database.Repositorys.Days.DayEntityRespositoryImpl

class DayEntityViewModelFactory(private val repository : DayEntityRespositoryImpl) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DayEntityRespositoryImpl::class.java)) return DayEntityViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}