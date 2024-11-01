package com.example.fithealth.ViewModel.Local_Database.User

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.UserSearch
import com.example.fithealth.Model.Database.Repositorys.Contacts.UserDatabaseRepositoryImpl
import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.Model.Database.Tables.Entitys.Message
import com.example.fithealth.Model.Database.Tables.Entitys.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDatabaseViewModel(private val repository: UserDatabaseRepositoryImpl) : ViewModel() {

    private val _inserContactStatus = MutableLiveData<Boolean>()
    val inserContactStatus: LiveData<Boolean> get() = _inserContactStatus

    private val _insertUserStatus = MutableLiveData<Boolean>()
    val inserUserStatus: LiveData<Boolean> get() = _insertUserStatus

    private val _contactList = MutableLiveData<List<Contact>>()
    val contactList: LiveData<List<Contact>> get() = _contactList

    private val _messageList = MutableStateFlow<List<Message>>(emptyList())
    val messageList : StateFlow<List<Message>> = _messageList

    private val _messageStatus  = MutableStateFlow<Boolean?>(null)
    val messageStatus : StateFlow<Boolean?> = _messageStatus





    fun insertContact(user: UserSearch, loggedUserId: String) {
        viewModelScope.launch {
            val contact = Contact(user.contactId,loggedUserId,user.userName,user.uniqueName,user.icon)
            _inserContactStatus.value = repository.insertContact(contact)
        }
    }

    fun inserUser(user: User) {
        viewModelScope.launch {
            _insertUserStatus.value = repository.insertLoggedUser(user)
        }
    }

    fun getAllContactsFromLoggedUser (){
        viewModelScope.launch {
            _contactList.value = repository.getAllContactsFromLoggedUser()
        }
    }

    fun getMessageList (contactId : String){

        viewModelScope.launch {

            _messageList.value = repository.getContactMessages(contactId)
        }
    }

    fun insertMessageToContact (message : Message){
        viewModelScope.launch {
            _messageStatus.value = repository.insertMessageToContact(message)
        }
    }
}