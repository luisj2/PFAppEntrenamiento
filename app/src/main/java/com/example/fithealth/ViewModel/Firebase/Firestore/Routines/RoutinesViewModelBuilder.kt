package com.example.fithealth.ViewModel.Firebase.Firestore.Routines

import com.example.fithealth.Model.Database.Firebase.Firestore.Routines.RoutinesRepository
import com.google.firebase.firestore.FirebaseFirestore

object RoutinesViewModelBuilder {
    fun getRoutinesViewModelFactory(): RoutinesViewModelFactory = RoutinesViewModelFactory(
        RoutinesRepository(FirebaseFirestore.getInstance())
    )
}