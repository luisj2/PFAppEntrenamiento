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
import androidx.lifecycle.lifecycleScope
import com.example.fithealth.Activitys.AccederAplicacion.RegisterActivity
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_USER_NODE
import com.example.fithealth.Model.Utils.ExtensionUtils.moveToActivity
import com.example.fithealth.R
import com.example.fithealth.View.Activitys.AccederAplicacion.LoginActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class SplahStartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {

            val nextActivity =
                if (FirebaseAuth.getInstance().currentUser != null) MainActivity::class.java else LoginActivity::class.java

            moveToActivity(nextActivity)
            finish()
        }


    }
}