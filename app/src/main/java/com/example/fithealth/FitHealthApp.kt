package com.example.fithealth

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_SESSION_PREFERENCES_NAME
import com.google.firebase.FirebaseApp

val Context.dataStore by preferencesDataStore(name = USER_SESSION_PREFERENCES_NAME)

class FitHealthApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this@FitHealthApp)
    }
}