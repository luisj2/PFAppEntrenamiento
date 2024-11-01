package com.example.fithealth.Model.Database.Relations.ContactAndMessages

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.Model.Database.Tables.Entitys.Message

data class ContactWithMessages (
    @Embedded val contact : Contact,

    @Relation(
        parentColumn = "contactId",
        entityColumn = "contactOwnerId"
    )

    val messages : List<Message>
)