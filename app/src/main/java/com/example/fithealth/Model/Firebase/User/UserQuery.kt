package com.example.fithealth.Model.Firebase.User

import com.example.fithealth.Model.DataClass.SearchUser

interface UserQuery {
    suspend fun getUserByName(name: String): List<SearchUser>
}