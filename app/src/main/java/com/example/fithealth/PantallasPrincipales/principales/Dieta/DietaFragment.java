package com.example.fithealth.PantallasPrincipales.principales.Dieta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.fithealth.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class DietaFragment extends Fragment {


    public DietaFragment() {

    }

    BottomNavigationView barraNavegacionDieta;

    boolean seleccion;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dieta, container, false);


        enlazazarComponentes(view);

        barraNavegacionDietaFuncionalidad();


        return view;
    }

    private void barraNavegacionDietaFuncionalidad() {
        barraNavegacionDieta.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                seleccion = false;

                if(item.getItemId() == R.id.itemBusquedaComidas){
                    seleccion = true;

                }else if(item.getItemId() == R.id.itemRegistroComidas){

                    seleccion = true;

                }else if(item.getItemId() == R.id.itemRegistroComidasGuardadas){

                    seleccion = true;


                }

                return seleccion;
            }
        });
    }

    private void enlazazarComponentes(View view) {
        barraNavegacionDieta = view.findViewById(R.id.barraNavegacionDieta);
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerViewComidas,fragment).commit();
    }

    public Fragment getFragmentActual(){
        Fragment fragmentTransaction = getActivity().getSupportFragmentManager().findFragmentById(R.id.containerViewComidas);
        return fragmentTransaction;
    }
}