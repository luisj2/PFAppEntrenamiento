package com.example.fithealth.Model.Database.Repositorys.Contacts

import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.Model.Database.Tables.Entitys.Message
import com.example.fithealth.Model.Database.Tables.Entitys.User

internal interface UserDatabaseRepository {

    //INSERTS
    suspend fun insertLoggedUser(user: User): Boolean
    suspend fun insertContact(contact: Contact): Boolean

    suspend fun insertMessageToContact(
        message: Message
    ): Boolean


    //GETS
    suspend fun getAllContactsFromLoggedUser(): List<Contact>

    suspend fun getContactMessages(contactId: String): List<Message>

    //DELETE


}