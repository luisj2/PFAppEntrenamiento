package com.example.fithealth.Model.Firebase.User

import android.util.Log
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.Model.Utils.ExtensionUtils.FIRESTORE_USERS_COLLECTION
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_UNIQUENAME_FIELD
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_USERNAME_FIELD
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository(private val fs: FirebaseFirestore) : UserQuery {

    override suspend fun getUserByName(name: String): List<SearchUser> {
        return withContext(Dispatchers.IO) {
            try {
                val documentList = fs.collection(FIRESTORE_USERS_COLLECTION)
                    .whereGreaterThanOrEqualTo(USER_UNIQUENAME_FIELD, name)
                    .whereLessThan(USER_UNIQUENAME_FIELD, name + "\uf8ff") // Esto asegura que se busque por prefijo
                    .get()
                    .await()


                if (documentList.isEmpty) emptyList<SearchUser>()
                else {
                    documentList.documents.mapNotNull { document ->
                        val uniqueName = document.getString(USER_UNIQUENAME_FIELD) ?: ""
                        val userName = document.getString(USER_USERNAME_FIELD) ?: ""

                        SearchUser(userName, uniqueName, 0)

                    }
                }
            } catch (e: Exception) {
                Log.e("FirestoreError", "Error: ${e.message}", e)
                emptyList()
            }
        }
    }
}