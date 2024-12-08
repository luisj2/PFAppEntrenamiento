package com.example.fithealth.View.Fragments.Training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fithealth.R

class TrainingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_entrenamiento, container, false)
    }

    //Inicilizamos y le ponemos el adapter al RecyclerView inicializado antes
    /*
    private fun crearAdapterRutinas() {
        adapterRutina = AdapterRutina(rutinas, this.context)
        rvListaRutinas!!.layoutManager = LinearLayoutManager(requireContext())
        rvListaRutinas!!.adapter = adapterRutina
    }

    //Onclick del boton de crear entrenamiento que nos desplaza a la ativity correspondiente
    private fun funcionalidadBtnCrearEntrenamiento() {
        btnCrearEjercicio!!.setOnClickListener {
            val intent = Intent(activity, CreateExerciseFragment::class.java)
            startActivity(intent)
        }
    }
    */
}