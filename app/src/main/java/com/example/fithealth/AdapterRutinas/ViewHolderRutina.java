package com.example.fithealth.AdapterRutinas;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.R;

public class ViewHolderRutina extends RecyclerView.ViewHolder {

    private TextView tvNombreRutina;
    public ViewHolderRutina(@NonNull View itemView) {
        super(itemView);
        tvNombreRutina = itemView.findViewById(R.id.tvRutina);
    }

    public void cambiarTextViewRutina(String nombreRutina){
        tvNombreRutina.setText(nombreRutina);
    }



}
