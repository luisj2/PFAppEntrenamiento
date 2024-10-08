package com.example.fithealth.Activitys.AccederAplicacion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fithealth.Model.Firebase.FirebaseHelper
import com.example.fithealth.Model.DataClass.AuthUser
import com.example.fithealth.Model.Firebase.FirebaseResult
import com.example.fithealth.Model.Permissions.Permisos
import com.example.fithealth.Model.Utils.ExtensionUtils.dialog
import com.example.fithealth.Model.Utils.ExtensionUtils.dissmissLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.isStableConnection
import com.example.fithealth.Model.Utils.ExtensionUtils.isValidEmailCredentials
import com.example.fithealth.Model.Utils.ExtensionUtils.moveToActivity
import com.example.fithealth.Model.Utils.ExtensionUtils.showLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.View.Activitys.AccederAplicacion.LoginActivity
import com.example.fithealth.ViewModel.Auth.AuthViewModel
import com.example.fithealth.ViewModel.Auth.AuthViewModelBuilder
import com.example.fithealth.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    //private lateinit var firebaseHelper: FirebaseHelper

    private lateinit var binding: ActivityRegisterBinding

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelBuilder.getAuthViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebaseHelper = FirebaseHelper(this)
        setupUI()
    }

    private fun setupUI() {
        setupOnclicks()
        setupObservers()
    }

    private fun setupOnclicks() {
        binding.apply {
            btnBack.setOnClickListener {
                moveToActivity(LoginActivity::class.java)
            }

            btnRegister.setOnClickListener {
                btnRegisterOnClick()
            }
        }
    }

    private fun setupObservers() {
        authViewModel.apply {
            registerErrorMessage.observe(this@RegisterActivity) { error -> toast(error) }

            registerUserState.observe(this@RegisterActivity) { registerSuccess ->
                if (registerSuccess) registrationSuccess()
                else toast("Ha habido un error al registrar el usuario o el usuario ya existe")
            }

            isLoading.observe(this@RegisterActivity) { isLoading ->
                if (isLoading) showLoadingScreen() else dissmissLoadingScreen()
            }
        }
    }

    private fun registrationSuccess() {
        toast("Usuario registrado correctamente")
        moveToActivity(LoginActivity::class.java)
    }

    private fun btnRegisterOnClick() {
        if (!isStableConnection()) {
            toast("Comprueba tu conexion")
        } else {
            binding.apply {
                val newName = etUsername.text.toString().trim()
                val newPassword = etPassword.text.toString().trim()
                val email = etEmail.text.toString().trim()

                val authUser = AuthUser(newName, newPassword, email)

                if (isValidUser(authUser)) authViewModel.registerUser(authUser)
            }
        }
    }


    private fun isValidUser(authUser: AuthUser): Boolean {
        return when {
            isTextFieldsEmpty(authUser) -> {
                toast("Rellena todos los campos")
                false
            }

            !isValidUserCredentials(authUser.userName) -> {
                toast("Nombre de usuario invalido")
                false
            }

            !isValidEmailCredentials(authUser.email) -> {
                toast("Correo invalido")
                false
            }

            else -> true
        }
    }

    /*
    private suspend fun registerUserInRealtimeDatabase() {
        withContext(Dispatchers.IO) {
            firebaseHelper.registerUserInRealtimeDatabase()
        }
    }

     */

    private fun isTextFieldsEmpty(authUser: AuthUser): Boolean =
        authUser.userName.isEmpty() && authUser.password.isEmpty() && authUser.email.isEmpty()


    private fun isValidUserCredentials(nombreUsuario: String): Boolean {
        val usernameRegex = "^[a-zA-Z0-9_]+$"
        val pattern = Pattern.compile(usernameRegex)
        val matcher = pattern.matcher(nombreUsuario)
        return matcher.matches()
    }


}

