package com.example.fithealth.AdapterRutinas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.R;

import java.util.List;

public class AdapterRutina extends RecyclerView.Adapter<ViewHolderRutina> {


    List<String> nombreRutinas;
    Context context;

    public AdapterRutina(List<String> nombreRutinas, Context context) {
        this.nombreRutinas = nombreRutinas;
        this.context = context;
    }
    //crear una instancia del ViewHolder para crear la vista
    //Parametros: parent hace referencia al reciclerView,el viewTipe al numero de vista a la que queremos hacer referencia si quieremos meter mas de una vista
    @NonNull
    @Override
    public ViewHolderRutina onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.rutina_items,parent,false);

        return new ViewHolderRutina(view);
    }

    //hacemos la funcionalidad de nuestro recyclerview en este caso cambiando el textview que tenemos en el layour al nombre de la
    //rutina que queremos
    @Override
    public void onBindViewHolder(@NonNull ViewHolderRutina holder, int position) {

        if(nombreRutinas.size()==0){

            if(context!=null){
                Toast.makeText(context, "No hay rutinas actualmente", Toast.LENGTH_SHORT).show();
                Log.i("contextNotNull","El contexto no es nulo");
            }else{
                Log.i("contextNull","El contexto es nulo");
            }

        }else{
            String nombreRutina = nombreRutinas.get(position);

            holder.cambiarTextViewRutina(nombreRutina);

        }


    }

    //Cuantos elementos va a haber en nuestro recyclerview
    @Override
    public int getItemCount() {
        return nombreRutinas.size();
    }


}
