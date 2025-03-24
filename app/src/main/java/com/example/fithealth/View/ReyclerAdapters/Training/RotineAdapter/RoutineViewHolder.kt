package com.example.fithealth.View.ReyclerAdapters.Training.RotineAdapter

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.Routine
import com.example.fithealth.R
import com.example.fithealth.databinding.ItemRoutineBinding
import com.example.fithealth.exercisesTypesImagesMap

class RoutineViewHolder(private val binding: ItemRoutineBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var isSelect = false
    fun bind(
        routine: Routine?,
        onSelectRoutine: ((Routine) -> Unit)?,
        onDeselectRoutine: ((Routine) -> Unit)?
    ) {
        if (routine == null) setupNullState()
        else setupWithRoutineState(routine, onSelectRoutine, onDeselectRoutine)

    }

    private fun setupWithRoutineState(
        routine: Routine,
        onSelectRoutine: ((Routine) -> Unit)?,
        onDeselectRoutine: ((Routine) -> Unit)?
    ) {
        setupFieldsWithRoutine(routine)
        setupSelectionViewOnClick(routine, onSelectRoutine, onDeselectRoutine)
    }

    private fun setupNullState() {
        setupWithNullInfo()
        setupOnNullSelect()
    }

    private fun setupSelectionViewOnClick(
        routine: Routine,
        onSelectRoutine: ((Routine) -> Unit)?,
        onDeselectRoutine: ((Routine) -> Unit)?
    ) {
        getView().setOnClickListener {
            handleRoutineSelected(
                routine,
                onSelectRoutine,
                onDeselectRoutine
            )
        }
    }

    private fun handleRoutineSelected(
        routine: Routine,
        onSelectRoutine: ((Routine) -> Unit)?,
        onDeselectRoutine: ((Routine) -> Unit)?
    ) {
        if (onSelectRoutine == null) {
            setupOnNullSelect()
            return
        }

        if (!isSelect) selectRoutine(routine, onSelectRoutine)
        else deselectRoutine(routine, onDeselectRoutine)

    }

    private fun deselectRoutine(routine: Routine, onDeselectRoutine: ((Routine) -> Unit)?) {
        onDeselectRoutine?.invoke(routine)
        deselectView(getView())
        isSelect = false
    }

    private fun selectRoutine(routine: Routine, onSelectRoutine: (Routine) -> Unit) {
        onSelectRoutine.invoke(routine)
        selectView(getView())
        isSelect = true
    }

    private fun selectView(view: View) {
        view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.verdeAceptar))
    }

    private fun deselectView(view: View) {
        view.background = null
    }

    private fun setupOnNullSelect() {
        getView().setOnClickListener {
            showToast("No se puede seleccionar este elemento")
        }
    }

    private fun setupFieldsWithRoutine(
        routine: Routine
    ) {
        binding.apply {
            tvRoutineName.text = routine.routineName
            tvExerciseDuration.text = routine.minutesDuration.toString()
            tvExercisesNumber.text = routine.exerciseList.size.toString()
            ivRoutineType.setImageResource(
                exercisesTypesImagesMap[routine.routineCategory]
                    ?: R.drawable.ic_ejercicio_predeterminado
            )
        }
    }

    private fun setupWithNullInfo() {
        val unknown = "???"
        binding.apply {
            tvRoutineName.text = unknown
            tvExerciseDuration.text = unknown
            tvExercisesNumber.text = unknown
            ivRoutineType.setImageResource(R.drawable.ic_ejercicio_predeterminado)
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun getContext(): Context = getView().context

    private fun getView(): View = binding.root

}