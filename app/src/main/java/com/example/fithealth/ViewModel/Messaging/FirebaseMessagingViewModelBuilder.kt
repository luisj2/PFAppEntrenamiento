package com.example.fithealth.ViewModel.Messaging

import com.example.fithealth.Model.Firebase.CloudMessaging.MessagingRepositoryImpl
import com.google.firebase.messaging.FirebaseMessaging

object FirebaseMessagingViewModelBuilder {

    fun getFirebaseMessageViewModel(): FirebaseMessagingViewModelFactory =
        FirebaseMessagingViewModelFactory(MessagingRepositoryImpl(FirebaseMessaging.getInstance()))

}