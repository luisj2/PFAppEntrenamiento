package com.example.fithealth;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class PantallaCarga {

    public static Dialog cargarPantallaCarga(Context context){

        Dialog dialog = new Dialog(context);

        if(dialog != null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //el fondo de la vista se vera tranparente
            dialog.setContentView(R.layout.pantalla_carga); //utillizara un layout custom para mostrar dicho laout como el dialog
            dialog.setCancelable(false); //no se puede cancelar al tocar la pantalla el usuario
            dialog.setCanceledOnTouchOutside(false); //el dialog no se quitara aunque el usuario toque afuera del dialog

            return dialog;
        }

        return null;
    }
    public static void esconderDialog(Dialog dialog){
        if(dialog != null && dialog.isShowing()){
            dialog.cancel();
        }
    }
}
