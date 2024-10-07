package com.example.fithealth.View.Activitys.AccederAplicacion


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.fithealth.Activitys.AccederAplicacion.RegisterActivity
import com.example.fithealth.Model.Firebase.FirebaseHelper
import com.example.fithealth.Model.Permissions.Permisos
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_SESSION_KEY
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_SESSION_PREFERENCES_NAME
import com.example.fithealth.Model.Utils.ExtensionUtils.checkUserSessionStatus
import com.example.fithealth.Model.Utils.ExtensionUtils.clearHelperText
import com.example.fithealth.Model.Utils.ExtensionUtils.dissmissLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.isStableConnection
import com.example.fithealth.Model.Utils.ExtensionUtils.isValidEmailCredentials
import com.example.fithealth.Model.Utils.ExtensionUtils.moveToActivity
import com.example.fithealth.Model.Utils.ExtensionUtils.saveUserSessionTo
import com.example.fithealth.Model.Utils.ExtensionUtils.setRequiredHelperText
import com.example.fithealth.Model.Utils.ExtensionUtils.showLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.R
import com.example.fithealth.View.Activitys.MainActivity
import com.example.fithealth.ViewModel.Auth.AuthViewModel
import com.example.fithealth.ViewModel.Auth.AuthViewModelBuilder
import com.example.fithealth.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelBuilder.getAuthViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            if (checkUserSessionStatus()) moveToActivity(MainActivity::class.java)
            setupUI()
        }
    }

    private fun setupUI() {
        setupOnClicks()
        setupObservers()
    }

    private fun setupObservers() {
        authViewModel.apply {
            logInErrorMessage.observe(this@LoginActivity) { error -> toast(error) }

            logInUserState.observe(this@LoginActivity) { logInSuccess ->
                if (logInSuccess) logIn()
                else toast("Error al iniciar sesion o el usuario no existe")

            }
            isLoading.observe(this@LoginActivity) { isLoading ->
                if (isLoading) showLoadingScreen() else dissmissLoadingScreen()
            }
        }
    }

    private fun logIn() {
        lifecycleScope.launch {
            saveUserSessionTo(true)
            moveToActivity(MainActivity::class.java)
        }
    }

    private fun setupOnClicks() {
        binding.apply {
            btnLogIn.setOnClickListener {
                logInOnClick()
            }

            tvAquiLink.setOnClickListener {
                moveToActivity(RegisterActivity::class.java)
            }
        }
    }

    private fun logInOnClick() {
        if (!isStableConnection()) {
            toast("Comprueba tu conexión")
        } else {
            val email: String = binding.etEmail.text.toString().trim()
            val password: String = binding.etPassword.text.toString().trim()

            if (isValidEmailAndPassword(email, password)) {
                authViewModel.logInUserWithEmailAndPassword(email, password)
                clearErrorFields()
            } else {
                showFieldValidationErrors(email, password)
            }

        }
    }

    private fun clearErrorFields() {
        binding.apply {
            tilEmail.clearHelperText()
            tilPassword.clearHelperText()
        }
    }

    private fun showFieldValidationErrors(email: String, password: String) {
        showEmailFieldsErrors(email)
        showPasswordFieldsErrors(password)
    }

    private fun showPasswordFieldsErrors(password: String) {
        binding.tilPassword.apply {
            when {
                password.isEmpty() -> setRequiredHelperText()
                password.length < 6 -> helperText = "Mínimo 6 caracteres de contraseña"
                else -> clearHelperText()
            }
        }
    }

    private fun showEmailFieldsErrors(email: String) {
        binding.tilEmail.apply {
            when {
                email.isEmpty() -> setRequiredHelperText()
                !isValidEmailCredentials(email) -> helperText = "Credenciales del correo inválidas"
                else -> clearHelperText()
            }
        }
    }

    private fun isValidEmailAndPassword(email: String, password: String): Boolean =
        email.isNotEmpty() && password.isNotEmpty() &&
                isValidEmailCredentials(email) && password.length >= 6

}