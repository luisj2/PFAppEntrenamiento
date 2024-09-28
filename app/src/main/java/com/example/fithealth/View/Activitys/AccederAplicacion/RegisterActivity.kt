package com.example.fithealth.Activitys.AccederAplicacion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fithealth.Model.Firebase.FirebaseHelper
import com.example.fithealth.Model.DataClass.AuthUser
import com.example.fithealth.Model.Permissions.Permisos
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.View.Activitys.AccederAplicacion.LoginActivity
import com.example.fithealth.databinding.ActivityRegistroBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseHelper: FirebaseHelper

    private lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseHelper = FirebaseHelper(this)
        setupUI()

    }

    private fun setupUI() {
        btnRegisterOnClick()
    }

    private fun btnRegisterOnClick() {
        binding.btnRegistro.setOnClickListener {

            if (Permisos.conexionEstable(applicationContext)) {
                binding.apply {
                    val newName = editTxtNuevoNombreUsuario.text.toString().trim()
                    val newPassword = editNuevoTxtContrasenia.text.toString().trim()
                    val email = editTxtNuevoCorreo.text.toString().trim()

                    val authUser = AuthUser(newName, newPassword, email)

                    if (isValidUser(authUser)) {
                        registerUserInFirebaseAuth(authUser)
                    }
                }
            } else {
                toast("Comprueba tu conexion")
            }

        }
    }

    private fun goToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun isValidUser(authUser: AuthUser): Boolean {

        if (isTextFieldsEmpty(authUser)) {
            toast("Rellena todos los campos")
            return false
        }
        if (firebaseHelper.isValidUserCredentials(authUser.userName)) {
            toast("Nombre de usuario invalido")
            return false
        }

        if (firebaseHelper.isValidEmailCredentials(authUser.email)) {
            toast("Correo invalido")
            return false
        }

        return true
    }

    private fun registerUserInFirebaseAuth(authUser: AuthUser) {
        firebaseHelper.isUserExist(authUser.email) { exist ->
            if (exist) {
                toast("El usuario ya existe")
            } else {
                registerUser(authUser)
            }
        }
    }

    private fun registerUser(authUser: AuthUser) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                firebaseHelper.registerUserFirebaseAuth(authUser)
                firebaseHelper.registerFirestoreUser(authUser)
                firebaseHelper.registerUserInRealtimeDatabase()
            }
            goToLoginActivity()
        }

    }

    private fun isTextFieldsEmpty(authUser: AuthUser): Boolean {
        return authUser.userName.isEmpty() && authUser.password.isEmpty() && authUser.email.isEmpty()
    }
}
