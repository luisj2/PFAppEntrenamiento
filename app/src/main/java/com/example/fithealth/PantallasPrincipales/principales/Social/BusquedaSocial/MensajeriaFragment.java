package com.example.fithealth.PantallasPrincipales.principales.Social.BusquedaSocial;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fithealth.BaseDeDatos.SqliteHelper;
import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.PantallasPrincipales.principales.Social.Contacto;
import com.example.fithealth.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MensajeriaFragment extends Fragment {


    private RecyclerView rvContactos;

    private List<Contacto> amigos;

    private SqliteHelper sqlHelper;

    private FirebaseHelper helper;

    private ContactosAdapter adapter;

    public MensajeriaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mensajeria, container, false);

        enlazarComponentes(view);

        inicializarVariables();


        adapter = new ContactosAdapter(amigos);
        rvContactos.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvContactos.setAdapter(adapter);

        amigosListener();
        return view;
    }

    private void inicializarVariables() {
        sqlHelper = new SqliteHelper(getActivity());
        amigos = sqlHelper.getAmigos();
        helper = new FirebaseHelper(getActivity());
    }


    private void enlazarComponentes(View view) {

        rvContactos = view.findViewById(R.id.rvContactos);

    }

    private void amigosListener() {

        FirebaseHelper.getIdUsuarioActual(new FirebaseHelper.IdUsuarioActual() {
            @Override
            public void getIdUsuario(String id) {

                helper.escuchadorAmigos(id,new FirebaseHelper.ActualizarIUSnapshoot() {
                    @Override
                    public void contactoAniadido(DataSnapshot snapshot) {
                        aniadirAmigoIU(snapshot);
                    }

                    @Override
                    public void contactoEliminado(DataSnapshot snapshot) {

                        eliminarAmigoIU(snapshot);
                    }
                });
            }
        });


    }

    private void eliminarAmigoIU(DataSnapshot snapshot) {
        Contacto contacto = obtenerContactoDeSnapshoot(snapshot);

        for (int i = 0; i < amigos.size(); i++) {
            Contacto amigo = amigos.get(i);

            if (amigo.getId().equals(contacto.getId())) {
                amigos.remove(i);
                sqlHelper.eliminarAmigo(amigo.getId());
                adapter.setContactos(amigos);
                adapter.notifyItemChanged(i);
                break;
            }
        }


    }

    private void aniadirAmigoIU(DataSnapshot snapshot) {

            Contacto contacto = obtenerContactoDeSnapshoot(snapshot);

            if(!amigoExiste(contacto)){
                amigos.add(contacto);
                sqlHelper.aniadirAmigo(contacto);
                adapter.setContactos(amigos);
                adapter.notifyItemChanged(amigos.size() - 1);
            }

        }



    private boolean amigoExiste(Contacto contacto) {
        boolean existe = false;

        for (Contacto contactoIteracion : amigos) {
            if(contacto.getId().equals(contactoIteracion.getId())){
                existe = true;
                break;
            }
        }

        return existe;

    }

    private Contacto obtenerContactoDeSnapshoot(DataSnapshot snapshot) {
        String idAmigo = snapshot.getKey();
        String nombre = "";
        Uri imagenPerfil = null;

        for (DataSnapshot childSnapshot : snapshot.getChildren()) {

            String key = childSnapshot.getKey();
            String value = childSnapshot.getValue(String.class);

            if (key.equals("nombre_usuario")) {
                nombre = value;
            } else if (key.equals("imagen_perfil")) {
                try {
                    imagenPerfil = Uri.parse(value);
                } catch (Exception e) {
                    imagenPerfil = null;
                    Log.e("ErrorAmigos", "Fallo al parsear la imagen de usuario");
                }
            }

        }

        return new Contacto(idAmigo, nombre, imagenPerfil);

    }
}