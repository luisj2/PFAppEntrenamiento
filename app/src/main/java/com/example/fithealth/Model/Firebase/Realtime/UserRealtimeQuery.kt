package com.example.fithealth.Model.Firebase.Realtime

import com.example.fithealth.Model.DataClass.Contacto
import com.example.fithealth.Model.DataClass.SearchUser

interface UserRealtimeQuery {

    //INSERTS
    suspend fun sendRequest(user: SearchUser): Boolean

    suspend fun addFriendToLoggedUser(user: SearchUser): Boolean

    suspend fun addLoggedInUserToFriend(friendId: String, loggedUser: SearchUser): Boolean

    //GET
    suspend fun getUserRequests(): List<SearchUser>

    suspend fun isPendingRequestFrom(id: String) : Boolean

    //REMOVE
    suspend fun removeUserRequest(id: String): Boolean
}