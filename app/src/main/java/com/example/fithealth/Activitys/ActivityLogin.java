package com.example.fithealth.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.fithealth.BarraSuperiorFragment;
import com.example.fithealth.PantallasPrincipales.principales.Dieta.DietaFragment;
import com.example.fithealth.PantallasPrincipales.principales.Entrenamiento.EntrenamientoFragment;
import com.example.fithealth.PantallasPrincipales.principales.Home.HomeFragment;
import com.example.fithealth.PantallasPrincipales.principales.Social.SocialFragment;
import com.example.fithealth.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityLogin extends AppCompatActivity {


    //instacias de los fragment principales
    HomeFragment home;
    DietaFragment dieta;
    EntrenamientoFragment entrenamiento;
    SocialFragment social;




    BarraSuperiorFragment barraSuperior;

    private boolean seleccion; //boolean que dira si se ha seleccionado un item

    //inicializamos el componente con el que se movera de fragment el usuario
    BottomNavigationView barraNavegacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            enlazarElementos();


            //inicializamos las instacias de los fragments
           inicializarComponentes();

            //mostrar la barra superior
            getSupportFragmentManager().beginTransaction().replace(R.id.barraArribaFragmentView,barraSuperior).commit();
            //ponemos el fragment de home por defecto
            setCurrentFragment(home);


            //la funcionalidad para cambiar el fragment del contentView segun al boton de la
            // barra de navegacion que de el usuario
            funcionalidadNavegacion();
    }

    private void inicializarComponentes() {
        home = new HomeFragment();
        dieta = new DietaFragment();
        entrenamiento = new EntrenamientoFragment();
        social = new SocialFragment();

        barraSuperior = new BarraSuperiorFragment(this.getApplicationContext());
    }


    public void enlazarElementos(){
        barraNavegacion = findViewById(R.id.bottomNavigationView);
    }

    public void funcionalidadNavegacion() {
        barraNavegacion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                seleccion = false;


                //ponemos el fragment que corresponda a partir del id siempre y cuando no sea el que hay actualmente

               if(item.getItemId() == R.id.itemHome && !getFragmentoActual().equals(home)){ //ponemos el fragment de home
                   setCurrentFragment(home);
                   seleccion = true;
               }else if(item.getItemId() == R.id.itemDieta && !getFragmentoActual().equals(dieta)){ //ponemos el fragment de dieta
                   setCurrentFragment(dieta);
                   seleccion = true;
               }else if(item.getItemId() == R.id.itemEntrenamiento && !getFragmentoActual().equals(entrenamiento)){ //ponemos el fragment de entrenamiento
                    setCurrentFragment(entrenamiento);
                    seleccion = true;
               }else if(item.getItemId() == R.id.itemSocial && !getFragmentoActual().equals(social)){ // ponemos el fragment de social
                    setCurrentFragment(social);
                    seleccion = true;
               }

                return seleccion;
            }
        });
    }


    //con este metodo modificamos el contenedor del fragment con el fragment que me pase por parametros
    //con ayuda del metodo replace(contenedor,fragment nuevo)
    public void setCurrentFragment (Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.ContainerView,fragment).commit();
    }

    //obtenemos el fragment que hay acutualmente en el container
    public Fragment getFragmentoActual() {
        return getSupportFragmentManager().findFragmentById(R.id.ContainerView);
    }

}