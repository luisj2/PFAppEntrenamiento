package com.example.fithealth.ViewModel.Auth.Realtime.User

import com.example.fithealth.Model.Firebase.Realtime.UserRealtimeRepository
import com.google.firebase.database.FirebaseDatabase

object UserRealtimeViewModelBuilder {

    fun getUserRealtimeViewModel(): UserRealtimeViewModelFactory =
        UserRealtimeViewModelFactory(UserRealtimeRepository(FirebaseDatabase.getInstance("https://fithealthpf-default-rtdb.europe-west1.firebasedatabase.app/")))
}