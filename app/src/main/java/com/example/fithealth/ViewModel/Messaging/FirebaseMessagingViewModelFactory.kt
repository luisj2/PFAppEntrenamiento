package com.example.fithealth.ViewModel.Messaging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.fithealth.Model.Firebase.CloudMessaging.MessagingRepositoryImpl

class FirebaseMessagingViewModelFactory(private val repository: MessagingRepositoryImpl) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(FirebaseMessagingViewModel::class.java)) return FirebaseMessagingViewModel(
            repository
        ) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}