package com.example.fithealth.Model.Firebase.CloudMessaging

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MessagingRepositoryImpl(private val FMessaging: FirebaseMessaging) : MessagingRepository {

    private val ERROR = "message_error"
    override suspend fun getToken(): String? {
        return withContext(Dispatchers.IO) {
            try {
                FMessaging.token.await()
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                null
            }
        }
    }

    override suspend fun deleteToken(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                FMessaging.deleteToken().await()
                true
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }


}