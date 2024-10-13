package com.example.fithealth.ViewModel.Auth

import com.example.fithealth.Model.Firebase.Auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

object AuthViewModelBuilder {
    fun getAuthViewModelFactory(): AuthViewModelFactory {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance(
            "https://fithealthpf-default-rtdb.europe-west1.firebasedatabase.app/"
        )

        return AuthViewModelFactory(
            AuthRepository(
                firebaseAuth,
                firebaseFirestore,
                firebaseDatabase
            )
        )
    }

}