package com.example.fithealth.Permisos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Permisos {

    public Permisos() {}

    public boolean conexionEstable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null){
            NetworkInfo conexionInfo = connectivityManager.getActiveNetworkInfo();
            return conexionInfo != null && conexionInfo.isConnectedOrConnecting();
        }

        return false;
    }


}
