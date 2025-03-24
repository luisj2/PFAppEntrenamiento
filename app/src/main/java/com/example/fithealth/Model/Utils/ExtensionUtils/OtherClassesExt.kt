package com.example.fithealth.Model.Utils.ExtensionUtils

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.example.fithealth.Activitys.AccederAplicacion.RegisterActivity
import com.example.fithealth.R
import com.example.fithealth.View.Activitys.AccederAplicacion.LoginActivity
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

fun RegisterActivity.isValidEmailCredentials(email: String): Boolean {
    val emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    val pattern = Pattern.compile(emailRegex)
    val matcher = pattern.matcher(email)
    return matcher.matches()
}

fun LoginActivity.isValidEmailCredentials(email: String): Boolean {
    val emailRegex =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    val pattern = Pattern.compile(emailRegex)
    val matcher = pattern.matcher(email)
    return matcher.matches()
}

fun TextInputLayout.setRequiredHelperText() {
    this.helperText = context.getString(R.string.requerido)
}

fun TextInputLayout.clearHelperText() {
    this.helperText = null
}

fun EditText.onTextChanged(action: (String) -> Unit) {
    this.doOnTextChanged { text, _, _, _ ->
        action(text?.toString().orEmpty())
    }
}