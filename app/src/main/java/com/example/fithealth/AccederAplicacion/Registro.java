package com.example.fithealth.AccederAplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fithealth.BaseDeDatos.Dao.DaoUsuario;
import com.example.fithealth.BaseDeDatos.FitHealthDatabase;
import com.example.fithealth.BaseDeDatos.TablaUsuarios;
import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.Permisos.Permisos;
import com.example.fithealth.R;
import com.example.fithealth.Usuario.DatosUsuario;
import com.example.fithealth.Usuario.Usuario;
import com.example.fithealth.UtilsHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {


    FitHealthDatabase bbdd;
    DaoUsuario daoUsuario;
    TablaUsuarios tablaUsuarios;
    TextInputEditText editTxtNuevaContrasenia, editTxtNuevoNombre, editTxtNuevoCorreo;

    FirebaseFirestore fs;

    FirebaseAuth auth;

    FirebaseHelper helper;

    Permisos permisos;

    UtilsHelper utHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        enlazarComponentes();

        inicializarVariables();

        //bbdd = FitHealthDatabase.getInstance(this.getApplicationContext()); //inicializar base de datos
        // daoUsuario= bbdd.daoUsuario(); //inicializar el dao usuario(necesario para modificar la base de datos)

        //tablaUsuarios = new TablaUsuarios(daoUsuario); //objeto con los metodos para modificar la tabla de usuarios

        FirebaseApp.initializeApp(Registro.this);


    }

    //inicializar las variables del inicio
    private void inicializarVariables() {
        permisos = new Permisos();
        utHelper = new UtilsHelper();
        auth = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();
        helper = new FirebaseHelper(getApplicationContext());
    }

    //inicializar los componentes
    public void enlazarComponentes() {
        editTxtNuevoNombre = findViewById(R.id.editTxtNuevoNombreUsuario);
        editTxtNuevaContrasenia = findViewById(R.id.editNuevoTxtContrasenia);
        editTxtNuevoCorreo = findViewById(R.id.editTxtNuevoCorreo);
    }

    //intent hacia inicio sesion
    public void volverAInicioSesion(View view) {
        Intent i = new Intent(this.getApplicationContext(), InicioSesion.class);
        startActivity(i);
    }

    //Insertar a un nuevo usuario en Firestore
    public void registrarse(View view) {

        //comprobamos que el usuario tenga conexion estable
        if (permisos.conexionEstable(getApplicationContext())) {
            // Recuperamos los textos que ha introducido el usuario en los campos
            String nuevoNombre = utHelper.getTxtTextView(editTxtNuevoNombre);
            String nuevaContrasenia = utHelper.getTxtTextView(editTxtNuevaContrasenia);
            String email = utHelper.getTxtTextView(editTxtNuevoCorreo);


            // Si no están vacíos, entra al if
            if (!nuevoNombre.isEmpty() && !nuevaContrasenia.isEmpty() && !email.isEmpty()) { //¿campos vacios?
                if (helper.credencialesCorreoValidas(email)) { //comprobacion de correo
                    if (helper.credencialesUsuarioValidas(nuevoNombre)) { //comprobacion de nombre de usuario

                        //Comprobamos la existencia del usuario haciendo una consulta de firebase y segun el resultado
                        //llamamos al metodo de la interfaz onExistenciaUsuario con el resultado de l aexistencia del usuario
                       helper.existeUsuario(email, new FirebaseHelper.OnExistenciaUsuarioListener() {
                           @Override
                           public void onExistenciaUsuario(boolean existe) {
                               if(existe){
                                   Toast.makeText(Registro.this, "Ese usuario ya existe", Toast.LENGTH_SHORT).show();
                               }else{
                                   helper.registrarUsuario(new Usuario(email,nuevoNombre,nuevaContrasenia));
                               }
                           }
                       });


                    } else { //usuario no valido
                        Toast.makeText(this, "Nombre no válido", Toast.LENGTH_SHORT).show();
                        editTxtNuevoNombre.setText("");
                    }

                } else { // correo no valido
                    Toast.makeText(this, "Email no válido", Toast.LENGTH_SHORT).show();
                    editTxtNuevoCorreo.setText("");
                }

            } else { // Algún campo está vacío
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        } else { //no tiene conexion
            Toast.makeText(this, "Comprueba tu conexion", Toast.LENGTH_SHORT).show();
        }

    }//fun de registrarse

} // fin de la clase