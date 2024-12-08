package com.example.fithealth.View.Activitys.AccederAplicacion


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fithealth.Activitys.AccederAplicacion.RegisterActivity
import com.example.fithealth.Model.Utils.ExtensionUtils.clearHelperText
import com.example.fithealth.Model.Utils.ExtensionUtils.dissmissLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.isStableConnection
import com.example.fithealth.Model.Utils.ExtensionUtils.isValidEmailCredentials
import com.example.fithealth.Model.Utils.ExtensionUtils.moveToActivity
import com.example.fithealth.Model.Utils.ExtensionUtils.setRequiredHelperText
import com.example.fithealth.Model.Utils.ExtensionUtils.showLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.View.Activitys.MainActivity
import com.example.fithealth.ViewModel.Auth.AuthViewModel
import com.example.fithealth.ViewModel.Auth.AuthViewModelBuilder
import com.example.fithealth.ViewModel.Auth.Firestore.User.UserFirestoreViewModel
import com.example.fithealth.ViewModel.Auth.Firestore.User.UserFirestoreViewModelBuilder
import com.example.fithealth.ViewModel.Local_Database.User.UserDatabaseViewModel
import com.example.fithealth.ViewModel.Local_Database.User.UserDatabaseViewModelBuilder
import com.example.fithealth.ViewModel.Messaging.FirebaseMessagingViewModel
import com.example.fithealth.ViewModel.Messaging.FirebaseMessagingViewModelBuilder
import com.example.fithealth.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelBuilder.getAuthViewModelFactory()
    }

    private val userFirestoreViewModel: UserFirestoreViewModel by viewModels {
        UserFirestoreViewModelBuilder.getUserViewModelFactory()
    }


    private val userDatabaseViewModel: UserDatabaseViewModel by viewModels {
        UserDatabaseViewModelBuilder.getUserDatabaseViewModelFactory(this)
    }

    private val messagingViewModel: FirebaseMessagingViewModel by viewModels {
        FirebaseMessagingViewModelBuilder.getFirebaseMessageViewModel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        setupOnClicks()
        setupObservers()
    }

    private fun setupObservers() {
        authObservers()
        userFirestoreObservers()
        userLocalDatabaseObservers()
    }

    private fun userLocalDatabaseObservers() {

        userDatabaseViewModel.apply {
            inserUserStatus.observe(this@LoginActivity) { insertUserStatus ->
                if (insertUserStatus) Log.i("infoUserDatabase", "usuario insertado correctamente")
                else Log.i("infoUserDatabase", "usuario no se ha insertado")
            }
        }


    }

    private fun userFirestoreObservers() {

        userFirestoreViewModel.apply {
            completeLoggedUser.observe(this@LoginActivity) { user ->
                if (user != null) userDatabaseViewModel.inserUser(user)
            }
        }
    }

    private fun authObservers() {
        authViewModel.apply {
            logInErrorMessage.observe(this@LoginActivity) { error -> toast(error) }

            logInUserState.observe(this@LoginActivity) { logInSuccess ->
                if (logInSuccess) {
                    logIn()
                    registerUserInLocalDatabase()
                } else toast("Error al iniciar sesion o el usuario no existe")

            }
            isLoading.observe(this@LoginActivity) { isLoading ->
                if (isLoading) showLoadingScreen() else dissmissLoadingScreen()
            }
        }
    }

    private fun registerUserInLocalDatabase() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {
            userFirestoreViewModel.getCompleteLoggedUserById(uid)
        }
    }

    private fun logIn() {
        messagingViewModel.getToken { token ->
            if (token != null) userFirestoreViewModel.insertToken(token)
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