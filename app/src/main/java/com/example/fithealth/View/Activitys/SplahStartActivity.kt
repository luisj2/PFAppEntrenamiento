package com.example.fithealth.View.Activitys

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.preferencesDataStore
import com.example.fithealth.Activitys.AccederAplicacion.RegisterActivity
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_SESSION_PREFERENCES_NAME
import com.example.fithealth.Model.Utils.ExtensionUtils.moveToActivity
import com.example.fithealth.R
import com.example.fithealth.View.Activitys.AccederAplicacion.LoginActivity
import com.google.firebase.FirebaseApp

class SplahStartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moveToActivity(LoginActivity::class.java)
        /*
        Handler(Looper.getMainLooper()).postDelayed({

           finish() // Finaliza la actividad Splash
        }, 1000)

         */
    }
}