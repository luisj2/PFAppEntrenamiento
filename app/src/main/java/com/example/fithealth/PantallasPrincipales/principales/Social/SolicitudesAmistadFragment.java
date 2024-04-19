package com.example.fithealth.PantallasPrincipales.principales.Social;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fithealth.BaseDeDatos.SqliteHelper;
import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class SolicitudesAmistadFragment extends Fragment {

    private FirebaseHelper helper;

    private RecyclerView rvSolicitudesAmistad;

    private ListaAmigosAdapter adapter;

    private List<Contacto> contactos;

    private SqliteHelper sqliteHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_solicitudes_amistad, container, false);

        enlazarComponentes(view);

        inicializarVariables();

        adapter = new ListaAmigosAdapter(contactos);
        rvSolicitudesAmistad.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSolicitudesAmistad.setAdapter(adapter);

        helper.getIdUsuarioActual(new FirebaseHelper.IdUsuarioActual() {
            @Override
            public void getIdUsuario(String id) {
                helper.solicitudesAmistadlistener(id, new FirebaseHelper.ActualizarIUSnapshoot() {
                    @Override
                    public void contactoAniadido(DataSnapshot solicitudSnapshoot) {

                        String idSolicitante = solicitudSnapshoot.getKey();
                        String nombre = "";
                        Uri imagenPerfil = null;

                        for (DataSnapshot childSolicitudSnapshoot : solicitudSnapshoot.getChildren()) {

                            String key = childSolicitudSnapshoot.getKey();
                            String value = childSolicitudSnapshoot.getValue(String.class);

                            if (key.equals("nombre_usuario")) {
                                nombre = value;
                            } else if (key.equals("ruta_imagen")) {
                                try {
                                    imagenPerfil = Uri.parse(value);
                                } catch (Exception e) {
                                    imagenPerfil = null;
                                    Log.e("ErrorImagen", "Error al parsear la imagen");
                                }
                            }

                        }

                        Contacto contacto = new Contacto(idSolicitante, nombre, imagenPerfil);

                        if (!contactoExiste(idSolicitante)) {
                            contactos.add(contacto);
                            sqliteHelper.aniadirContacto(contacto);
                            adapter.setSolicitudesAmistad(contactos);
                            adapter.notifyItemChanged(contactos.size() - 1);
                        }


                    }

                    @Override
                    public void contactoEliminado(DataSnapshot snapshot) {

                        for (DataSnapshot childSnapshoot : snapshot.getChildren()) {

                            childSnapshoot.getRef().addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    //String nombre = snapshot.child("nombre_usuario").getValue(String.class);

                                    String id = snapshot.getKey();
                                    for (int i = 0; i < contactos.size(); i++) {
                                        Contacto contacto = contactos.get(i);

                                        if (contacto.getId().equals(id)) {
                                            sqliteHelper.deleteContacto(id);
                                            contactos.remove(i);
                                            adapter.setSolicitudesAmistad(contactos);
                                            adapter.notifyItemRemoved(i);
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }


                });
            }
        });


        return view;
    }

    private boolean contactoExiste(String idSolicitante) {
        boolean existe = false;

        for (Contacto contacto : contactos) {
            if (contacto.getId().equals(idSolicitante)) {
                existe = true;
                break;
            }
        }

        return existe;
    }

    private void enlazarComponentes(View view) {
        rvSolicitudesAmistad = view.findViewById(R.id.rvSolicitudesAmistad);
    }

    private void inicializarVariables() {
        helper = new FirebaseHelper(this.getContext());
        sqliteHelper = new SqliteHelper(getActivity());
        contactos = sqliteHelper.obtenerContactos();

    }


}