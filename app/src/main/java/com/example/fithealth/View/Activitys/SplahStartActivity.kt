package com.example.fithealth.View.Activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fithealth.Model.Utils.ExtensionUtils.moveToActivity
import com.example.fithealth.View.Activitys.AccederAplicacion.LoginActivity
import com.google.firebase.auth.FirebaseAuth
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