package com.example.fithealth.ViewModel.Auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.AuthUser
import com.example.fithealth.Model.Firebase.Auth.AuthRepository
import com.example.fithealth.Model.Firebase.Auth.LoginService
import com.example.fithealth.Model.Firebase.Auth.RegisterService
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthWebException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _registerUserState = MutableLiveData<Boolean>()
    val registerUserState: LiveData<Boolean> get() = _registerUserState

    private val _logInUserState = MutableLiveData<Boolean>()
    val logInUserState: LiveData<Boolean> get() = _logInUserState


    private val _registerErrorMessage = MutableLiveData<String>()
    val registerErrorMessage: LiveData<String> get() = _registerErrorMessage

    private val _logInErrorMessage = MutableLiveData<String>()
    val logInErrorMessage: LiveData<String> get() = _logInErrorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    fun registerUser(authUser: AuthUser) {
        viewModelScope.launch {
            try {
                Log.i(
                    "verificacion_usuario",
                    "email: ${authUser.email} y contraseña: ${authUser.password}"
                )
                changeLoadingTo(true)
                val userExist = repository.isUserExist(authUser.email)
                if (!userExist) {

                    val registrationEmailPasswordSuccess =
                        repository.registerWithEmailAndPassword(authUser.email, authUser.password)
                    val registrationFirestoreSucess = repository.registerUserInFirestore(authUser)

                    _registerUserState.value =
                        registrationEmailPasswordSuccess &&
                                registrationFirestoreSucess
                } else {
                    _registerUserState.value = false
                }

            } catch (e: Exception) {
                _registerErrorMessage.value = manageRegistrationError(e)
            } finally {
                changeLoadingTo(false)
            }
        }
    }

    private fun changeLoadingTo(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun logInUserWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            try {
                changeLoadingTo(true)

                _logInUserState.value =
                    repository.isUserExist(email) && repository.logInWithEmailAndPassword(
                        email,
                        password
                    )

            } catch (e: Exception) {
                _logInErrorMessage.value = manageLogInError(e)
            } finally {
                changeLoadingTo(false)
            }
        }
    }



    private fun manageRegistrationError(error: Exception): String {
        return when (error) {
            is FirebaseAuthWeakPasswordException -> "Contraseña débil o no válida"
            is FirebaseAuthWebException -> "Error en la red"
            is FirebaseAuthUserCollisionException -> "El correo electrónico ya está registrado"
            is FirebaseAuthInvalidCredentialsException -> "Comprueba que el correo y la contraseña sean correctas"
            else -> {
                Log.e("ErrorFirebase", "Error inesperado: $error")
                "Error inesperado"
            }
        }
    }

    private fun manageLogInError(error: Exception): String {
        return when (error) {
            is FirebaseAuthWeakPasswordException -> "Contraseña débil o no válida"
            is FirebaseAuthWebException -> "Error en la red"
            is FirebaseAuthInvalidUserException -> "El correo electrónico no está registrado"
            is FirebaseAuthInvalidCredentialsException -> "Contraseña incorrecta"
            is FirebaseTooManyRequestsException -> "Demasiados intentos, inténtalo más tarde"
            is FirebaseFirestoreException -> "Error al iniciar sesión"
            else -> {
                Log.e("ErrorFirebase", "Error inesperado: $error")
                "Error inesperado"
            }
        }
    }


}