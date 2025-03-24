package com.example.fithealth.Model.Firebase.Realtime

import android.util.Log
import com.example.fithealth.Model.DataClass.UserSearch
import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.Model.Database.Tables.Entitys.Message
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_FRIEND_NODE
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_MESSAGE_NODE
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_REQUEST_NODE
import com.example.fithealth.Model.Utils.ExtensionUtils.REALTIME_USER_NODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.ZoneOffset
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UserRealtimeRepository(private val realtime: FirebaseDatabase) : UserRealtimeQuery {

    private val ERROR = "realtime_error"
    override suspend fun sendRequest(user: UserSearch): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val reference =
                    getCurrentUserReference().child(REALTIME_REQUEST_NODE).child(user.contactId)

                reference.setValue(user).await()

                true

            } catch (e: Exception) {
                Log.e("realtime_error", "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun addFriendToLoggedUser(user: UserSearch): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val reference =
                    getCurrentUserReference().child(REALTIME_FRIEND_NODE).child(user.contactId)
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
        loggedUser: UserSearch
    ): Boolean {
        return withContext(Dispatchers.IO) {

            try {
                val reference = getUserRootReference().child(friendId).child(REALTIME_FRIEND_NODE)
                    .child(loggedUser.contactId)
                reference.setValue(loggedUser).await()
                true
            } catch (e: Exception) {
                Log.e("TAG", "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun isSendMessageByContactId(message: Message): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val reference = getUserRootReference()
                    .child(message.contactOwnerId)
                    .child(REALTIME_MESSAGE_NODE)
                    .child(
                        getCurrentLoggedUserId() + message.messageDate.toInstant(ZoneOffset.UTC)
                            .toEpochMilli().toString()
                    )

                reference.setValue(message).await()
                true
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun getUserRequests(): List<UserSearch> {
        return withContext(Dispatchers.IO) {
            try {
                val reference = getCurrentUserReference().child(REALTIME_REQUEST_NODE)

                val snapshot = reference.get().await()

                if (!snapshot.exists()) emptyList()
                else {
                    snapshot.children.mapNotNull {
                        it.getValue(UserSearch::class.java)
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

    override suspend fun killMessageListener(): Boolean {
        return withContext(Dispatchers.IO) {
           false
        }
    }

    override suspend fun getAllUserContacts(): List<Contact> {
        return withContext(Dispatchers.IO) {
            try {
                val reference = getCurrentUserReference().child(REALTIME_FRIEND_NODE)
                val snapshot = reference.get().await()

                if (!snapshot.exists()) emptyList()
                else {
                    snapshot.children.mapNotNull {
                        it.getValue(Contact::class.java)
                    }
                }
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                emptyList()
            }
        }
    }

    override suspend fun getContactById(id: String): Contact? {
        return withContext(Dispatchers.IO) {
            try {
                val reference = getCurrentUserReference().child(REALTIME_FRIEND_NODE).child(id)

                val snapshot = reference.get().await()

                if (snapshot.exists()) {
                    snapshot.getValue(Contact::class.java)
                } else null
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                null
            }
        }
    }

    override suspend fun getMessageSent(idOwner: String): Message? {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot =
                    getCurrentUserReference().child(REALTIME_MESSAGE_NODE).child(idOwner).get()
                        .await()
                snapshot.getValue(Message::class.java)
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                null
            }
        }
    }

    override suspend fun listenForMessages(): Message {
        return suspendCancellableCoroutine { continuation ->
            val reference = getCurrentUserReference().child(REALTIME_MESSAGE_NODE)

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            if (snapshot.exists()) {
                                val lastMessage =
                                    snapshot.children.lastOrNull()?.getValue(Message::class.java)
                                lastMessage?.let {
                                    continuation.resume(it)
                                } ?: run {
                                    continuation.resumeWithException(Exception("No hay mensajes disponibles"))
                                }
                            } else {
                                continuation.resumeWithException(Exception("No hay mensajes disponibles"))
                            }
                        } catch (e: Exception) {
                            Log.e(ERROR, "Error: ${e.message}", e)
                            continuation.resumeWithException(e)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(ERROR, "Error al escuchar mensajes: ${error.message}")
                    continuation.resumeWithException(Exception("Error al escuchar mensajes: ${error.message}"))
                }
            }

            reference.addValueEventListener(listener)

            continuation.invokeOnCancellation {
                reference.removeEventListener(listener)
            }
        }
    }


    private fun getCurrentUserReference(): DatabaseReference {
        return realtime.getReference(REALTIME_USER_NODE)
            .child("ABC1")
    }

    private fun getUserRootReference(): DatabaseReference {
        return realtime.getReference(REALTIME_USER_NODE)
    }

    private fun getCurrentLoggedUserId(): String = FirebaseAuth.getInstance().currentUser!!.uid


}