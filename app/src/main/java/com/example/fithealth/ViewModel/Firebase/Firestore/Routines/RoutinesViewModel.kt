package com.example.fithealth.ViewModel.Firebase.Firestore.Routines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.Routine
import com.example.fithealth.Model.Database.Firebase.Firestore.Routines.RoutinesRepository
import kotlinx.coroutines.launch

class RoutinesViewModel(private val repository: RoutinesRepository) : ViewModel() {

    private val _insertRoutineStatus = MutableLiveData<Boolean>()
    val insertRoutineStatus: LiveData<Boolean> get() = _insertRoutineStatus

    private val _allRoutines = MutableLiveData<List<Routine?>>()
    val allRoutine: LiveData<List<Routine?>> get() = _allRoutines

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun insertRoutine(routine: Routine) {
        viewModelScope.launch {
            changeLoadingTo(true)
            _insertRoutineStatus.value = repository.insertRoutines(routine)
            changeLoadingTo(false)
        }
    }

    fun getAllRoutines(){
        viewModelScope.launch {
            changeLoadingTo(true)
            _allRoutines.value = repository.getAllRoutines()
            changeLoadingTo(false)
        }
    }


    private fun changeLoadingTo(laodingStatus: Boolean) {
        _loading.value = laodingStatus
    }

}