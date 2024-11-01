package com.example.fithealth.Model.Database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fithealth.Model.Database.Tables.Entitys.Message

@Dao
interface MessageDao {

    //INSERTS

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertMessage(message : Message) : Long

    //QUERYS

    @Query(
        """
    SELECT m.* 
    FROM message m 
    INNER JOIN contact c 
    ON c.contactId = m.contactOwnerId 
    WHERE m.contactOwnerId = :contactId 
    ORDER BY m.messageDate ASC
"""
    )
    suspend fun getMessagesByContactId(contactId: String): List<Message>
}