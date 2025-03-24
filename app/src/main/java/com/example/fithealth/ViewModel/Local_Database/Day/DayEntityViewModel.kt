package com.example.fithealth.ViewModel.Local_Database.Day

import DayEntity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.Routine
import com.example.fithealth.Model.Database.Local_Database.Repositorys.Days.DayEntityRespositoryImpl
import com.example.fithealth.Model.Database.Tables.Entitys.Meal
import kotlinx.coroutines.launch
import java.util.Date

class DayEntityViewModel(private val repository: DayEntityRespositoryImpl) : ViewModel() {

    private val _insertDayStatus = MutableLiveData<Boolean>()
    val insertDayStatus: LiveData<Boolean> get() = _insertDayStatus

    private val _updateDayRoutinesStatus = MutableLiveData<Boolean>()
    val updateDayRoutinesStatus: LiveData<Boolean> get() = _updateDayRoutinesStatus


    private fun insertDay(
        dayDate: Date,
        routineList: List<Routine> = emptyList(),
        mealList: List<Meal> = emptyList()
    ) {
        viewModelScope.launch {
                val day = DayEntity(
                    date = dayDate,
                    routines = routineList,
                    meals = mealList
                )
                _insertDayStatus.value = repository.insertDay(day)

        }

    }

    fun updateDayRoutines(dayDate : Date,routineList : List<Routine>){
        viewModelScope.launch {
            if(repository.existDay(dayDate)) _updateDayRoutinesStatus.value = repository.updateDayRoutines(dayDate,routineList)
            else insertDay(dayDate,routineList)
        }
    }

}