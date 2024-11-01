package com.example.fithealth.Model.Database.Tables.Entitys

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "contact",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userOwnerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Contact(
    @PrimaryKey(autoGenerate = false) val contactId: String = "",
    val userOwnerId: String="",
    val userName: String = "",
    val uniqueName: String = "",
    val icon: Int = 0
)
