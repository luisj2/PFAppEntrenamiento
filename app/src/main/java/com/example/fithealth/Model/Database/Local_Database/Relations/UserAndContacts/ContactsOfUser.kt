package com.example.fithealth.Model.Database.Relations.UserAndContacts

import androidx.room.Embedded
import androidx.room.Relation
import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.Model.Database.Tables.Entitys.User

data class ContactsOfUser(
    @Embedded val userEntity : User,

    @Relation(
       parentColumn = "userId",
       entityColumn = "contactId"
   )

    val contactList : List<Contact>
)