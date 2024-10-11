package com.example.fithealth.ViewModel.Auth.Realtime.User

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.Model.Firebase.Realtime.UserRealtimeRepository
import kotlinx.coroutines.launch

class UserRealtimeViewModel (private val repository : UserRealtimeRepository) : ViewModel() {

    private val _sendRequestStatus = MutableLiveData<Boolean>()
    val sendRequestStatus : LiveData<Boolean> get() = _sendRequestStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading
    fun sendRequest (user : SearchUser){
        viewModelScope.launch {
            changeLoadingTo(true)
            _sendRequestStatus.value = repository.sendRequest(user)
            changeLoadingTo(false)
        }
    }
    private fun changeLoadingTo(isLoading : Boolean){
        _isLoading.value = isLoading
    }
}