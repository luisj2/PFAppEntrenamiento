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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


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

        View view = inflater.inflate(R.layout.fragment_barra_superior,container,false);

        txtViewNombreUsuario = view.findViewById(R.id.txtViewNombreUsuario);

        buttonImagenPerfil = view.findViewById(R.id.buttonPerfil);
        
        botonAjustes = view.findViewById(R.id.buttonAjustes);
        

        buttonImagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hola", Toast.LENGTH_SHORT).show();
            }
        });
        
        botonAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(context,AjustesActivity.class));
            }
        });

        txtViewNombreUsuario.setText(preferences.getString("nombreUsuario","Defecto"));


        return view;
    }
}