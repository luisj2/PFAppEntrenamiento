package com.example.fithealth.Model.Firebase.Realtime

import android.util.Log
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_FRIEND_NODE
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_MESSAGE_NODE
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_USER_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class UserRealtimeRepository(private val realtime: FirebaseDatabase) : UserRealtimeQuery {
    override suspend fun sendRequest(user: SearchUser): Boolean {
        return try {
            val reference = realtime.getReference(REALTIME_USER_NODE)
                .child(FirebaseAuth.getInstance().uid.toString())
                .child(REALTIME_FRIEND_NODE).child(user.id)

            reference.setValue(user).await()

            true

        } catch (e: Exception) {
            Log.e("realtime_error", "Error: ${e.message}", e)
            false
        }
    }

}