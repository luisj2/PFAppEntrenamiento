package com.example.fithealth.Model.Database.Firebase.Firestore.Exercise

import com.example.fithealth.Model.DataClass.Exercise

interface ExerciseQuery {

    //INSERT
    suspend fun insertExercise(exercise : Exercise) : Boolean

    //GET

    suspend fun getAllUserExercises() : List<Exercise?>
    suspend fun getExerciseByName(exerciseName : String) : List<Exercise?>

    suspend fun getExerciseContainsName (exerciseName : String) : List<Exercise?>

    //UPDATE

    //QUERYS

    suspend fun isExerciseExist(exercise : Exercise) : Boolean


    //DELETE 

}