package com.example.fithealth.ViewModel.Auth.Firestore.User

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.Model.Firebase.Firestore.User.UserFirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class UserFirestoreViewModel(private val repository: UserFirestoreRepository) : ViewModel() {

    private val _userListByName = MutableLiveData<List<SearchUser>?>()
    val userListByName: LiveData<List<SearchUser>?> get() = _userListByName

    private val _loggedUser = MutableLiveData<SearchUser?>()
    val loggedUser: LiveData<SearchUser?> get() = _loggedUser


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


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
            _loggedUser.value = repository.getUserById(FirebaseAuth.getInstance().uid.toString())
        }
    }

}