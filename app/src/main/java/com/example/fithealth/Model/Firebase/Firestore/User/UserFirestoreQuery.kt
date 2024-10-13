package com.example.fithealth.Model.Firebase.Firestore.User

import com.example.fithealth.Model.DataClass.SearchUser

interface UserFirestoreQuery {
    suspend fun getUsersByName(name: String): List<SearchUser>

    suspend fun getUserById (id : String) : SearchUser?


}