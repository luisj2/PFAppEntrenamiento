package com.example.fithealth.Model.Database.Local_Database.Repositorys.Days

import DayEntity
import android.util.Log
import com.example.fithealth.Model.DataClass.Routine
import com.example.fithealth.Model.Database.Local_Database.Dao.DayDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DayEntityRespositoryImpl(private val dayDao: DayDao) : DayEntityRespository {

    companion object {
        private const val ERROR = "DayEntityError"
    }

    override suspend fun insertDay(dayEntity: DayEntity): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                dayDao.insertDay(dayEntity) > 0L
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    override suspend fun updateDayRoutines(dayDate: Date, routineList: List<Routine>): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val dayString = convertDateToString(dayDate)
                val dayEntityUpdated = dayDao.getDayById(dayString)
                dayEntityUpdated?.routines = routineList

                dayEntityUpdated != null
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }

    }

    override suspend fun existDay(dayDate: Date): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val dayString = convertDateToString(dayDate)
                dayDao.getDayById(dayString) != null
            } catch (e: Exception) {
                Log.e(ERROR, "Error: ${e.message}", e)
                false
            }
        }
    }

    private fun convertDateToString(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return dateFormat.format(date)
    }


}