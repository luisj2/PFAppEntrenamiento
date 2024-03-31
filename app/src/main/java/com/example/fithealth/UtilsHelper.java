package com.example.fithealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class UtilsHelper {

    public UtilsHelper() {
    }


    //Crar vista
    public View crearVista(LayoutInflater inflater, ViewGroup parent, int layoutItem){
        return inflater.inflate(layoutItem,parent,false);
    }




    public String getTxtTextView (TextView tv){
        return tv.getText().toString();
    }
}
