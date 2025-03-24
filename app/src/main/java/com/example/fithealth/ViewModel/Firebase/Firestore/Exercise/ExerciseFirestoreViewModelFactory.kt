package com.example.fithealth.ViewModel.Firebase.Firestore.Exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithealth.Model.Database.Firebase.Firestore.Exercise.ExerciseFirestoreRespository

class ExerciseFirestoreViewModelFactory(private val repository : ExerciseFirestoreRespository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseFirestoreViewModel::class.java)) {
            return ExerciseFirestoreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


