package com.example.fithealth.ViewModel.Auth.User

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.Model.Firebase.User.UserQuery
import com.example.fithealth.Model.Firebase.User.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _userListByName = MutableLiveData<List<SearchUser>?>()
    val userListByName: LiveData<List<SearchUser>?> get() = _userListByName

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getUserByName(name: String) {
        if (name.isNotEmpty()) {
            viewModelScope.launch {
                changeLoadingTo(true)
                _userListByName.value = repository.getUserByName(name)
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

}