package com.example.fithealth.ViewModel.Auth

import com.example.fithealth.Model.Firebase.Auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object AuthViewModelBuilder {
    fun getAuthViewModelFactory(): AuthViewModelFactory =
        AuthViewModelFactory(
            AuthRepository(
                FirebaseAuth.getInstance(),
                FirebaseFirestore.getInstance()
            )
        )

}