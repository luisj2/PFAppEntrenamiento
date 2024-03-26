package com.example.fithealth.Activitys;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fithealth.R;

public class ActivityInicio extends AppCompatActivity {


    TextView tvNombre;

    Animation arribaHaciaAbajo;

    Animation abajoHaciaArriba;

    ImageView ivIcono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        enlazarComponentes();

        inicializarAnimaciones();

        enlazarAnimaciones();

        cambiarActivityConDelay();

    }

    private void inicializarAnimaciones() {
        arribaHaciaAbajo = AnimationUtils.loadAnimation(this,R.anim.movimiento_arriba);
        abajoHaciaArriba = AnimationUtils.loadAnimation(this,R.anim.movimiento_abajo);
    }

    private void enlazarAnimaciones() {
        tvNombre.setAnimation(abajoHaciaArriba);
        ivIcono.setAnimation(arribaHaciaAbajo);
    }

    private void cambiarActivityConDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
            }
        },1600);
    }



    private void enlazarComponentes() {
        tvNombre = findViewById(R.id.tvNombre);
        ivIcono = findViewById(R.id.ivIconoAnimado);

    }
}