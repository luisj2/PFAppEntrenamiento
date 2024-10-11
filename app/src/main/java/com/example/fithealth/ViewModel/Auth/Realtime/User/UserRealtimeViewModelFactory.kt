package com.example.fithealth.ViewModel.Auth.Realtime.User

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fithealth.Model.Firebase.Realtime.UserRealtimeRepository

class UserRealtimeViewModelFactory(private val repository: UserRealtimeRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserRealtimeViewModel::class.java))
            return UserRealtimeViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}