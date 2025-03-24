package com.example.fithealth.View.Activitys.Training

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.Model.DataClass.Routine
import com.example.fithealth.Model.Utils.ExercisesSelected
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.Model.Utils.ExtensionUtils.toggleLoadingScreen
import com.example.fithealth.R
import com.example.fithealth.View.ReyclerAdapters.Training.ExerciseAdapter.CreateRoutineExerciseList.AddExeriseToRoutineAdapter
import com.example.fithealth.View.ReyclerAdapters.Training.ExerciseToRoutineAdapter.ExerciseToRoutineAdapter
import com.example.fithealth.View.SpinnerAdapters.SpinnerAdapterCustom
import com.example.fithealth.ViewModel.Firebase.Firestore.Routines.RoutinesViewModel
import com.example.fithealth.ViewModel.Firebase.Firestore.Routines.RoutinesViewModelBuilder
import com.example.fithealth.databinding.ActivityCreateRoutineBinding
import com.example.fithealth.exerciseTypes


class CreateRoutineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateRoutineBinding

    private val routinesViewModel: RoutinesViewModel by viewModels {
        RoutinesViewModelBuilder.getRoutinesViewModelFactory()
    }

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) updateExerciseRecycler(ExercisesSelected.exerciseSelectedList.toList())

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRoutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()

    }


    private fun setupUI() {
        setupExercisesRecycler()
        setupSpinners()
        setupOnClicks()
        setupRoutineObservers()
    }

    private fun setupExercisesRecycler() {
        binding.rvExercisesToRoutine.apply {
            layoutManager = LinearLayoutManager(this@CreateRoutineActivity)
            adapter = AddExeriseToRoutineAdapter(mutableListOf(),::removeExerciseInSelection,::setNoExercisesMessageVisibility)
        }
    }

    private fun removeExerciseInSelection(exercise : Exercise){
        ExercisesSelected.exerciseSelectedList.remove(exercise)
        val adapter = binding.rvExercisesToRoutine.adapter as? AddExeriseToRoutineAdapter
        adapter?.removeExercise(exercise)
    }

    private fun updateExerciseRecycler(exerciseSelectedList: List<Exercise>) {
        val adapter = binding.rvExercisesToRoutine.adapter as? AddExeriseToRoutineAdapter
        adapter?.updateList(exerciseSelectedList)
    }

    private fun setNoExercisesMessageVisibility(visible: Boolean) {
        binding.tvNoExercisesMessage.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun setupRoutineObservers() {
        routinesViewModel.apply {
            insertRoutineStatus.observe(this@CreateRoutineActivity) { insertStatus ->
                if (insertStatus) routineInserted()
                else routineNotInserted()
            }
            loading.observe(this@CreateRoutineActivity) { loadingStatus ->
                toggleLoadingScreen(loadingStatus)
            }
        }
    }

    private fun routineNotInserted() {
        toast("La Rutina no se ha inseretado correctamente")
    }

    private fun routineInserted() {
        toast("Rutina insertada correctamente")
        clearFields()
    }

    private fun clearFields() {
        binding.apply {
            etRoutineName.text.clear()
            clearExerciseRecycler()
            putSpinnerToDefaultSelection(spinnerRoutineDuration)
            putSpinnerToDefaultSelection(spinnerRoutineType)
        }
    }

    private fun putSpinnerToDefaultSelection(spinnerRoutineDuration: Spinner) {
        spinnerRoutineDuration.setSelection(0)
    }

    private fun clearExerciseRecycler() {
        val adapter = binding.rvExercisesToRoutine.adapter as? ExerciseToRoutineAdapter
        adapter?.updateList(emptyList())
    }

    private fun setupSpinners() {
        setupRoutineTypeSpinner()
        setupRoutineDurationSpinner()
    }

    private fun setupRoutineTypeSpinner() {
        val adapter = SpinnerAdapterCustom(this, getTypeExercisesOptions())
        binding.spinnerRoutineType.adapter = adapter
    }

    private fun setupRoutineDurationSpinner() {
        val spinnerItems = getTimeOptions()
        val spinnerAdapter = getBasicArrayAdapter(spinnerItems)
        binding.spinnerRoutineDuration.adapter = spinnerAdapter
    }

    private fun getBasicArrayAdapter(spinnerItems: List<String>): ArrayAdapter<String> =
        ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            spinnerItems
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

    private fun getTimeOptions(): List<String> = listOf(
        "30 minutos",
        "45 minutos",
        "1 hora",
        "1 hora 30 minutos",
        "2 horas",
        "2 horas 30 minutos",
        "3 horas"
    )


    private fun setupOnClicks() {
        binding.apply {
            btnBack.setOnClickListener { finish() }
            btnAddNewExerciseToRoutine.setOnClickListener {
                startActivityForResult.launch(
                    Intent(
                        this@CreateRoutineActivity,
                        SearchExerciseActivity::class.java
                    )
                )
            }
            btnSaveRoutine.setOnClickListener {
                if (validateRoutineFields()) {
                    val routine = createRoutineWithFields()
                    saveRoutine(routine)
                }
            }
        }
    }

    private fun saveRoutine(routineToSave: Routine) {
        routinesViewModel.insertRoutine(routineToSave)
    }

    private fun createRoutineWithFields(): Routine {
        binding.apply {
            val routineName = etRoutineName.text.toString()
            val exerciseList = getExerciseListByExerciseAdapter()
            val routineCategory = getRoutineCategorySelection()
            val duration = convertToMinutes(getDurationSpinnerSelection())
            return Routine(
                routineName,
                routineCategory,
                minutesDuration = duration,
                exerciseList = exerciseList
            )
        }
    }

    private fun convertToMinutes(durationSelection: String): Int {
        return when {
            durationSelection.contains("minutos") -> {
                //con split dividimos las palabras y obtenemso la primera
                durationSelection.split(" ")[0].toIntOrNull() ?: 0
            }

            durationSelection.contains("hora") -> {
                val splitText = durationSelection.split(" ")
                val hours = splitText[0].toIntOrNull() ?: 0
                val minutes = if (durationSelection.contains("minutos")) splitText[2].toIntOrNull()
                    ?: 0 else 0

                hours * 60 + minutes
            }

            else -> 0
        }
    }

    private fun getDurationSpinnerSelection(): String =
        binding.spinnerRoutineDuration.selectedItem?.toString() ?: "???"


    private fun getRoutineCategorySelection(): String {
        binding.spinnerRoutineType.apply {
            val adapter = this.adapter as? SpinnerAdapterCustom
            val selectedSpinnerPosition = this.selectedItemPosition
            return adapter?.getTypeByPosition(selectedSpinnerPosition) ?: "???"
        }
    }

    private fun getExerciseListByExerciseAdapter(): List<Exercise?> {
        val adapter = binding.rvExercisesToRoutine.adapter as? ExerciseToRoutineAdapter
        return adapter?.getExerciseList() ?: emptyList()
    }


    private fun validateRoutineFields(): Boolean {
        binding.apply {
            val routineName = etRoutineName.text.toString().trim()
            val recyclerItems = rvExercisesToRoutine.adapter?.itemCount ?: 0

            when {
                routineName.isEmpty() -> toast("Rellena el nombre de la rutina")
                recyclerItems == 0 -> toast("Añade algun ejercicio")
                else -> return true
            }
            return false
        }
    }

    private fun getTypeExercisesOptions(): List<Pair<String, Int>> =
        listOf(
            Pair(exerciseTypes[0], R.drawable.ic_corazon),
            Pair(exerciseTypes[1], R.drawable.ic_fuerza),
            Pair(exerciseTypes[2], R.drawable.ic_hipertrofia),
            Pair(exerciseTypes[3], R.drawable.ic_velocidad)
        )


}