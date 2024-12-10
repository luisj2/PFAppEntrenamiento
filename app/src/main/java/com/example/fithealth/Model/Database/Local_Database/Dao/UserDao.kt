package com.example.fithealth.Model.Database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.fithealth.Model.Database.Relations.UserAndContacts.ContactsOfUser
import com.example.fithealth.Model.Database.Tables.Entitys.User

@Dao
interface UserDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : User) : Long

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :id")
    suspend fun getContactOfUser(id : String) : ContactsOfUser
}