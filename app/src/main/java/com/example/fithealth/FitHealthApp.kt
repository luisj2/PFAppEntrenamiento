package com.example.fithealth

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.fithealth.Model.DataClass.SearchUser
import com.google.firebase.FirebaseApp


class FitHealthApp : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this@FitHealthApp)
    }
}