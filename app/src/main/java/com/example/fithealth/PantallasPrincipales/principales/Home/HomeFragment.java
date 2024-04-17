    package com.example.fithealth.PantallasPrincipales.principales.Home;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.R;
import com.example.fithealth.UtilsHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


    public class HomeFragment extends Fragment {


    UtilsHelper utHelper;

    View view;

    Button btnAnimacion;

    public HomeFragment() {
    }



    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);
        btnAnimacion = view.findViewById(R.id.btnCargarAnimacion);

        btnAnimacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Dialog dialog = new Dialog(getActivity());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //el fondo de la vista se vera tranparente
                dialog.setContentView(R.layout.pantalla_carga); //utillizara un layout custom para mostrar dicho laout como el dialog
                dialog.setCancelable(false); //no se puede cancelar al tocar la pantalla el usuario
                dialog.setCanceledOnTouchOutside(false);

                dialog.show();

                 */

               // FirebaseHelper helper = new FirebaseHelper(getActivity());

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                reference.setValue("hola mundo").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Hola", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        return view;
    }
}