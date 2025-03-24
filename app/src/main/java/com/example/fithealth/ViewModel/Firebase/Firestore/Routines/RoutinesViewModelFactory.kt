package com.example.fithealth.ViewModel.Firebase.Firestore.Routines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithealth.Model.Database.Firebase.Firestore.Routines.RoutinesRepository

class RoutinesViewModelFactory (private val repository : RoutinesRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST") // Para suprimir advertencias de cast no chequeado
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoutinesViewModel::class.java)) {
            return RoutinesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}