package com.example.fithealth.Model.Firebase.Auth

import com.example.fithealth.Model.DataClass.AuthUser

interface RegisterService {
    suspend fun registerWithEmailAndPassword(email: String, password: String): Boolean
    suspend fun registerUserInFirestore(authUser: AuthUser) : Boolean

    suspend fun registerUserInRealtime (id : String) : Boolean
}