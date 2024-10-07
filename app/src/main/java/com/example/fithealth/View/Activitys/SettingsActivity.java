package com.example.fithealth.View.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fithealth.R;
import com.example.fithealth.View.Activitys.AccederAplicacion.LoginActivity;

public class SettingsActivity extends AppCompatActivity {



    Button btnCerrarSesion;

    SharedPreferences preferences;

    SharedPreferences.Editor editor;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        enlazarComponentes();

        inicializarVariables();

        funcionalidadCerrarSesion();
    }

    private void inicializarVariables() {
        preferences = getSharedPreferences("DatosInicio", MODE_PRIVATE);
        editor = preferences.edit();

        context = this.getApplicationContext();
    }

    private void funcionalidadCerrarSesion() {
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("iniciada",false).commit();
                startActivity(new Intent(context, LoginActivity.class));
            }
        });
    }

    public void enlazarComponentes(){
        btnCerrarSesion = findViewById(R.id.cerrarSesion);
    }
}