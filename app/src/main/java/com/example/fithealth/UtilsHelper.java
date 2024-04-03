package com.example.fithealth;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class UtilsHelper {

    Context context;

    public UtilsHelper(Context context) {

        this.context = context;
    }


    //Crar vista
    public View crearVista(LayoutInflater inflater, ViewGroup parent, int layoutItem){
        return inflater.inflate(layoutItem,parent,false);
    }

    public String getRutaImagen (String nombreImagen){
        return "android.resource://" + context.getPackageName() + "/drawable/" + nombreImagen;

    }

    public void adaptarImagen (String rutaImagen, ImageView contenedor,int ancho,int alto) {
        Glide.with(context)
                .load(Uri.parse(rutaImagen))
                .override(ancho, alto)
                .into(contenedor);
    }

    public void adaptarImagen (int imageResource,ImageView contenedor,int ancho,int alto){
        Glide.with(context)
                .load(imageResource)
                .override(ancho, alto)
                .into(contenedor);
    }




    public String getTxtTextView (TextView tv){
        return tv.getText().toString().trim();
    }

    public String gettxtEditText (EditText et){
        return et.getText().toString().toLowerCase().trim();
    }
}
