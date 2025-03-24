package com.example.fithealth.ViewModel.Auth.Realtime.User

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.UserSearch
import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.Model.Database.Tables.Entitys.Message
import com.example.fithealth.Model.Firebase.Realtime.UserRealtimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserRealtimeViewModel(private val repository: UserRealtimeRepository) : ViewModel() {

    private val _sendRequestStatus = MutableLiveData<Boolean>()
    val sendRequestStatus: LiveData<Boolean> get() = _sendRequestStatus

    private val _requestList = MutableLiveData<List<UserSearch>>()
    val requestList: LiveData<List<UserSearch>> get() = _requestList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _removeRequestStatus = MutableLiveData<Boolean>()
    val removeRequestStatus: LiveData<Boolean> get() = _removeRequestStatus

    private val _agreeFriendStatus = MutableLiveData<Boolean>()
    val agreeFriendStatus: LiveData<Boolean> get() = _agreeFriendStatus

    private val _agreePending = MutableLiveData<UserSearch>()
    val agreePending: LiveData<UserSearch> get() = _agreePending

    private val _contactList = MutableLiveData<List<Contact>>()
    val contactList: LiveData<List<Contact>> get() = _contactList

    private val _contactById = MutableLiveData<Contact?>()
    val contactById: LiveData<Contact?> get() = _contactById

    private val _isMessageSend = MutableLiveData<Boolean>()
    val isMessageSend: LiveData<Boolean> get() = _isMessageSend

    private val _loadingMessage = MutableStateFlow<Boolean?>(null)
    val loadingMessage: StateFlow<Boolean?> = _loadingMessage

    private val _messageSent = MutableStateFlow<Message?>(null)
    val messageSent: StateFlow<Message?> = _messageSent


    fun sendRequest(user: UserSearch) {
        viewModelScope.launch {
            changeLoadingTo(true)
            if (!repository.isPendingRequestFrom(user.contactId)) _sendRequestStatus.value =
                repository.sendRequest(user)
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

    fun agreeFriend(userToAgree: UserSearch, loggedUser: UserSearch) {
        viewModelScope.launch {
            changeLoadingTo(true)
            if (repository.removeUserRequest(userToAgree.contactId)) {
                repository.removeUserRequest(loggedUser.contactId)
                val result =
                    repository.addFriendToLoggedUser(userToAgree) && repository.addLoggedInUserToFriend(
                        userToAgree.contactId,
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

    fun getAllUserContacts() {
        viewModelScope.launch {
            _contactList.value = repository.getAllUserContacts()
        }
    }

    fun getContactById(id: String) {
        viewModelScope.launch {
            _contactById.value = repository.getContactById(id)
        }
    }

    private fun changeLoadingTo(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun sendMessage(message: Message) {
        viewModelScope.launch {
            val isMessageSend = repository.isSendMessageByContactId(message)
            _isMessageSend.value = isMessageSend
            if (isMessageSend) _loadingMessage.value = true
        }
    }

    fun getMessageSent(idOwner: String) {
        viewModelScope.launch {
            _messageSent.value = repository.getMessageSent(idOwner)
        }
    }


}