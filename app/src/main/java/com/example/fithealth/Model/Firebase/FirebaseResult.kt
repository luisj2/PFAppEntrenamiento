package com.example.fithealth.Model.Firebase

sealed class FirebaseResult<out T> {
    object Loading : FirebaseResult<Nothing>()
    data class Success<out T>(val data: T) : FirebaseResult<T>()
    data class Failure(val exception: Exception) : FirebaseResult<Nothing>()
}