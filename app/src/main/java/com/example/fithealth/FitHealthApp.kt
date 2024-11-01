package com.example.fithealth

import android.app.Application
import com.google.firebase.FirebaseApp


class FitHealthApp : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this@FitHealthApp)
    }
}