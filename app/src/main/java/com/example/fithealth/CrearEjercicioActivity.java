package com.example.fithealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class CrearEjercicioActivity extends AppCompatActivity {

    Spinner spinnerTipoEjercicio;

    Spinner spinnerPrivacidades;

    Button btnAniadirEjercicio;


    UtilsHelper utHelper;

    String [] tiposMedidas,tiposEjercicios;

    List<TipoEjercicioIconos> opcionesTiposEjercicios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ejercicio);

        enlazarComponentes();

        inicializarVariables();

        crearIU();


    }

    private void inicializarVariables() {
        tiposMedidas = rellenarMedidas();
        tiposEjercicios = rellenarTiposEjercicio();
        opcionesTiposEjercicios = new ArrayList<>();
        rellenarOpciones();
    }

    private void crearIU() {
        AdapterSpinnerCustom adapterTiposEjercicios = new AdapterSpinnerCustom(this.getApplicationContext(),R.layout.items_custom_spinner,opcionesTiposEjercicios);
        spinnerTipoEjercicio.setAdapter(adapterTiposEjercicios);

        AdapterSpinnerCustom adapterPrivacidades = new AdapterSpinnerCustom(this.getApplicationContext(),R.layout.items_custom_spinner,rellenarPrivacidades());
        spinnerPrivacidades.setAdapter(adapterPrivacidades);

        onClickbtnAniadirEjercicio();

    }

    private void onClickbtnAniadirEjercicio() {
        btnAniadirEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AniadirEjercicioAFirebase();
            }
        });
    }

    private void AniadirEjercicioAFirebase() {

    }

    private ArrayAdapter<String> crearAdapter (String [] items){
        return new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,items);
    }

    private String [] rellenarMedidas (){
        return new String[]{"Segs","Kg","Placas"};
    }



    private String[] rellenarTiposEjercicio(){
        return new String[]{"Hipertrofia","Fuerza","Velocidad","Resistencia","Otro"};
    }

    private void enlazarComponentes() {
        spinnerTipoEjercicio = findViewById(R.id.spinnerTipoEjercicio);
        spinnerPrivacidades = findViewById(R.id.spinnerPrivacidades);
        btnAniadirEjercicio = findViewById(R.id.btnAniadirEjercicio);
    }

    public void rellenarOpciones (){
        TipoEjercicioIconos opcion1 = new TipoEjercicioIconos("Resistencia",getImagenResource(1));
        opcionesTiposEjercicios.add(opcion1);

        TipoEjercicioIconos opcion2 = new TipoEjercicioIconos("Fuerza",getImagenResource(2));
        opcionesTiposEjercicios.add(opcion2);

        TipoEjercicioIconos opcion3 = new TipoEjercicioIconos("Hipertrofia",getImagenResource(3));
        opcionesTiposEjercicios.add(opcion3);

        TipoEjercicioIconos opcion4 = new TipoEjercicioIconos("Velocidad",getImagenResource(4));
        opcionesTiposEjercicios.add(opcion4);


    }

    private int getImagenResource (int id){

        int resource = 0;
        switch(id){
            case 1:
                resource = R.drawable.ic_corazon;
                break;
            case 2:
                resource = R.drawable.ic_fuerza;
                break;

            case 3:
                resource = R.drawable.ic_hipertrofia;
                break;

            case 4:
                resource = R.drawable.ic_velocidad;
                break;

            default:
                Log.i("error","No se ha encontrado la imagen que se pide");
                break;

        }

        return resource;
    }

    public List <TipoEjercicioIconos> rellenarPrivacidades (){
        List <TipoEjercicioIconos> opcionesPrivacidades = new ArrayList<TipoEjercicioIconos>();

        TipoEjercicioIconos opcion1 = new TipoEjercicioIconos("Privado",R.drawable.ic_privado);
        opcionesPrivacidades.add(opcion1);

        TipoEjercicioIconos opcion2 = new TipoEjercicioIconos("Publico",R.drawable.ic_publico);
        opcionesPrivacidades.add(opcion2);

        return opcionesPrivacidades;
    }





}