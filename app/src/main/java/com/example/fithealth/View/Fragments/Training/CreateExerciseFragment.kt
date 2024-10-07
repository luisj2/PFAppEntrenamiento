package com.example.fithealth.View.Fragments.Training

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.fithealth.Model.DataClass.Ejercicio
import com.example.fithealth.Model.DataClass.TipoEjercicioIconos
import com.example.fithealth.Model.Firebase.FirebaseHelper
import com.example.fithealth.Model.Permissions.Permisos
import com.example.fithealth.R
import com.example.fithealth.View.SpinnerAdapters.AdapterSpinnerCustom

class CreateExerciseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_crear_ejercicio, container, false)
    }

    /*
    private fun pedirAniadirImagen() {
        btnAniadirImagen!!.setOnClickListener {
            pedirPermisosImagenes()
            imagenGaleria()
        }
    }

    private fun imagenGaleria() {
        val i = Intent(Intent.ACTION_PICK)
        i.setType(LAUNCH_IMAGENES)
        startActivityForResult(i, COD_SEL_IMAGE)
    }

    private fun pedirPermisosImagenes() {
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("imagenInfo", "He entrado en los permisos")
        } else {
            Log.i("imagenInfo", "No he entrado")
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                Permisos.REQUEST_CODE_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Permisos.REQUEST_CODE_PERMISSION) {
            permisosConcedidos =
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido
                    Toast.makeText(activity, "Permisos concedidos", Toast.LENGTH_SHORT).show()
                    true
                    // Realizar la acción deseada aquí
                } else {
                    // Permiso denegado
                    Toast.makeText(activity, "Permisos denegados", Toast.LENGTH_SHORT).show()
                    false
                    // Realizar alguna acción apropiada, como mostrar un mensaje al usuario o finalizar la actividad
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == COD_SEL_IMAGE) {
            urlImagen = data!!.data //obtener la imagen seleccionada por el usuario

            //cargamos la imagen en el ImageView correspondiente
            Glide.with(this)
                .load(urlImagen)
                .override(ivImagenEjercicio!!.width, ivImagenEjercicio!!.height)
                .into(ivImagenEjercicio!!)
        }
    }

    private fun getRutaImagen(idEjercicio: String): String {
        Log.i("IdRegistrado", preferences!!.getString("correoElectronico", "")!!)
        val idusuario = preferences!!.getString("IdUser", "")
        var ruta = ""
        if (!idusuario!!.isEmpty()) {
            ruta = RUTA_IMAGENES + PHOTO + idusuario + idEjercicio
        }
        return ruta
    }

    private fun inicializarVariables() {
        tiposMedidas = rellenarMedidas()
        tiposEjercicios = rellenarTiposEjercicio()
        opcionesTiposEjercicios = ArrayList()
        helper = FirebaseHelper(activity!!)
        preferences = activity!!.getSharedPreferences("DatosInicio", Context.MODE_PRIVATE)
        permisosConcedidos = false
        ruta = ""
        pantallaCarga = PantallaCarga.cargarPantallaCarga(activity)
        rellenarOpciones()
    }

    private fun crearIU() {
        val adapterTiposEjercicios =
            AdapterSpinnerCustom(activity, R.layout.items_custom_spinner, opcionesTiposEjercicios!!)
        spinnerTipoEjercicio!!.adapter = adapterTiposEjercicios
        val adapterPrivacidades =
            AdapterSpinnerCustom(activity, R.layout.items_custom_spinner, rellenarPrivacidades())
        spinnerPrivacidades!!.adapter = adapterPrivacidades
    }

    private fun onClickbtnAniadirEjercicio() {
        btnAniadirEjercicio!!.setOnClickListener {
            if (!etNombreEjercicio!!.text.toString().isEmpty()) {
                pantallaCarga!!.show()
                crearEjercicio()
            } else {
                Toast.makeText(activity, "Completa el nombre del ejercicio", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun crearEjercicio() {
        val nombreEjercicio = etNombreEjercicio!!.text.toString()
        val tipoEjercicio = txtIndexSpinner(spinnerTipoEjercicio)
        val privacidad = txtIndexSpinner(spinnerPrivacidades)
        val ejercicio = Ejercicio("", nombreEjercicio, tipoEjercicio, privacidad)
        helper.aniadirEjercicioFirestore(ejercicio, object : DevolverEjercicio() {
            fun getId(id: String) {
                ruta = getRutaImagen(id)
                idEjercicio = id
                if (ivImagenEjercicio!!.drawable != null &&
                    ivImagenEjercicio!!.drawable.constantState !==
                    activity!!.resources.getDrawable(R.drawable.ic_ejercicio_predeterminado).constantState
                ) {
                    helper.subirImagen(ruta, urlImagen, object : UrlDescargada() {
                        fun getDowloadUrl(urlDescarga: String?) {
                            helper.updateEjercicio(idEjercicio, "foto", urlDescarga)
                            Toast.makeText(
                                activity,
                                "Se ha añadido el ejercicio correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
                PantallaCarga.esconderDialog(pantallaCarga)
            }
        })
    }

    private fun txtIndexSpinner(spinner: Spinner?): String {
        val tipoEjercicioAdapter = spinner!!.adapter as ArrayAdapter<TipoEjercicioIconos>
        val posicion = spinner.selectedItemPosition
        return tipoEjercicioAdapter.getItem(posicion)!!.nombre
    }

    private fun rellenarMedidas(): Array<String> {
        return arrayOf("Segs", "Kg", "Placas")
    }

    private fun rellenarTiposEjercicio(): Array<String> {
        return arrayOf("Hipertrofia", "Fuerza", "Velocidad", "Resistencia", "Otro")
    }

    private fun enlazarComponentes(view: View) {
        spinnerTipoEjercicio = view.findViewById(R.id.spinnerTipoEjercicio)
        spinnerPrivacidades = view.findViewById(R.id.spinnerPrivacidades)
        btnAniadirEjercicio = view.findViewById(R.id.btnAniadirEjercicio)
        etNombreEjercicio = view.findViewById(R.id.etNombreEjercicio)
        ivImagenEjercicio = view.findViewById(R.id.ivImagenCrearEjercicio)
        btnAniadirImagen = view.findViewById(R.id.btnImagenEjercicio)
    }

    fun rellenarOpciones() {
        val opcion1 = TipoEjercicioIconos("Resistencia", getImagenResource(1))
        opcionesTiposEjercicios!!.add(opcion1)
        val opcion2 = TipoEjercicioIconos("Fuerza", getImagenResource(2))
        opcionesTiposEjercicios!!.add(opcion2)
        val opcion3 = TipoEjercicioIconos("Hipertrofia", getImagenResource(3))
        opcionesTiposEjercicios!!.add(opcion3)
        val opcion4 = TipoEjercicioIconos("Velocidad", getImagenResource(4))
        opcionesTiposEjercicios!!.add(opcion4)
    }

    private fun getImagenResource(id: Int): Int {
        var resource = 0
        when (id) {
            1 -> resource = R.drawable.ic_corazon
            2 -> resource = R.drawable.ic_fuerza
            3 -> resource = R.drawable.ic_hipertrofia
            4 -> resource = R.drawable.ic_velocidad
            else -> Log.i("error", "No se ha encontrado la imagen que se pide")
        }
        return resource
    }

    fun rellenarPrivacidades(): List<TipoEjercicioIconos> {
        val opcionesPrivacidades: MutableList<TipoEjercicioIconos> = ArrayList()
        val opcion1 = TipoEjercicioIconos("Privado", R.drawable.ic_privado)
        opcionesPrivacidades.add(opcion1)
        val opcion2 = TipoEjercicioIconos("Publico", R.drawable.ic_publico)
        opcionesPrivacidades.add(opcion2)
        return opcionesPrivacidades
    }

     */
}