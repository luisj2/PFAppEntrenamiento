package com.example.fithealth.Model.Database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fithealth.Model.Database.Tables.Entitys.Contact

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact): Long

    @Query("""
        SELECT c.* FROM contact c
        INNER JOIN user u ON c.userOwnerId = u.userId
        WHERE u.userId = :userId
        """)
    suspend fun getAllContactsFromUserId(userId : String): List<Contact>





}
