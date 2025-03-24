package com.example.fithealth.View.Activitys.Training

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithealth.Model.DataClass.Exercise
import com.example.fithealth.Model.Utils.ExercisesSelected
import com.example.fithealth.Model.Utils.ExtensionUtils.onTextChanged
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.R
import com.example.fithealth.View.ReyclerAdapters.Training.ExerciseToRoutineAdapter.ExerciseToRoutineAdapter
import com.example.fithealth.ViewModel.Firebase.Firestore.Exercise.ExerciseFirestoreViewModel
import com.example.fithealth.ViewModel.Firebase.Firestore.Exercise.ExerciseFirestoreViewModelBuilder
import com.example.fithealth.databinding.ActivitySearchExerciseBinding

class SearchExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchExerciseBinding

    private var menu: Menu? = null
    private val exerciseListToSend: MutableList<Exercise> = mutableListOf()
    private var avariableExercise: List<Exercise?> = listOf()

    private val exerciseViewModel: ExerciseFirestoreViewModel by viewModels {
        ExerciseFirestoreViewModelBuilder.getExerciseFirestoreViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }


    private fun setupUI() {
        setupExerciseToRoutineRecycler()
        setupOnClicks()
        setupNavegateToolbarButton()
        searchTextListener()
        setupExerciseFirestoreObservers()
        exerciseViewModel.getAlluserExercises()
    }

    private fun setupOnClicks() {
        binding.apply {
            btnSaveExercisesInRoutine.setOnClickListener {
                if (exerciseListToSend.isEmpty()) toast("Añade algún ejercicio")
                else {
                    ExercisesSelected.exerciseSelectedList.addAll(exerciseListToSend.toSet())
                    setResult(Activity.RESULT_OK, Intent())
                    finish()
                }

            }
        }
    }

    private fun setupNavegateToolbarButton() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_backarrowblack)
        }
    }

    private fun setupExerciseFirestoreObservers() {
        exerciseViewModel.apply {
            allExercises.observe(this@SearchExerciseActivity) { exerciseList ->
                avariableExercise =
                    exerciseList.filter { it !in ExercisesSelected.exerciseSelectedList }
                updateExerciseRecyclerWithList(avariableExercise)
            }

            exerciseContainsName.observe(this@SearchExerciseActivity) { exerciseList ->
                updateExerciseRecyclerWithList(exerciseList)
            }
        }
    }

    private fun updateExerciseRecyclerWithList(exerciseList: List<Exercise?>) {
        if (exerciseList.isNotEmpty()) updateExerciseList(exerciseList)
    }

    private fun updateExerciseList(exerciseList: List<Exercise?>) {
        val exerciseAdapter = binding.rvBuscarEjercicios.adapter as? ExerciseToRoutineAdapter
        exerciseAdapter?.updateList(exerciseList)
    }

    private fun setupExerciseToRoutineRecycler() {
        binding.rvBuscarEjercicios.apply {
            layoutManager = LinearLayoutManager(this@SearchExerciseActivity)
            adapter =
                ExerciseToRoutineAdapter(emptyList(), ::exerciseSelection, ::exerciseDeselection)
        }
    }

    private fun exerciseSelection(exercise: Exercise) {
        exerciseListToSend.add(exercise)
        updateSelectedCount()
    }

    private fun exerciseDeselection(exercise: Exercise) {
        exerciseListToSend.remove(exercise)
        updateSelectedCount()
    }

    private fun searchTextListener() {
        binding.etBusquedaEjercicio.onTextChanged { searchText ->
            searchExerciseByName(searchText)
        }
    }


    private fun searchExerciseByName(searchText: String?) {
        if (searchText.isNullOrBlank()) {
            exerciseViewModel.getAlluserExercises()
            binding.etBusquedaEjercicio.text.clear()
        } else {
            val filteredList = avariableExercise.filter { it?.exerciseName?.startsWith(searchText, ignoreCase = true) == true }
            updateExerciseList(filteredList)
        }
    }


    //MENU

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_items_selected, menu)
        this.menu = menu
        updateSelectedCount()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateSelectedCount() {
        Log.i("info_update_count", "el menu es null: ${menu == null}")
        menu?.findItem(R.id.action_select_count)?.title = "${exerciseListToSend.size} seleccionados"
    }


}