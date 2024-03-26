package com.example.fithealth;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fithealth.Activitys.AjustesActivity;


public class BarraSuperiorFragment extends Fragment {




    private TextView txtViewNombreUsuario;

    private ImageButton buttonImagenPerfil;
    
    private ImageButton botonAjustes;

    SharedPreferences preferences;

    Context context;


    public BarraSuperiorFragment(@NonNull Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("DatosInicio", MODE_PRIVATE);
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflarVista(inflater, container);

        enlazarComponentes(view);


        //listener del boton de la imagen de perfil
        funcionalidadImagenPerfil();

        //listener del boton de ajuestes
        funcionalidadBtnAjuestes();

        // txtViewNombreUsuario.setText();
        return view;
    }

    private void funcionalidadBtnAjuestes() {
        botonAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AjustesActivity.class));
            }
        });
    }

    private void funcionalidadImagenPerfil() {
        buttonImagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hola", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View inflarVista(LayoutInflater inflater,ViewGroup container) {
        return inflater.inflate(R.layout.fragment_barra_superior,container,false);
    }

    private void enlazarComponentes(View view) {
        txtViewNombreUsuario = view.findViewById(R.id.txtViewNombreUsuario);

        buttonImagenPerfil = view.findViewById(R.id.buttonPerfil);

        botonAjustes = view.findViewById(R.id.buttonAjustes);
    }

}