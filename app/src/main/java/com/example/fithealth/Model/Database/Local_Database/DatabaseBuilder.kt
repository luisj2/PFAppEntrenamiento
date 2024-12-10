package com.example.fithealth.Model.Database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private const val DATABASE_NAME = "FitHealthDatabase"

    fun provideDatabase(context: Context): FitHealthDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            FitHealthDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
}