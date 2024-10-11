package com.example.fithealth.Model.Firebase.Firestore.User

import com.example.fithealth.Model.DataClass.SearchUser

interface UserFirestoreQuery {
    suspend fun getUserByName(name: String): List<SearchUser>
}