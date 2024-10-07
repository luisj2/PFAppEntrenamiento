package com.example.fithealth.Model.Utils.ExtensionUtils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.fithealth.Model.Utils.DialogManager


fun Fragment.toast(message : String, messageLength : Int = Toast.LENGTH_SHORT){
    Toast.makeText(requireContext(), message, messageLength).show()
}

fun Fragment.dialog(title: String, message: String) {
    DialogManager.showDialog(requireContext(), title, message)
}

fun Fragment.moveToActivity(targetClass : Class<*>,bundle : Bundle = bundleOf()){
    startActivity(Intent(requireContext(),targetClass).putExtras(bundle))
}