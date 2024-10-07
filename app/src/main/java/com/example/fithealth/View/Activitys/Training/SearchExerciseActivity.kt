package com.example.fithealth.View.Activitys.Training

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.SearchExerciseData
import com.example.fithealth.Model.Firebase.FirebaseHelper
import com.example.fithealth.R

class SearchExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_ejercicio)

    }

    /*
    private fun funcionalidadBuscarEjercicio() {
        btnBuscarEjercicio!!.setOnClickListener {
            val busquedaEjercicio: String = utils.gettxtEditText(etBusqueda)
            helper!!.getEjerciciosData(busquedaEjercicio, object : GestionEjercicios() {
                fun infoEjercicios(ejercicios: List<SearchExerciseData?>) {
                    if (!ejercicios.isEmpty()) {
                        crearAdapterEjercicios(ejercicios)
                    } else {
                        Toast.makeText(
                            this@SearchExerciseActivity,
                            "No hay resultados",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }


    private fun crearIU() {
        val ruta: String = utils.getRutaImagen("ic_lupa")
        utils.adaptarImagen(ruta, btnBuscarEjercicio, 40, 60)
    }


    private fun crearAdapterEjercicios(ejercicios: List<SearchExerciseData?>) {
        val adapter = AdapterBusquedaEjercicio(ejercicios)
        rvBuscarEjercicios!!.adapter = adapter
        rvBuscarEjercicios!!.layoutManager = LinearLayoutManager(this)
    }
     */
}