package com.example.fithealth.ViewModel.Auth.Realtime.User

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.Model.Firebase.Realtime.UserRealtimeRepository
import kotlinx.coroutines.launch

class UserRealtimeViewModel(private val repository: UserRealtimeRepository) : ViewModel() {

    private val _sendRequestStatus = MutableLiveData<Boolean>()
    val sendRequestStatus: LiveData<Boolean> get() = _sendRequestStatus

    private val _requestList = MutableLiveData<List<SearchUser>>()
    val requestList: LiveData<List<SearchUser>> get() = _requestList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _removeRequestStatus = MutableLiveData<Boolean>()
    val removeRequestStatus: LiveData<Boolean> get() = _removeRequestStatus

    private val _agreeFriendStatus = MutableLiveData<Boolean>()
    val agreeFriendStatus: LiveData<Boolean> get() = _agreeFriendStatus

    private val _agreePending = MutableLiveData<SearchUser>()
    val agreePending: LiveData<SearchUser> get() = _agreePending



    fun sendRequest(user: SearchUser) {
        viewModelScope.launch {
            changeLoadingTo(true)
            if(!repository.isPendingRequestFrom(user.id)) _sendRequestStatus.value = repository.sendRequest(user)
            else _agreePending.value = user
            changeLoadingTo(false)
        }
    }

    fun getUserRequests() {
        viewModelScope.launch {
            _requestList.value = repository.getUserRequests()
        }
    }

    fun removeUserRequest(id: String) {
        viewModelScope.launch {
            changeLoadingTo(true)
            _removeRequestStatus.value = repository.removeUserRequest(id)
            changeLoadingTo(false)
        }
    }

    fun agreeFriend(userToAgree: SearchUser, loggedUser: SearchUser) {
        viewModelScope.launch {
            changeLoadingTo(true)
            if (repository.removeUserRequest(userToAgree.id)) {
                val result =
                    repository.addFriendToLoggedUser(userToAgree) && repository.addLoggedInUserToFriend(
                        userToAgree.id,
                        loggedUser
                    )
                _agreeFriendStatus.value = result

                //update the results with the requests
                if (result) getUserRequests()
            } else {
                _agreeFriendStatus.value = false
            }
            changeLoadingTo(false)
        }
    }

    private fun changeLoadingTo(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}