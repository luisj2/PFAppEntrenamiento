package com.example.fithealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fithealth.AccederAplicacion.InicioSesion;

public class AjustesActivity extends AppCompatActivity {



    Button btnCerrarSesion;

    SharedPreferences preferences;

    SharedPreferences.Editor editor;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        enlazarComponentes();
        preferences = getSharedPreferences("DatosInicio", MODE_PRIVATE);
        editor = preferences.edit();

        context = this.getApplicationContext();


        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("iniciada",false).commit();
                startActivity(new Intent(context,InicioSesion.class));
            }
        });
    }

    public void enlazarComponentes(){
        btnCerrarSesion = findViewById(R.id.cerrarSesion);
    }
}