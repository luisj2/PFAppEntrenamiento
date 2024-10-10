package com.example.fithealth.ViewModel.Auth.User

import com.example.fithealth.Model.Firebase.User.UserRepository
import com.google.firebase.firestore.FirebaseFirestore

object UserViewModelBuilder {
    fun getUserViewModelFactory(): UserViewModelFactory =
        UserViewModelFactory(UserRepository(FirebaseFirestore.getInstance()))


}