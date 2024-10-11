package com.example.fithealth.Model.Firebase.Firestore.User

import android.util.Log
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.Model.Utils.ExtensionUtils.FIRESTORE_USERS_COLLECTION
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_ID_FIELD
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_UNIQUENAME_FIELD
import com.example.fithealth.Model.Utils.ExtensionUtils.USER_USERNAME_FIELD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserFirestoreRepository(private val fs: FirebaseFirestore) : UserFirestoreQuery {

    override suspend fun getUserByName(name: String): List<SearchUser> {
        return withContext(Dispatchers.IO) {
            try {
                val documentList = fs.collection(FIRESTORE_USERS_COLLECTION)
                    .whereGreaterThanOrEqualTo(USER_UNIQUENAME_FIELD, name)
                    .whereLessThan(USER_UNIQUENAME_FIELD, name + "\uf8ff") // Esto asegura que se busque por prefijo
                    .whereNotEqualTo(USER_ID_FIELD,FirebaseAuth.getInstance().uid)
                    .get()
                    .await()


                if (documentList.isEmpty) emptyList<SearchUser>()
                else {
                    documentList.documents.mapNotNull { document ->
                        val id = document.getString(USER_ID_FIELD) ?: ""
                        val uniqueName = document.getString(USER_UNIQUENAME_FIELD) ?: ""
                        val userName = document.getString(USER_USERNAME_FIELD) ?: ""

                        SearchUser(id,userName, uniqueName, 0)

                    }
                }
            } catch (e: Exception) {
                Log.e("FirestoreError", "Error: ${e.message}", e)
                emptyList()
            }
        }
    }
}