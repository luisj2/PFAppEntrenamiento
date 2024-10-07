package com.example.fithealth.Model.Firebase.Auth

import com.example.fithealth.Model.DataClass.AuthUser

interface LoginService {
    suspend fun logInWithEmailAndPassword(email : String,password : String) : Boolean


}