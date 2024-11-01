package com.example.fithealth.ViewModel.Auth.Firestore.User

import com.example.fithealth.Model.Firebase.Firestore.User.UserFirestoreRepository
import com.google.firebase.firestore.FirebaseFirestore

object UserFirestoreViewModelBuilder {
    fun getUserViewModelFactory(): UserFirestoreViewModelFactory =
        UserFirestoreViewModelFactory(UserFirestoreRepository(FirebaseFirestore.getInstance()))


}