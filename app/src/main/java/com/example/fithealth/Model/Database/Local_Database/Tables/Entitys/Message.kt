package com.example.fithealth.Model.Database.Tables.Entitys

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.fithealth.Model.Utils.DateUtils
import java.time.LocalDateTime

@Entity(
    tableName = "message",
    foreignKeys = [ForeignKey(
        entity = Contact::class,
        parentColumns = ["contactId"],
        childColumns = ["contactOwnerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Message(
    @PrimaryKey(autoGenerate = true)
val messageId: Long = 0,
val contactOwnerId: String = "",
val messageContent: String,
val messageHour: String = DateUtils.dateToHourAndMinutes(LocalDateTime.now()),
val messageDate: LocalDateTime = LocalDateTime.now(),
var messageMine: Boolean
)