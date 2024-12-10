package com.example.fithealth.Model.Firebase.Realtime

import com.example.fithealth.Model.DataClass.UserSearch
import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.Model.Database.Tables.Entitys.Message

interface UserRealtimeQuery {

    //INSERTS
    suspend fun sendRequest(user: UserSearch): Boolean

    suspend fun addFriendToLoggedUser(user: UserSearch): Boolean

    suspend fun addLoggedInUserToFriend(friendId: String, loggedUser: UserSearch): Boolean

    suspend fun isSendMessageByContactId (message : Message) : Boolean

    //GET
    suspend fun getUserRequests(): List<UserSearch>

    suspend fun isPendingRequestFrom(id: String): Boolean

    suspend fun getAllUserContacts(): List<Contact>

    suspend fun getContactById(id: String): Contact?

    suspend fun getMessageSent (idOwner : String) : Message?

    suspend fun listenForMessages () : Message?

    //REMOVE
    suspend fun removeUserRequest(id: String): Boolean

    //OTHER
    suspend fun killMessageListener() : Boolean
}