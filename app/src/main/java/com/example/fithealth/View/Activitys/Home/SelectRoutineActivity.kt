package com.example.fithealth.View.Activitys.Home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithealth.Model.DataClass.Routine
import com.example.fithealth.R
import com.example.fithealth.View.ReyclerAdapters.Training.RotineAdapter.RoutineAdapter
import com.example.fithealth.ViewModel.Firebase.Firestore.Routines.RoutinesViewModel
import com.example.fithealth.ViewModel.Firebase.Firestore.Routines.RoutinesViewModelBuilder
import com.example.fithealth.databinding.ActivitySelectRoutineBinding

//poner en uso la entidad del dia creado
//probar el recycler si funciona
class SelectRoutineActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectRoutineBinding
    private var menu: Menu? = null
    private val routineListToSend: MutableList<Routine?> = mutableListOf()

    private val routinesViewModel: RoutinesViewModel by viewModels {
        RoutinesViewModelBuilder.getRoutinesViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectRoutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        setupRoutineRecycler()
        setupRoutinesObserver()
        setupNavigateToolbarButton();
        setupButtons()
        routinesViewModel.getAllRoutines()
    }

    private fun setupButtons() {
        binding.apply {
            btnRegisterRoutine.setOnClickListener { registerSelectedRoutines() }
        }
    }

    private fun registerSelectedRoutines() {
       for(selectedRoutine in routineListToSend){

       }
    }

    private fun setupRoutineRecycler() {
        binding.apply {
            rvRoutines.layoutManager =
                LinearLayoutManager(this@SelectRoutineActivity, LinearLayoutManager.VERTICAL, false)
            rvRoutines.adapter = RoutineAdapter(onSelectRoutine = ::selectRoutine, onDeselectRoutine = ::deselectRoutine)
        }
    }

    private fun setupRoutinesObserver() {
        routinesViewModel.apply {
            allRoutine.observe(this@SelectRoutineActivity) { routineList ->
                setNoItemMessageVisibility(routineList.isEmpty())
                updateRoutineListAdapter(routineList)
            }
        }
    }

    private fun setNoItemMessageVisibility(visible : Boolean){
        binding.tvNoItems.visibility = if(visible) View.VISIBLE else View.GONE
    }

    private fun selectRoutine(routine : Routine){
        routineListToSend.add(routine)
        updateSelectedCount()
    }

    private fun deselectRoutine(routine : Routine){
        routineListToSend.remove(routine)
        updateSelectedCount()
    }

    private fun setupNavigateToolbarButton() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_backarrowblack)
        }
    }

    private fun updateRoutineListAdapter(routineList: List<Routine?>) {
        val adapter = binding.rvRoutines.adapter as? RoutineAdapter
        adapter?.updateList(routineList)
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
        val menuItem = menu?.findItem(R.id.action_select_count)

        val selectionText = if (routineListToSend.size > 1) "seleccionados" else "seleccionado"
        menuItem?.title = "${routineListToSend.size} $selectionText"
    }
}