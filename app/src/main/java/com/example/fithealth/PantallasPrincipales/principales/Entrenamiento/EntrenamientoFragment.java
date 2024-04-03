package com.example.fithealth.PantallasPrincipales.principales.Entrenamiento;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.fithealth.Rutinas.AdapterRutina;
import com.example.fithealth.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntrenamientoFragment extends Fragment {


    private Button btnCrearEjercicio;
    private List<String> rutinas; //ArrayList que le pasaremos al adapter

    private RecyclerView rvListaRutinas;

    private AdapterRutina adapterRutina;



    View view;



    public EntrenamientoFragment() {}




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rutinas = new ArrayList<String>(Arrays.asList("Pecho","Espalda"));

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_entrenamiento,container,false);

        enlazarComponentes(view);

        crearAdapterRutinas();

        funcionalidadBtnCrearEntrenamiento();

        return view;
    }

    //Inicilizamos y le ponemos el adapter al RecyclerView inicializado antes
    private void crearAdapterRutinas() {
        adapterRutina = new AdapterRutina(rutinas,this.getContext());
        rvListaRutinas.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvListaRutinas.setAdapter(adapterRutina);
    }

    //Onclick del boton de crear entrenamiento que nos desplaza a la ativity correspondiente
    private void funcionalidadBtnCrearEntrenamiento() {
        btnCrearEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrearEjercicioActivity.class);
                startActivity(intent);


            }
        });
    }

    public void enlazarComponentes(View view){
        btnCrearEjercicio = view.findViewById(R.id.btnCrearEjercicio);
        rvListaRutinas = view.findViewById(R.id.rvListaRutinas);
    }

}