package com.example.fithealth.PantallasPrincipales.principales.Social.BusquedaSocial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fithealth.PantallasPrincipales.principales.Social.Contacto;
import com.example.fithealth.R;

import java.util.ArrayList;
import java.util.List;

public class MensajeriaFragment extends Fragment {


    RecyclerView rvContactos;

    public MensajeriaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mensajeria, container, false);

        enlazarComponentes(view);

        List<Contacto> contactos = obtenerContactos();
        ContactosAdapter adapter = new ContactosAdapter(contactos);
        rvContactos.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvContactos.setAdapter(adapter);

        return view;
    }

    private List<Contacto> obtenerContactos() {
        List <Contacto> contactos = new ArrayList<>();

        contactos.add(new Contacto("1","Juan",null));
        contactos.add(new Contacto("2","Roberto",null));
        contactos.add(new Contacto("3","Alvaro",null));

        return contactos;
    }

    private void enlazarComponentes(View view) {

        rvContactos = view.findViewById(R.id.rvContactos);
    }
}