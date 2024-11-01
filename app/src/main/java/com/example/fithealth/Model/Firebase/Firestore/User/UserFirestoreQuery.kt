package com.example.fithealth.Model.Firebase.Firestore.User

import com.example.fithealth.Model.DataClass.UserSearch
import com.example.fithealth.Model.Database.Tables.Entitys.User

interface UserFirestoreQuery {

    //INSERT

    suspend fun insertTokenByUserId (userId : String,token : String) : Boolean

    //GET
    suspend fun getUsersByName(name: String): List<UserSearch>

    suspend fun getUserSearchById (id : String) : UserSearch?

    suspend fun getUserById(id : String) : User?

    //DELETE

    suspend fun removeTokenByUserId (userId : String,tokenToDelete : String) : Boolean




}