package com.example.fithealth.PantallasPrincipales.principales.Entrenamiento;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.fithealth.AdapterSpinnerCustom;
import com.example.fithealth.Ejercicios.Ejercicio;
import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.PantallaCarga;
import com.example.fithealth.Permisos.Permisos;
import com.example.fithealth.R;
import com.example.fithealth.TipoEjercicioIconos;
import com.example.fithealth.UtilsHelper;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

public class CrearEjercicioActivity extends AppCompatActivity {

    private Spinner spinnerTipoEjercicio;

    private ActivityResultLauncher<String> multimediaLauncher;

    private Spinner spinnerPrivacidades;

    private EditText etNombreEjercicio;

    private Button btnAniadirEjercicio;

    private ImageView ivImagenEjercicio;

    private ImageButton btnAniadirImagen;

    private FirebaseHelper helper;


    private UtilsHelper utHelper;

    private String[] tiposMedidas, tiposEjercicios;

    private List<TipoEjercicioIconos> opcionesTiposEjercicios;


    private final int COD_SEL_IMAGE = 300;

    private Uri urlImagen;

    private final String RUTA_IMAGENES = "ejercicio/*";
    private final String PHOTO = "photo";

    private SharedPreferences preferences;

    private String ruta;

    private final String LAUNCH_IMAGENES = "image/*";

    private String idEjercicio;

    private boolean permisosConcedidos;

    private Dialog pantallaCarga;


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


                pedirPermisosImagenes();

                imagenGaleria();


            }
        });


    }

    private void imagenGaleria() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType(LAUNCH_IMAGENES);
        startActivityForResult(i, COD_SEL_IMAGE);
    }

    private void pedirPermisosImagenes() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i("imagenInfo", "He entrado en los permisos");
        } else {
            Log.i("imagenInfo", "No he entrado");
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    Permisos.REQUEST_CODE_PERMISSION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Permisos.REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
                permisosConcedidos = true;
                // Realizar la acción deseada aquí
            } else {
                // Permiso denegado
                Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show();
                permisosConcedidos = false;
                // Realizar alguna acción apropiada, como mostrar un mensaje al usuario o finalizar la actividad
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == COD_SEL_IMAGE) {

            urlImagen = data.getData(); //obtener la imagen seleccionada por el usuario

            //cargamos la imagen en el ImageView correspondiente
            Glide.with(this)
                    .load(urlImagen)
                    .override(ivImagenEjercicio.getWidth(), ivImagenEjercicio.getHeight())
                    .into(ivImagenEjercicio);

        }

    }


    private String getRutaImagen(String idEjercicio) {

        Log.i("IdRegistrado", preferences.getString("correoElectronico", ""));

        String idusuario = preferences.getString("IdUser", "");

        String ruta = "";

        if (!idusuario.isEmpty()) {
            ruta = RUTA_IMAGENES + PHOTO + idusuario + idEjercicio;
        }

        return ruta;
    }

    private void inicializarVariables() {
        tiposMedidas = rellenarMedidas();
        tiposEjercicios = rellenarTiposEjercicio();
        opcionesTiposEjercicios = new ArrayList<>();
        helper = new FirebaseHelper(getApplicationContext());
        preferences = getSharedPreferences("DatosInicio", MODE_PRIVATE);
        permisosConcedidos = false;
        ruta = "";
        pantallaCarga = PantallaCarga.cargarPantallaCarga(this);

        rellenarOpciones();
    }

    private void crearIU() {
        AdapterSpinnerCustom adapterTiposEjercicios = new AdapterSpinnerCustom(this.getApplicationContext(), R.layout.items_custom_spinner, opcionesTiposEjercicios);
        spinnerTipoEjercicio.setAdapter(adapterTiposEjercicios);

        AdapterSpinnerCustom adapterPrivacidades = new AdapterSpinnerCustom(this.getApplicationContext(), R.layout.items_custom_spinner, rellenarPrivacidades());
        spinnerPrivacidades.setAdapter(adapterPrivacidades);


        onClickbtnAniadirEjercicio();


    }


    private void onClickbtnAniadirEjercicio() {
        btnAniadirEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNombreEjercicio.getText().toString().isEmpty()) {
                    pantallaCarga.show();
                    crearEjercicio();
                } else {
                    Toast.makeText(CrearEjercicioActivity.this, "Completa el nombre del ejercicio", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void crearEjercicio() {
        String nombreEjercicio = etNombreEjercicio.getText().toString();
        String tipoEjercicio = txtIndexSpinner(spinnerTipoEjercicio);
        String privacidad = txtIndexSpinner(spinnerPrivacidades);
        Ejercicio ejercicio = new Ejercicio("", nombreEjercicio, tipoEjercicio, privacidad);

        helper.aniadirEjercicioFirestore(ejercicio, new FirebaseHelper.DevolverEjercicio() {
            @Override
            public void getId(String id) {
                ruta = getRutaImagen(id);
                idEjercicio = id;

                if (ivImagenEjercicio.getDrawable() != null &&
                        ivImagenEjercicio.getDrawable().getConstantState() !=
                                getResources().getDrawable(R.drawable.ic_ejercicio_predeterminado).getConstantState()) {

                    helper.subirImagen(ruta, urlImagen, new FirebaseHelper.UrlDescargada() {
                        @Override
                        public void getDowloadUrl(String urlDescarga) {
                            helper.updateEjercicio(idEjercicio, "foto", urlDescarga);

                            Toast.makeText(CrearEjercicioActivity.this, "Se ha añadido el ejercicio correctamente", Toast.LENGTH_SHORT).show();


                        }
                    });
                }

                PantallaCarga.esconderDialog(pantallaCarga);

            }
        });


    }

    private String txtIndexSpinner(Spinner spinner) {
        ArrayAdapter<TipoEjercicioIconos> tipoEjercicioAdapter = (ArrayAdapter<TipoEjercicioIconos>) spinner.getAdapter();

        int posicion = spinner.getSelectedItemPosition();

        return tipoEjercicioAdapter.getItem(posicion).getNombre();

    }

    private ArrayAdapter<String> crearAdapter(String[] items) {
        return new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, items);
    }

    private String[] rellenarMedidas() {
        return new String[]{"Segs", "Kg", "Placas"};
    }


    private String[] rellenarTiposEjercicio() {
        return new String[]{"Hipertrofia", "Fuerza", "Velocidad", "Resistencia", "Otro"};
    }

    private void enlazarComponentes() {
        spinnerTipoEjercicio = findViewById(R.id.spinnerTipoEjercicio);
        spinnerPrivacidades = findViewById(R.id.spinnerPrivacidades);
        btnAniadirEjercicio = findViewById(R.id.btnAniadirEjercicio);
        etNombreEjercicio = findViewById(R.id.etNombreEjercicio);
        ivImagenEjercicio = findViewById(R.id.ivImagenCrearEjercicio);
        btnAniadirImagen = findViewById(R.id.btnImagenEjercicio);
    }

    public void rellenarOpciones() {
        TipoEjercicioIconos opcion1 = new TipoEjercicioIconos("Resistencia", getImagenResource(1));
        opcionesTiposEjercicios.add(opcion1);

        TipoEjercicioIconos opcion2 = new TipoEjercicioIconos("Fuerza", getImagenResource(2));
        opcionesTiposEjercicios.add(opcion2);

        TipoEjercicioIconos opcion3 = new TipoEjercicioIconos("Hipertrofia", getImagenResource(3));
        opcionesTiposEjercicios.add(opcion3);

        TipoEjercicioIconos opcion4 = new TipoEjercicioIconos("Velocidad", getImagenResource(4));
        opcionesTiposEjercicios.add(opcion4);


    }

    private int getImagenResource(int id) {

        int resource = 0;
        switch (id) {
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
                Log.i("error", "No se ha encontrado la imagen que se pide");
                break;

        }

        return resource;
    }

    public List<TipoEjercicioIconos> rellenarPrivacidades() {
        List<TipoEjercicioIconos> opcionesPrivacidades = new ArrayList<TipoEjercicioIconos>();

        TipoEjercicioIconos opcion1 = new TipoEjercicioIconos("Privado", R.drawable.ic_privado);
        opcionesPrivacidades.add(opcion1);

        TipoEjercicioIconos opcion2 = new TipoEjercicioIconos("Publico", R.drawable.ic_publico);
        opcionesPrivacidades.add(opcion2);

        return opcionesPrivacidades;
    }


}