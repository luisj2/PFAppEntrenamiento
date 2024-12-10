package com.example.fithealth.Model.Firebase.Firestore.User

import android.util.Log
import com.example.fithealth.Model.DataClass.UserSearch
import com.example.fithealth.Model.Database.Tables.Entitys.User
import com.example.fithealth.Model.Utils.ExtensionUtils.FIRESTORE_USERS_COLLECTION
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_EMAIL_FIELD
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_ID_FIELD
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_PASSWORD_FIELD
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_TOKEN_FIELD
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_UNIQUENAME_FIELD
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_USERNAME_FIELD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserFirestoreRepository(private val fs: FirebaseFirestore) : UserFirestoreQuery {

    private val FIRESTORE_ERROR = "firestore_error"
    override suspend fun insertTokenByUserId(userId: String, token: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val reference = getUsersCollection().document(userId)
                reference.update(USER_TOKEN_FIELD, FieldValue.arrayUnion(token))
                true
            } catch (e: Exception) {
                Log.e(FIRESTORE_ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun getUsersByName(name: String): List<UserSearch> {
        return withContext(Dispatchers.IO) {
            try {
                val documentList = getUsersCollection()
                    .whereGreaterThanOrEqualTo(USER_UNIQUENAME_FIELD, name)
                    .whereLessThan(
                        USER_UNIQUENAME_FIELD,
                        name + "\uf8ff"
                    ) // Esto asegura que se busque por prefijo
                    .whereNotEqualTo(USER_ID_FIELD, FirebaseAuth.getInstance().uid)
                    .get()
                    .await()


                if (documentList.isEmpty) emptyList()
                else {
                    documentList.documents.mapNotNull { document ->
                        val id = document.getString(USER_ID_FIELD) ?: ""
                        val uniqueName = document.getString(USER_UNIQUENAME_FIELD) ?: ""
                        val userName = document.getString(USER_USERNAME_FIELD) ?: ""

                        UserSearch(id, userName, uniqueName, 0)

                    }
                }
            } catch (e: Exception) {
                Log.e("FirestoreError", "Error: ${e.message}", e)
                emptyList()
            }
        }
    }

    override suspend fun getUserSearchById(id: String): UserSearch? {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = getUsersCollection().whereEqualTo(USER_ID_FIELD, id).get().await()

                if (snapshot.documents.isNotEmpty()) {
                    val document = snapshot.documents[0]

                    val userId = document.getString(USER_ID_FIELD) ?: ""
                    val userName = document.getString(USER_USERNAME_FIELD) ?: ""
                    val uniqueName = document.getString(USER_UNIQUENAME_FIELD) ?: ""


                    UserSearch(userId, userName, uniqueName, 0)

                } else null

            } catch (e: Exception) {
                Log.e("firestore_error", "Error: ${e.message}", e)
                null
            }


        }
    }

    override suspend fun getUserById(id: String): User? {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = getUsersCollection().whereEqualTo(USER_ID_FIELD, id).get().await()

                if (snapshot.documents.isNotEmpty()) {
                    val document = snapshot.documents[0]

                    val userId = document.getString(USER_ID_FIELD) ?: ""
                    val userName = document.getString(USER_USERNAME_FIELD) ?: ""
                    val uniqueName = document.getString(USER_UNIQUENAME_FIELD) ?: ""
                    val email = document.getString(USER_EMAIL_FIELD) ?: ""
                    val password = document.getString(USER_PASSWORD_FIELD) ?: ""
                    val icon = 0

                    User(userId, userName, email, password, uniqueName, icon)

                } else null
            } catch (e: Exception) {
                Log.e(FIRESTORE_ERROR, "Error: ${e.message}", e)
                null
            }
        }
    }

    override suspend fun removeTokenByUserId(userId: String, token: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val reference = getUsersCollection().document(userId)

                reference.update(USER_TOKEN_FIELD, FieldValue.arrayRemove(token))
                true
            } catch (e: Exception) {
                Log.e(FIRESTORE_ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }


    private fun getUsersCollection(): CollectionReference =
        fs.collection(FIRESTORE_USERS_COLLECTION)

}