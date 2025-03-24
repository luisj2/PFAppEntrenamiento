package com.example.fithealth.Model.Database.Local_Database.Repositorys.Days

import DayEntity
import com.example.fithealth.Model.DataClass.Routine
import java.util.Date

interface DayEntityRespository {

    suspend fun insertDay (dayEntity : DayEntity) : Boolean

    suspend fun updateDayRoutines(dayDate : Date, routineList : List<Routine>) : Boolean

    suspend fun existDay (dayDate : Date) : Boolean
}