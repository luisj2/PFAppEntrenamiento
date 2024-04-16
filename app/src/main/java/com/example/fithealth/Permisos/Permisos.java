package com.example.fithealth.Permisos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



public class Permisos {



    public static final int REQUEST_CODE_PERMISSION = 1001;


    public static boolean conexionEstable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null){
            NetworkInfo conexionInfo = connectivityManager.getActiveNetworkInfo();
            return conexionInfo != null && conexionInfo.isConnectedOrConnecting();
        }

        return false;
    }

    public static boolean isPermitidoGaleria(Context context) {

        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void pedirPermisosGaleria (Context context){
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE_PERMISSION);
    }

}
