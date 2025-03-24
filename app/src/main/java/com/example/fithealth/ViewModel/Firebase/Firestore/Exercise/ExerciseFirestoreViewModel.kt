package com.example.fithealth.ViewModel.Firebase.Firestore.Exercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.Model.Database.Firebase.Firestore.Exercise.ExerciseFirestoreRespository
import kotlinx.coroutines.launch

class ExerciseFirestoreViewModel(private val repository: ExerciseFirestoreRespository) :
    ViewModel() {

    private val _insertExerciseStatus = MutableLiveData<Boolean>()
    val insertExerciseStatus: LiveData<Boolean> get() = _insertExerciseStatus

    private val _allExercises = MutableLiveData<List<Exercise?>>()
    val allExercises: LiveData<List<Exercise?>> get() = _allExercises

    private val _exerciseContainsName = MutableLiveData<List<Exercise?>>()
    val exerciseContainsName : LiveData<List<Exercise?>> get() = _exerciseContainsName



    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            changeLoadingTo(true)
            _insertExerciseStatus.value = repository.insertExercise(exercise)
            changeLoadingTo(false)
        }
    }

    fun getExerciseContainsName (exerciseName : String){
        viewModelScope.launch {
            changeLoadingTo(true)
            _exerciseContainsName.value = repository.getExerciseContainsName(exerciseName)
            changeLoadingTo(false)
        }
    }

    fun getAlluserExercises() {
        viewModelScope.launch {
            changeLoadingTo(true)
            _allExercises.value = repository.getAllUserExercises()
            changeLoadingTo(false)
        }
    }

    private fun changeLoadingTo(loadingStatus: Boolean) {
        _loading.value = loadingStatus
    }

}