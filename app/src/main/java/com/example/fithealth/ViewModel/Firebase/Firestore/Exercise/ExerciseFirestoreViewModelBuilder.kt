package com.example.fithealth.ViewModel.Firebase.Firestore.Exercise

import com.example.fithealth.Model.Database.Firebase.Firestore.Exercise.ExerciseFirestoreRespository
import com.google.firebase.firestore.FirebaseFirestore

object ExerciseFirestoreViewModelBuilder {
    fun getExerciseFirestoreViewModelFactory() = ExerciseFirestoreViewModelFactory(
        ExerciseFirestoreRespository(FirebaseFirestore.getInstance())
    )
}