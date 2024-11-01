package com.example.fithealth.ViewModel.Local_Database.User

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithealth.Model.Database.Repositorys.Contacts.UserDatabaseRepositoryImpl

class UserDatabaseViewModelFactory(private val repository: UserDatabaseRepositoryImpl) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDatabaseViewModel::class.java)) {
            return UserDatabaseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}