package com.example.fithealth.Model.Utils

import android.app.AlertDialog
import android.content.Context

object DialogManager {
    private const val CERRAR = "Cerrar"
    fun showDialog(dialogContext: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(dialogContext).apply {
            setTitle(title)
            setMessage(message)
        }
        builder.setPositiveButton(CERRAR) { dialog, _ ->
            dialog.dismiss()
        }
    }
}