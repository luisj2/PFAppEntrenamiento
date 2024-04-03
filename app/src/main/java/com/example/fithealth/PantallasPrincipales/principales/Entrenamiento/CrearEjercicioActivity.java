package com.example.fithealth.PantallasPrincipales.principales.Entrenamiento;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fithealth.AdapterSpinnerCustom;
import com.example.fithealth.Ejercicios.Ejercicio;
import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.R;
import com.example.fithealth.TipoEjercicioIconos;
import com.example.fithealth.UtilsHelper;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

public class CrearEjercicioActivity extends AppCompatActivity {

    Spinner spinnerTipoEjercicio;

    ActivityResultLauncher<String> multimediaLauncher;

    Spinner spinnerPrivacidades;

    EditText etNombreEjercicio;

    Button btnAniadirEjercicio;

    ImageView ivImagenEjercicio;

    ImageButton btnAniadirImagen;

    FirebaseHelper helper;


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

        pedirAniadirImagen();


    }

    private void pedirAniadirImagen() {

        btnAniadirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(CrearEjercicioActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(btnAniadirImagen.getWidth(), btnAniadirImagen.getHeight())	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();

        ivImagenEjercicio.setImageURI(uri);
    }

    private void inicializarVariables() {
        tiposMedidas = rellenarMedidas();
        tiposEjercicios = rellenarTiposEjercicio();
        opcionesTiposEjercicios = new ArrayList<>();
        helper = new FirebaseHelper(getApplicationContext());
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
        Ejercicio ejercicio = crearEjercicio();
        helper.aniadirEjercicioFirestore(ejercicio);
    }

    private Ejercicio crearEjercicio() {
        String nombreEjercicio = etNombreEjercicio.getText().toString();
        int imageResource = 0;
        String tipoEjercicio = txtIndexSpinner(spinnerTipoEjercicio);
        String privacidad = txtIndexSpinner(spinnerPrivacidades);


        Ejercicio ejercicio = new Ejercicio("",nombreEjercicio,0L,tipoEjercicio,privacidad);

        return ejercicio;
    }

    private String txtIndexSpinner(Spinner spinner) {
        ArrayAdapter <TipoEjercicioIconos> tipoEjercicioAdapter = (ArrayAdapter<TipoEjercicioIconos>) spinner.getAdapter();

        int posicion = spinner.getSelectedItemPosition();

        return tipoEjercicioAdapter.getItem(posicion).getNombre();

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
        etNombreEjercicio = findViewById(R.id.etNombreEjercicio);
        ivImagenEjercicio = findViewById(R.id.ivImagenCrearEjercicio);
        btnAniadirImagen = findViewById(R.id.btnImagenEjercicio);
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