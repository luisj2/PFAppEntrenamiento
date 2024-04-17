package com.example.fithealth.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.fithealth.BarraSuperiorFragment;
import com.example.fithealth.PantallasPrincipales.principales.Entrenamiento.MenuEntrenamientoFragment;
import com.example.fithealth.PantallasPrincipales.principales.Dieta.DietaFragment;
import com.example.fithealth.PantallasPrincipales.principales.Home.HomeFragment;
import com.example.fithealth.PantallasPrincipales.principales.Social.BusquedaSocial.MensajeriaFragment;
import com.example.fithealth.PantallasPrincipales.principales.Social.MenuSocialFragment;
import com.example.fithealth.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityPrincipal extends AppCompatActivity {


    //instacias de los fragment principales
    Fragment primeraPantalla;
    Fragment segundaPantalla;
    Fragment terceraPantalla;
    Fragment cuartaPantalla;


    public ActivityPrincipal(){}



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
            setCurrentFragment(primeraPantalla);


            //la funcionalidad para cambiar el fragment del contentView segun al boton de la
            // barra de navegacion que de el usuario
            funcionalidadNavegacion();
    }

    private void inicializarComponentes() {
        primeraPantalla = new HomeFragment();
        segundaPantalla = new DietaFragment();
        terceraPantalla = new MenuEntrenamientoFragment();
        cuartaPantalla = new MenuSocialFragment();


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

               if(item.getItemId() == R.id.itemHome && !getFragmentoActual().equals(primeraPantalla)){ //ponemos el fragment de home
                   setCurrentFragment(primeraPantalla);
                   seleccion = true;
               }else if(item.getItemId() == R.id.itemDieta && !getFragmentoActual().equals(segundaPantalla)){ //ponemos el fragment de dieta
                   setCurrentFragment(segundaPantalla);
                   seleccion = true;
               }else if(item.getItemId() == R.id.itemEntrenamiento && !getFragmentoActual().equals(terceraPantalla)){ //ponemos el fragment de entrenamiento
                    setCurrentFragment(terceraPantalla);
                    seleccion = true;
               }else if(item.getItemId() == R.id.itemSocial && !getFragmentoActual().equals(cuartaPantalla)){ // ponemos el fragment de social
                    setCurrentFragment(cuartaPantalla);
                    seleccion = true;
               }

                return seleccion;
            }


        });
    }


    //con este metodo modificamos el contenedor del fragment con el fragment que me pase por parametros
    //con ayuda del metodo replace(contenedor,fragment nuevo)
    public void setCurrentFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.ContainerView,fragment).commit();
    }

    //obtenemos el fragment que hay acutualmente en el container
    public Fragment getFragmentoActual() {
        return getSupportFragmentManager().findFragmentById(R.id.ContainerView);
    }

}