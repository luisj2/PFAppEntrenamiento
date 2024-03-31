package com.example.fithealth.Activitys;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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

        cambiarColorFuenteTV();

        inicializarAnimaciones();

        enlazarAnimaciones();

        cambiarActivityConDelay();

    }


    private void cambiarColorFuenteTV() {
        String primeraParte = "Fit";
        String segundaParte = "Health";

        Spannable spannable = new SpannableString(primeraParte+segundaParte);

        spannable.setSpan(new ForegroundColorSpan(Color.BLACK),0,primeraParte.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvNombre.setText(spannable);

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
                startActivity(new Intent(getApplicationContext(), ActivityPrincipal.class));
            }
        },1600);
    }



    private void enlazarComponentes() {
        tvNombre = findViewById(R.id.tvNombre);
        ivIcono = findViewById(R.id.ivIconoAnimado);

    }
}