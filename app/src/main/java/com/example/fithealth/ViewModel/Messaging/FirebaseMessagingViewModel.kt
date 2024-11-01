package com.example.fithealth.ViewModel.Messaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.Firebase.CloudMessaging.MessagingRepositoryImpl
import kotlinx.coroutines.launch

class FirebaseMessagingViewModel(private val repository: MessagingRepositoryImpl) : ViewModel() {
    private val _newToken = MutableLiveData<String?>()
    val newToken: LiveData<String?> get() = _newToken

    private val _deleteTokenStatus = MutableLiveData<Boolean>()
    val deleteTokenStatus: LiveData<Boolean> get() = _deleteTokenStatus


    fun getToken(onCompleate: (String?) -> Unit = {}) {
        viewModelScope.launch {
            val token = repository.getToken()
            _newToken.value = token
            onCompleate(token)
        }
    }

    fun deleteToken() {
        viewModelScope.launch {
            _deleteTokenStatus.value = repository.deleteToken()
        }
    }

}