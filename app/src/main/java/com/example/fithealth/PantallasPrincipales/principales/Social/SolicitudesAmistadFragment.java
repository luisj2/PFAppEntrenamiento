package com.example.fithealth.PantallasPrincipales.principales.Social;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.R;


public class SolicitudesAmistadFragment extends Fragment {

    private FirebaseHelper helper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_solicitudes_amistad, container, false);

        inicializarVariables();

        helper.getIdUsuarioActual(new FirebaseHelper.IdUsuarioActual() {
            @Override
            public void getIdUsuario(String id) {
                helper.solicitudesAmistadlistener(id);
            }
        });


        return view;
    }

    private void inicializarVariables() {
        helper = new FirebaseHelper(this.getContext());
    }
}