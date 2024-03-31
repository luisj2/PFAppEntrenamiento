package com.example.fithealth.PantallasPrincipales.principales.Social;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.fithealth.BusquedaSocial.SocialAdapter;
import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.R;
import com.example.fithealth.Rutinas.AdapterRutina;
import com.example.fithealth.Usuario.Usuario;
import com.example.fithealth.UtilsHelper;

import java.util.List;


public class SocialFragment extends Fragment {



    public SocialFragment() {
        // Required empty public constructor
    }


    UtilsHelper utHelper;
    SearchView svBarraBusqueda;

    Button btnBuscar;

    FirebaseHelper helper; //Objeto para metodos relaccionado con firebase

    RecyclerView rvSocialUsuarios;

    SocialAdapter adapter;

    View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_social,container,false);

        inicializarVariables();

        enlazarComponentes(view);

        onClickBtnBuscar();

        return view;

    }

    private void inicializarVariables() {
        utHelper = new UtilsHelper();
        helper = new FirebaseHelper(getActivity());
    }

    //OnClick del boton de buscar donde
    private void onClickBtnBuscar() {

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultaBusqueda();
            }
        });
    }

    private void enlazarComponentes(View view) {
        btnBuscar = view.findViewById(R.id.btnBuscarUsuarioSocial);
        svBarraBusqueda = view.findViewById(R.id.barraBusquedaSocial);
        rvSocialUsuarios = view.findViewById(R.id.rvListaUsuarios);
    }

    //Realizamos una consulta en Firebase y la  mostramos en el RecyclerView con el adaptador
    private void consultaBusqueda(){
        //Obtenemos lo que ha escrito el usuario en la barra de busqueda
        String nombreBusqueda =  svBarraBusqueda.getQuery().toString();

        helper.getUsuariosPorNombre(nombreBusqueda, new FirebaseHelper.CargaUsuarios() {
            @Override
            public void implementacionUsuariosFirestore(List<Usuario> usuarios) {
                crearAdapterBusquedaSocial(usuarios);

            }
        });
    }

    private void crearAdapterBusquedaSocial(List<Usuario> usuarios) {
        adapter = new SocialAdapter(usuarios);
        rvSocialUsuarios.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvSocialUsuarios.setAdapter(adapter);
    }
}