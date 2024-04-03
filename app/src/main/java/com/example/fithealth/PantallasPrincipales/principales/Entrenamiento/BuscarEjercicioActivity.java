package com.example.fithealth.PantallasPrincipales.principales.Entrenamiento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.R;
import com.example.fithealth.UtilsHelper;

import java.util.List;

public class BuscarEjercicioActivity extends AppCompatActivity {


    ImageButton btnBuscarEjercicio;

    RecyclerView rvBuscarEjercicios;

    EditText etBusqueda;

    FirebaseHelper helper;

    UtilsHelper utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_ejercicio);

        enlazarComponentes();

        inicializarVariables();

        crearIU();

        funcionalidadBuscarEjercicio();




    }

    private void funcionalidadBuscarEjercicio() {

        btnBuscarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String busquedaEjercicio = utils.gettxtEditText(etBusqueda);
               helper.getEjerciciosData(busquedaEjercicio, new FirebaseHelper.GestionEjercicios() {
                   @Override
                   public void infoEjercicios(List<BuscarEjercicioData> ejercicios) {
                       if(!ejercicios.isEmpty()){
                           crearAdapterEjercicios(ejercicios);
                       }else {
                           Toast.makeText(BuscarEjercicioActivity.this, "No hay resultados", Toast.LENGTH_SHORT).show();
                       }

                   }
               });

            }
        });

    }

    private void crearIU() {
        String ruta = utils.getRutaImagen("ic_lupa");
        utils.adaptarImagen(ruta,btnBuscarEjercicio,40,60);


    }

    private void inicializarVariables() {

        utils = new UtilsHelper(getApplicationContext());
        helper = new FirebaseHelper(getApplicationContext());
    }

    private void enlazarComponentes() {

        btnBuscarEjercicio = findViewById(R.id.btnBuscarEjercicio);
        rvBuscarEjercicios = findViewById(R.id.rvBuscarEjercicios);
        etBusqueda = findViewById(R.id.etBusquedaEjercicio);
    }

    private void crearAdapterEjercicios (List <BuscarEjercicioData> ejercicios){

        AdapterBusquedaEjercicio adapter = new AdapterBusquedaEjercicio(ejercicios);
        rvBuscarEjercicios.setAdapter(adapter);
        rvBuscarEjercicios.setLayoutManager(new LinearLayoutManager(this));
    }




}