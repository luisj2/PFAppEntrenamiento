package com.example.fithealth.Model.Services

import android.content.Context
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagesService(private val context: Context) : FirebaseMessagingService() {



    override fun onCreate() {
        super.onCreate()

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

    }

}