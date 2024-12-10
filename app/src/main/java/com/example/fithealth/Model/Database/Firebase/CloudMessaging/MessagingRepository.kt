package com.example.fithealth.Model.Firebase.CloudMessaging

interface MessagingRepository {

    suspend fun getToken () : String?

    suspend fun deleteToken() : Boolean

}