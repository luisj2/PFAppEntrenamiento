package com.example.fithealth.Model.Services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.fithealth.Model.Database.DatabaseBuilder
import com.example.fithealth.Model.Database.FitHealthDatabase
import com.example.fithealth.Model.Database.Repositorys.Contacts.UserDatabaseRepositoryImpl
import com.example.fithealth.Model.Firebase.Realtime.UserRealtimeRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagesListenerService(private val context: Context) : Service() {

    private lateinit var database: FitHealthDatabase
    private lateinit var realtimeRepository: UserRealtimeRepository
    private lateinit var databaseRepository: UserDatabaseRepositoryImpl

    private val realtimeInstance =
        FirebaseDatabase.getInstance("https://fithealthpf-default-rtdb.europe-west1.firebasedatabase.app/")

    override fun onCreate() {
        super.onCreate()
        database = DatabaseBuilder.provideDatabase(context)
        realtimeRepository =
            UserRealtimeRepository(realtimeInstance)
        databaseRepository = UserDatabaseRepositoryImpl(
            database.getContactsDao(),
            database.getUserDao(),
            database.getMessageDao()
        )
        CoroutineScope(Dispatchers.IO).launch {
            val message = realtimeRepository.listenForMessages()
            databaseRepository.insertMessageToContact(message)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}