package com.example.fithealth.AdapterRutinas;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.R;

public class ViewHolderRutina extends RecyclerView.ViewHolder {

    private TextView tvNombreRutina;
    private Button button;
    public ViewHolderRutina(@NonNull View itemView) {
        super(itemView);
        tvNombreRutina = itemView.findViewById(R.id.tvRutina);
        button = itemView.findViewById(R.id.btnActivityMostrarRutinas);
        funcionalidadButton();
    }

    private void funcionalidadButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("informacionBoton","La informacion del este item es "+tvNombreRutina.getText().toString());
            }
        });
    }

    public void cambiarTextViewRutina(String nombreRutina){
        tvNombreRutina.setText(nombreRutina);
    }



}
