package com.example.fithealth.Model.Firebase.Auth

import android.util.Log
import com.example.fithealth.Model.DataClass.AuthUser
import com.example.fithealth.Model.DataClass.FirestoreUser
import com.example.fithealth.Model.Utils.ExtensionUtils.FIRESTORE_USERS_COLLECTION
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthWebException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.math.log

class AuthRepository(private val auth: FirebaseAuth, private val fs: FirebaseFirestore) :
    LoginService, RegisterService {


    override suspend fun registerWithEmailAndPassword(email: String, password: String): Boolean =
        withContext(Dispatchers.IO) {
            auth.createUserWithEmailAndPassword(email, password).await().user != null
        }

    override suspend fun registerUserInFirestore(authUser: AuthUser): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val firestoreUser = createFirestoreUser(authUser)
                val userInfo = buildUserHashMap(firestoreUser)
                val userId = firestoreUser.userId

                fs.collection(FIRESTORE_USERS_COLLECTION).document(userId).set(userInfo).await()
                true
            } catch (e: Exception) {
                Log.e(
                    "error_firestore",
                    "El error es: ${e.message}"
                )
                false
            }
        }
    }


    private fun createFirestoreUser(authUser: AuthUser): FirestoreUser {
        val uid = auth.currentUser!!.uid
        val identifyHastag = uid.substring(0, 6)
        val userName = authUser.userName
        return FirestoreUser(
            userId = uid,
            email = authUser.email,
            password = authUser.password,
            identifyHastag = identifyHastag,
            userName = userName,
            uniqueName = "$userName # $identifyHastag"
        )
    }

    private fun buildUserHashMap(user: FirestoreUser): HashMap<String, Any> {
        return hashMapOf(
            "id" to user.userId,
            "email" to user.email,
            "password" to user.password,
            "userName" to user.userName,
            "identifyHashtag" to user.identifyHastag,
            "uniqueName" to user.uniqueName
        )
    }


    override suspend fun logInWithEmailAndPassword(email: String, password: String): Boolean =
        withContext(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password).await().user != null
        }


    suspend fun isUserExist(email: String): Boolean {
        return withContext(Dispatchers.IO) {

            try {
                val reference =
                    FirebaseFirestore.getInstance().collection(FIRESTORE_USERS_COLLECTION)
                val snapshot = reference
                    .whereEqualTo("email", email)
                    .get()
                    .await()

                !snapshot.isEmpty
            } catch (e: Exception) {
                false
            }
        }
    }

}