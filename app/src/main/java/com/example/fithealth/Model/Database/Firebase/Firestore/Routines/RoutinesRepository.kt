package com.example.fithealth.Model.Database.Firebase.Firestore.Routines

import android.util.Log
import com.example.fithealth.Model.DataClass.Routine
import com.example.fithealth.Model.Utils.ExtensionUtils.FIRESTORE_USERS_COLLECTION
import com.example.fithealth.Model.Utils.ExtensionUtils.FIRESTORE_USER_ROUTINES_COLLECTION
import com.example.fithealth.userId
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RoutinesRepository(private val fs: FirebaseFirestore) : RoutinesQuery {

    companion object {
        private const val ERROR = "routineFirestoreError"
    }

    override suspend fun insertRoutines(routine: Routine): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                insertRoutineInUserCollection(routine)
                true
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun getAllRoutines(): List<Routine?> {
        return withContext(Dispatchers.IO){
            try {
                getUserRoutines()
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                emptyList()
            }
        }
    }

    private suspend fun getUserRoutines(): List<Routine?> =
        getRoutineCollection().
            get()
            .await()
            .documents
            .mapNotNull { it.toObject(Routine::class.java) }


    private suspend fun insertRoutineInUserCollection(routine: Routine) {
        getRoutineCollection()
            .document(routine.routineId)
            .set(routine)
            .await()
    }

    private fun getRoutineCollection(): CollectionReference {
        return userId?.let { currentUserId ->
            getUserCollection()
                .document(currentUserId)
                .collection(FIRESTORE_USER_ROUTINES_COLLECTION)
        } ?: throw IllegalStateException("User ID is null")
    }

    private fun getUserCollection(): CollectionReference = fs.collection(FIRESTORE_USERS_COLLECTION)

}