package com.example.fithealth.Model.Database.Firebase.Firestore.Routines

import com.example.fithealth.Model.DataClass.Routine

interface RoutinesQuery {

    //INSERT
    suspend fun insertRoutines (routine : Routine) : Boolean

    //GET
    suspend fun getAllRoutines() : List<Routine?>
}