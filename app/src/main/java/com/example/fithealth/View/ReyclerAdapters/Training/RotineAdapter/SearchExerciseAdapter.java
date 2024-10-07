package com.example.fithealth.View.ReyclerAdapters.Training.RotineAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.R;
import com.example.fithealth.Model.DataClass.SearchExerciseData;

import java.util.List;

class AdapterBusquedaEjercicio {

    /*
    List<SearchExerciseData> data;

    View view;

    LayoutInflater inflater;

   // UtilsHelper utils;

    public AdapterBusquedaEjercicio(List<SearchExerciseData> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolderBuscarEjercicio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        //this.utils = new UtilsHelper(parent.getContext());

        view = inflater.inflate(R.layout.item_busqueda_ejercicios,parent,false);

        return new ViewHolderBuscarEjercicio(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBuscarEjercicio holder, int position) {

        SearchExerciseData item = data.get(position);

        holder.setNombreEjercicio(item.getNombreEjercicio());
        holder.setImagenEjercicio(item.getImagenEjercicio());
        holder.setImagenPrivacidad(item.getImagenPrivacidad());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class ViewHolderBuscarEjercicio extends RecyclerView.ViewHolder{

    private TextView tvNombreEjercicio;

    private ImageView ivEjercicio;
    private ImageView ivPrivacidad;
    public ViewHolderBuscarEjercicio(@NonNull View itemView) {
        super(itemView);

        enlazarComponentes(itemView);
    }

    private void enlazarComponentes(View view) {
        tvNombreEjercicio = view.findViewById(R.id.tvNombreEjercicio);
        ivEjercicio = view.findViewById(R.id.ivImagenEjercicio);
        ivPrivacidad = view.findViewById(R.id.ivPrivacidad);
    }

    public ImageView getIvEjercicio() {
        return ivEjercicio;
    }

    public ImageView getIvPrivacidad() {
        return ivPrivacidad;
    }

    public void setNombreEjercicio (String nombreEjercicio){
        tvNombreEjercicio.setText(nombreEjercicio);
    }

    public void setImagenPrivacidad(int resource){
        ivPrivacidad.setImageResource(resource);
    }

    public void setImagenEjercicio(int resource){
        ivEjercicio.setImageResource(resource);
    }


     */

}
