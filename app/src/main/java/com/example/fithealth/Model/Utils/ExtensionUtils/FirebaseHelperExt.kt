package com.example.fithealth.Model.Utils.ExtensionUtils

import android.content.Context
import android.widget.Toast
import com.example.fithealth.Model.Firebase.FirebaseHelper
import com.example.fithealth.Model.Utils.DialogManager

fun FirebaseHelper.toast(message : String,dialogLong : Int = Toast.LENGTH_SHORT){
    Toast.makeText(this.context, message, dialogLong).show()
}

fun FirebaseHelper.dialog(title : String,message : String){
    DialogManager.showDialog(this.context,title,message)
}