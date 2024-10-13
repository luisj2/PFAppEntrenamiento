package com.example.fithealth.Model.Utils.ExtensionUtils

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.fithealth.Model.Permissions.Permisos
import com.example.fithealth.Model.Utils.DialogManager
import com.example.fithealth.R
import com.example.fithealth.ViewModel.Auth.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

private var loadingDialog: Dialog? = null

fun Activity.toast(message: String, messageLength: Int = Toast.LENGTH_SHORT) {
    if (!isDestroyed) Toast.makeText(this, message, messageLength).show()
}

fun Activity.dialog(title: String, message: String) {
    DialogManager.showDialog(this, title, message)
}

fun Activity.showLoadingScreen() {

    if (loadingDialog == null || !loadingDialog!!.isShowing) {
        loadingDialog = Dialog(this).apply {
            // El fondo del diálogo será transparente
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // Asignamos el layout custom para mostrar como diálogo
            setContentView(R.layout.pantalla_carga)
            // El diálogo no puede ser cancelado tocando fuera o con el botón atrás
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            // Mostramos el diálogo
            show()
        }
    }

}

fun Activity.dissmissLoadingScreen() {
    loadingDialog?.let { dialog ->
        if (dialog.isShowing) {
            dialog.dismiss()
            loadingDialog = null
        }
    }
}

fun Activity.moveToActivity(targetActivity: Class<*>, bundle: Bundle = bundleOf()) {
    startActivity(Intent(this, targetActivity).putExtras(bundle))
}

fun Activity.isStableConnection(): Boolean {
    return Permisos.conexionEstable(this)
}


