package com.example.fithealth.Model.Firebase.Realtime

import android.util.Log
import com.example.fithealth.Model.DataClass.Contacto
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_FRIEND_NODE
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_MESSAGE_NODE
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_REQUEST_NODE
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_USER_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRealtimeRepository(private val realtime: FirebaseDatabase) : UserRealtimeQuery {
    override suspend fun sendRequest(user: SearchUser): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val reference = getCurrentUserReference().child(REALTIME_FRIEND_NODE).child(user.id)

                reference.setValue(user).await()

                true

            } catch (e: Exception) {
                Log.e("realtime_error", "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun addFriendToLoggedUser(user: SearchUser): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val reference = getCurrentUserReference().child(REALTIME_FRIEND_NODE).child(user.id)
                reference.setValue(user).await()
                true
            } catch (e: Exception) {
                Log.e("realtime_error", "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun addLoggedInUserToFriend(
        friendId: String,
        loggedUser: SearchUser
    ): Boolean {
        return withContext(Dispatchers.IO) {

            try {
                val reference = getUserRootReference().child(friendId).child(REALTIME_FRIEND_NODE)
                    .child(loggedUser.id)
                reference.setValue(loggedUser).await()
                true
            } catch (e: Exception) {
                Log.e("TAG", "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun getUserRequests(): List<SearchUser> {
        return withContext(Dispatchers.IO) {
            try {
                val reference = getCurrentUserReference().child(REALTIME_REQUEST_NODE)

                val snapshot = reference.get().await()

                if (!snapshot.exists()) emptyList()
                else {
                    snapshot.children.mapNotNull {
                        it.getValue(SearchUser::class.java)
                    }
                }
            } catch (e: Exception) {
                Log.e("realtime_error", "Error: ${e.message}", e)
                emptyList()
            }
        }
    }

    override suspend fun isPendingRequestFrom(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val reference = getCurrentUserReference().child(REALTIME_FRIEND_NODE).child(id)

                val snapshot = reference.get().await()

                snapshot.exists()
            } catch (e: Exception) {
                Log.e("realtime_error", "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun removeUserRequest(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val reference = getCurrentUserReference().child(REALTIME_REQUEST_NODE).child(id)
                val snapshot = reference.get().await()

                if (snapshot.exists()) {
                    reference.removeValue().await()
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                Log.e("realtime_error", "Error: ${e.message}", e)
                false
            }
        }
    }


    private fun getCurrentUserReference(): DatabaseReference {
        return realtime.getReference(REALTIME_USER_NODE)
            .child("contacto1")
    }

    private fun getUserRootReference(): DatabaseReference {
        return realtime.getReference(REALTIME_USER_NODE)
    }


}