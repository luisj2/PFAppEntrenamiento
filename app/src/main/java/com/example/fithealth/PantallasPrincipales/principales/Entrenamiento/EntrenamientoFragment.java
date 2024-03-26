package com.example.fithealth.PantallasPrincipales.principales.Entrenamiento;

import android.os.Bundle;

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


    private Button btnCrearEntrenamiento;
    private List<String> rutinas;

    private RecyclerView rvListaRutinas;

    private AdapterRutina adapterRutina;



    public EntrenamientoFragment() {}




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rutinas = new ArrayList<String>(Arrays.asList("Pecho","Espalda"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrenamiento,container,false);

        enlazarComponentes(view);

        adapterRutina = new AdapterRutina(rutinas,this.getContext());

        rvListaRutinas.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvListaRutinas.setAdapter(adapterRutina);

        return view;
    }

    private void funcionalidadBtnCrearEntrenamiento() {
        btnCrearEntrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void enlazarComponentes(View view){
        btnCrearEntrenamiento = view.findViewById(R.id.btnCrearRutina);
        rvListaRutinas = view.findViewById(R.id.rvListaRutinas);
    }
}