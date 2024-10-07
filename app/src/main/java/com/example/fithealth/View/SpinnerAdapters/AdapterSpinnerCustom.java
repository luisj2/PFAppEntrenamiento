package com.example.fithealth.View.SpinnerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fithealth.R;
import com.example.fithealth.Model.DataClass.TipoEjercicioIconos;

import java.util.List;

public class AdapterSpinnerCustom extends ArrayAdapter<TipoEjercicioIconos> {

    private List <TipoEjercicioIconos> textImagenList;

    LayoutInflater inflater;

    TextView tvNombreTipoEjercicio;
    ImageView ivIconoTipoEjercicio;

    int resource;
    public AdapterSpinnerCustom(Context context, int resource, @NonNull List <TipoEjercicioIconos> opciones) {
        super(context, resource, opciones);
        inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.textImagenList = opciones;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(resource,parent,false);
        TipoEjercicioIconos txtImagen = getItem(position);

        enlazarComponentes(view);

        TipoEjercicioIconos opcion= getItem(position);

        tvNombreTipoEjercicio.setText(opcion.getNombre());
        ivIconoTipoEjercicio.setImageResource(opcion.getImagen());



        return view;
    }

    private void enlazarComponentes(View view) {
        tvNombreTipoEjercicio = view.findViewById(R.id.tvNombreTipoEjercicio);
        ivIconoTipoEjercicio = view.findViewById(R.id.ivImagenTipoEjercicio);
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource,parent,false);
        TipoEjercicioIconos txtImagen = getItem(position);

        enlazarComponentes(view);

        TipoEjercicioIconos opcion= getItem(position);

        tvNombreTipoEjercicio.setText(opcion.getNombre());
        ivIconoTipoEjercicio.setImageResource(opcion.getImagen());



        return view;
    }
}

