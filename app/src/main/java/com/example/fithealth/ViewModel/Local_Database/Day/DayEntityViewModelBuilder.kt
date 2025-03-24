package com.example.fithealth.ViewModel.Local_Database.Day

import android.content.Context
import com.example.fithealth.Model.Database.DatabaseBuilder
import com.example.fithealth.Model.Database.Local_Database.Repositorys.Days.DayEntityRespositoryImpl

object DayEntityViewModelBuilder {
    fun getDayEntityViewModelFactory(context : Context): DayEntityViewModelFactory {
        val database = DatabaseBuilder.provideDatabase(context)
        val dayDao = database.getDayDao()
        val repository = DayEntityRespositoryImpl(dayDao)
        return DayEntityViewModelFactory(repository)
    }
}