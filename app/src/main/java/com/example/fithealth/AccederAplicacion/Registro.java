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
import com.example.fithealth.Permisos.Permisos;
import com.example.fithealth.R;
import com.example.fithealth.Usuario.DatosUsuario;
import com.example.fithealth.Usuario.Usuario;
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

    Permisos permisos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        enlazarComponentes();
        permisos = new Permisos();

        //bbdd = FitHealthDatabase.getInstance(this.getApplicationContext()); //inicializar base de datos
        // daoUsuario= bbdd.daoUsuario(); //inicializar el dao usuario(necesario para modificar la base de datos)

        //tablaUsuarios = new TablaUsuarios(daoUsuario); //objeto con los metodos para modificar la tabla de usuarios


        FirebaseApp.initializeApp(Registro.this);


    }

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

    //Insertar a un nuevo usuario en la base de datos
    public void registrarse(View view) {

        if(permisos.conexionEstable(getApplicationContext())){
            // Recuperamos los textos que ha introducido el usuario en los campos
            String nuevoNombre = editTxtNuevoNombre.getText().toString();
            String nuevaContrasenia = editTxtNuevaContrasenia.getText().toString();
            String email = editTxtNuevoCorreo.getText().toString();

            //Map<String,Object> datosUsuario = new HashMap<>();



            fs = FirebaseFirestore.getInstance();

            //auth = FirebaseAuth.getInstance();

            // Si no están vacíos, entra al if
            if (!nuevoNombre.isEmpty() && !nuevaContrasenia.isEmpty() && !email.isEmpty()) { //¿campos vacios?
                if (esEmailValido(email)) { //comprobacion de correo
                    if (esUsuarioValido(nuevoNombre)) { //comprobacion de nombre de usuario

                        //hacemos una consulta a los datos: con el get accedemos a los decumentos de la coleccion
                        //y con el addoncompleteListener maneja los datos recibidos y con el objeto task tratar con ellos
                        fs.collection("usuarios").get().addOnCompleteListener( task ->{

                            Map<String,Object> datosUsuario = null;
                            boolean usuarioExiste = false;

                            if(task.isSuccessful()){


                                //recorre cada documento que hay en la base de datos, es decir la informacion de cada usuaario
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    datosUsuario = document.getData();


                                    if(datosUsuario.get("Correo").toString().equals(email)){
                                        Toast.makeText(this, "Ese correo ya existe", Toast.LENGTH_SHORT).show();
                                        usuarioExiste = true;
                                    }

                                }

                                if(!usuarioExiste){
                                    Usuario usuario = new Usuario(email, nuevoNombre, nuevaContrasenia);
                                    registrarNuevoUsuario(usuario);
                                }
                            }else{
                                Log.e("FirebaseError","Error al cargar los datos de fierebase");
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
        }else{ //no tiene conexion
            Toast.makeText(this, "Comprueba tu conexion", Toast.LENGTH_SHORT).show();
        }

    }

    public void registrarNuevoUsuario(Usuario usuario) {

        HashMap<String, Object> datosUsuario = rellenarHashMapConUsuario(usuario);

        fs.collection("usuarios").document(usuario.getEmail()).set(datosUsuario).addOnSuccessListener(aVoid -> {
                    // Éxito en la operación
                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Manejar el error
                    Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                });
        ;
    }


    public boolean esEmailValido(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Método para validar un nombre de usuario normal (permite letras, números y guiones bajos)
    public boolean esUsuarioValido(String nombreUsuario) {
        String usernameRegex = "^[a-zA-Z0-9_]+$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(nombreUsuario);
        return matcher.matches();
    }


    public HashMap<String, Object> rellenarHashMapConUsuario(Usuario usuario) {
        HashMap<String, Object> datosUsuario = new HashMap<>();

        datosUsuario.put("Correo", usuario.getEmail());
        datosUsuario.put("NombreUsuario", usuario.getNombreUsario());
        datosUsuario.put("Contrasenia", usuario.getContrasenia());

        return datosUsuario;
    }

}