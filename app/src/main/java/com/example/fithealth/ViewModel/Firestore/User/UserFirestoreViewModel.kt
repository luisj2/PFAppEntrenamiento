package com.example.fithealth.ViewModel.Auth.Firestore.User

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.UserSearch
import com.example.fithealth.Model.Database.Tables.Entitys.User
import com.example.fithealth.Model.Firebase.Firestore.User.UserFirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class UserFirestoreViewModel(private val repository: UserFirestoreRepository) : ViewModel() {

    private val curentUserId : String = FirebaseAuth.getInstance().currentUser!!.uid

    private val _userListByName = MutableLiveData<List<UserSearch>?>()
    val userListByName: LiveData<List<UserSearch>?> get() = _userListByName

    private val _loggedUser = MutableLiveData<UserSearch?>()
    val loggedUser: LiveData<UserSearch?> get() = _loggedUser


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _completeLoggedUser = MutableLiveData<User?>()
    val completeLoggedUser : LiveData<User?> get() = _completeLoggedUser



    fun getUserByName(name: String) {
        if (name.isNotEmpty()) {
            viewModelScope.launch {
                changeLoadingTo(true)
                _userListByName.value = repository.getUsersByName(name)
                changeLoadingTo(false)
            }
        }
    }

    private fun changeLoadingTo(loadingStatus: Boolean) {
        _isLoading.value = loadingStatus
    }

    fun clearSearchResults() {
        _userListByName.value = null
        changeLoadingTo(false)
    }

    fun getLoggedUser (){
        viewModelScope.launch {
            _loggedUser.value = repository.getUserSearchById(FirebaseAuth.getInstance().uid.toString())
        }
    }

    fun getCompleteLoggedUserById (id : String){
        viewModelScope.launch {
            _completeLoggedUser.value = repository.getUserById(id)
        }
    }

    fun insertToken (token : String){
        viewModelScope.launch {
            repository.insertTokenByUserId(curentUserId,token)
        }
    }


}