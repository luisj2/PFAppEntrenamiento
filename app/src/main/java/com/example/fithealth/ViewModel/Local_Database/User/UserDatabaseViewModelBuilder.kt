package com.example.fithealth.ViewModel.Local_Database.User

import android.content.Context
import com.example.fithealth.Model.Database.DatabaseBuilder
import com.example.fithealth.Model.Database.Repositorys.Contacts.UserDatabaseRepositoryImpl

object UserDatabaseViewModelBuilder {
    fun getUserDatabaseViewModelFactory(context: Context): UserDatabaseViewModelFactory {
        val database = DatabaseBuilder.provideDatabase(context)
        val contactDao = database.getContactsDao()
        val userDao = database.getUserDao()
        val messageDao = database.getMessageDao()
        return UserDatabaseViewModelFactory(UserDatabaseRepositoryImpl(contactDao,userDao,messageDao))
    }
}

