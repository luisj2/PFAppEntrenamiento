package com.example.fithealth.Model.Firebase.Realtime

import com.example.fithealth.Model.DataClass.SearchUser

interface UserRealtimeQuery {
    //INSERTS
    suspend fun sendRequest(user : SearchUser) : Boolean
}