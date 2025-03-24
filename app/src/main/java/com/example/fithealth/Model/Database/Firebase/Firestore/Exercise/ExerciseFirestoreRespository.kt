package com.example.fithealth.Model.Database.Firebase.Firestore.Exercise

import android.util.Log
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.Model.Utils.ExtensionUtils.EXERCISE_NAME_FIELD
import com.example.fithealth.Model.Utils.ExtensionUtils.EXERCISE_TYPE_FIELD
import com.example.fithealth.Model.Utils.ExtensionUtils.FIRESTORE_USERS_COLLECTION
import com.example.fithealth.Model.Utils.ExtensionUtils.FIRESTORE_USER_EXERCISE_COLLECTION
import com.example.fithealth.userId
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ExerciseFirestoreRespository(private val fs: FirebaseFirestore) : ExerciseQuery {

    companion object {
        private const val ERROR = "exerciseFirestoreError"
    }

    override suspend fun insertExercise(exercise: Exercise): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                if (!isExerciseExist(exercise)) insertExerciseInUserCollection(exercise)
                true
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun getAllUserExercises(): List<Exercise?> {
        return withContext(Dispatchers.IO) {
            try {
                getExercisesList()
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                emptyList()
            }
        }
    }

    private suspend fun getExercisesList(): List<Exercise?> {
        return getExerciseCollection()
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(Exercise::class.java) }
    }


    private suspend fun insertExerciseInUserCollection(exercise: Exercise) {
        getExerciseCollection()
            .document(exercise.exerciseId)
            .set(exercise)
            .await()

    }

    override suspend fun getExerciseByName(exerciseName: String): List<Exercise?> {
        return withContext(Dispatchers.IO) {
            try {
                val querySnapshot = getExerciseSnapshotByName(exerciseName)
                parseExerciseListFromQuerySnapshot(querySnapshot)
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                emptyList()
            }
        }
    }

    private fun parseExerciseListFromQuerySnapshot(
        querySnapshot: QuerySnapshot?,
    ): List<Exercise?> =
        querySnapshot?.documents?.mapNotNull { it.toObject(Exercise::class.java) } ?: emptyList()

    override suspend fun getExerciseContainsName(exerciseName: String): List<Exercise> {
        return withContext(Dispatchers.IO) {
            try {
                getExerciseListContainName(exerciseName)
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                emptyList()
            }
        }
    }

    private suspend fun getExerciseListContainName(exerciseName: String): List<Exercise> {
        val exercisesResults = mutableListOf<Exercise>()

        val queryAllExercises = getExerciseCollection().get().await()

        for (document in queryAllExercises.documents) {
            val exercise = document.toObject(Exercise::class.java)

            if (containsText(exercise?.exerciseName ?: "", exerciseName)) {
                if (exercise != null) exercisesResults.add(exercise)
            }
        }


        return exercisesResults
    }

    private fun containsText(source: String, target: String): Boolean =
        source?.contains(target, ignoreCase = true) == true

    private suspend fun getExerciseSnapshotByName(exerciseName: String): QuerySnapshot? {
        return getExerciseCollection()
            .whereEqualTo(EXERCISE_NAME_FIELD, exerciseName)
            .get()
            .await()
    }

    override suspend fun isExerciseExist(exercise: Exercise): Boolean {
        return withContext(Dispatchers.IO) {
            try {

                val querySnapshot =
                    getExerciseSnapshotByTypeAndName(exercise.exerciseType, exercise.exerciseName)

                querySnapshot?.isEmpty == false
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    private suspend fun getExerciseSnapshotByTypeAndName(
        exerciseType: String,
        exerciseName: String
    ): QuerySnapshot? {
        return getExerciseCollection()
            .whereEqualTo(EXERCISE_NAME_FIELD, exerciseName)
            .whereEqualTo(EXERCISE_TYPE_FIELD, exerciseType)
            .get()
            .await()
    }

    private fun getUsersCollection(): CollectionReference =
        fs.collection(FIRESTORE_USERS_COLLECTION)

    private fun getExerciseCollection(): CollectionReference {
        return userId?.let { currentUserId ->
            getUsersCollection()
                .document(currentUserId)
                .collection(FIRESTORE_USER_EXERCISE_COLLECTION)
        } ?: throw IllegalStateException("User ID is null")
    }
}