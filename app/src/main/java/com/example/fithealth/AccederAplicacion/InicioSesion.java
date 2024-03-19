package com.example.fithealth.AccederAplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fithealth.BaseDeDatos.Dao.DaoUsuario;
import com.example.fithealth.BaseDeDatos.FitHealthDatabase;
import com.example.fithealth.BaseDeDatos.TablaUsuarios;
import com.example.fithealth.MainActivity;
import com.example.fithealth.Permisos.Permisos;
import com.example.fithealth.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicBoolean;

public class InicioSesion extends AppCompatActivity {

    //ATRIBUTOS
    TextView textView;
    EditText editTxtNombreUsuario,editTxtContrasenia;
    FitHealthDatabase bbdd;
    DaoUsuario daoUsuario;
    TablaUsuarios tablaUsuarios;

    FirebaseFirestore fs;

    Permisos permisos;


    SharedPreferences mantenerCuentaIniciada; //Comprobar si el usuario mantiene el inicio de sesion iniciado
    CheckBox boxMantenerCuentaIniciada;




    SharedPreferences.Editor editor; //editar lo que hadentro de la instacia de mantenerCuentaInicada de SharedPreferences




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);

        enlazarComponentes();
        permisos = new Permisos();

        mantenerCuentaIniciada = getSharedPreferences("DatosInicio", MODE_PRIVATE);

        recuperarDatosPreferences();


        /*

        bbdd = FitHealthDatabase.getInstance(this.getApplicationContext()); //inicializar base de datos
        daoUsuario= bbdd.daoUsuario(); //inicializar el dao usuario(necesario para modificar la base de datos)

       // deleteDatabase();
        tablaUsuarios = new TablaUsuarios(daoUsuario); //objeto con los metodos para modificar la tabla de usuarios


         */
        fs = FirebaseFirestore.getInstance();
        // Obtener una instancia de SharedPreferences.Editor
         editor = mantenerCuentaIniciada.edit();




    }

    private void recuperarDatosPreferences() {
        //si le dio a mantener cuenta iniciada le manda a Home
        if (mantenerCuentaIniciada != null) {
            if(mantenerCuentaIniciada.getBoolean("iniciada", false)){
                moverseAMain();
            }else{  //y sino le autocompleta los campos con el ultimo usuario que intrudujo
                String ultimoNombreUsuarioUsado = mantenerCuentaIniciada.getString("correoElectronico", "");
                String ultimaContraseniaUsada = mantenerCuentaIniciada.getString("contraseniaUsuario", "");
                if (!ultimoNombreUsuarioUsado.isEmpty()) {
                    editTxtNombreUsuario.setText(ultimoNombreUsuarioUsado);
                }

                if (!ultimaContraseniaUsada.isEmpty()) {
                    editTxtContrasenia.setText(ultimaContraseniaUsada);
                }
            }
        }
    }

    public void enlazarComponentes(){
        textView = findViewById(R.id.textView2);
        editTxtContrasenia = findViewById(R.id.editTxtContrasenia);
        editTxtNombreUsuario = findViewById(R.id.editTxtNombreUsuario);
        boxMantenerCuentaIniciada = findViewById(R.id.boxMantenerCuentaIniciada);

    }
    public void moverseAMain(){
        Intent i = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
    public void iniciarSesion(View view) {
        if(permisos.conexionEstable(getApplicationContext())){
            // Recuperamos los textos que ha introducido el usuario en los campos de usuario y contraseña
            String correoElectronico = editTxtNombreUsuario.getText().toString();
            String contrasenia = editTxtContrasenia.getText().toString();

           //comprobamos que todos los campos esten rellenados
            if(!correoElectronico.isEmpty() && !contrasenia.isEmpty()){
                comprobacionesUsuario(correoElectronico,contrasenia);
            }else{ //campos no rellenos
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            }

        }else{ //el usuario no esta conectado a internet
            Toast.makeText(this, "Conectate a internet para iniciar sesion", Toast.LENGTH_SHORT).show();
        }


    }




    public void contraseniaCorrecta(String correoElectronico, String contrasenia) {
        AtomicBoolean isCorrecta = new AtomicBoolean(false);

        //accedemos a documetn del correo electronico que se pasa por parametros
        fs.collection("usuarios").document(correoElectronico).get().addOnSuccessListener(document -> {


            if (document.exists()) {
                if(document.getString("Contrasenia").equals(contrasenia)) {
                    isCorrecta.set(true);
                    contraseniaCorrectaVerificacion(isCorrecta.get(),correoElectronico,contrasenia);
                }else{
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
            }



        }).addOnFailureListener(e -> {

           Log.e("ErrorContrasenia","Ha habido un error al introducir la contrasenia");
        });

    }

    public void moverseARegistro(View view){
        Intent i = new Intent(this.getApplicationContext(), Registro.class);
        startActivity(i);
    }

    public boolean deleteDatabase() {
         return deleteDatabase("FitHealthBase");
    }

    public void comprobacionesUsuario (String correo,String contrasenia){
        AtomicBoolean existeUsuario = new AtomicBoolean(false); //variable para comrobar que el usuario existe

        //accedemos a la coleccion de usuarios de firebase al decument del correo que me pasa por parametros
        fs.collection("usuarios").document(correo).get().addOnCompleteListener(it -> {
            DocumentSnapshot document = it.getResult(); //recuperamos el documento del correo que le estamos pasando al usuario

            //si el documento existe significa que en la base de datos esta el correo que se han pasado por parametros
            if(document != null && document.exists()){
                existeUsuario.set(true);

            }
            //llamamos aqui a este metodo para verificar si el usuario existe por que es una operacion asincrona y si no lo llamamos aqui puede fallar
           existeUsuarioVerificacion(existeUsuario.get(),correo,contrasenia);

        });
    }

    private void entrarEnAplicacion(String correoElectronico,String contrasenia) {
        Toast.makeText(this, "Usuario correcto", Toast.LENGTH_SHORT).show();

        // Comprobamos si el usuario quiere mantener la cuenta iniciada después de iniciar sesión
        if (boxMantenerCuentaIniciada.isChecked()) { // Quiere mantenerla iniciada
            editor.putBoolean("iniciada", true);
        } else { // el ususairo no quiere mantenerla iniciada
            editor.putBoolean("iniciada", false);
        }

        // Guardamos en el preferences el correo y la contraseña para futuros usos
        editor.putString("correoElectronico", correoElectronico);
        editor.putString("contraseniaUsuario", contrasenia);
        editor.commit(); // Actualizamos cambios

        moverseAMain(); // Nos desplazamos al activity Main
    }



    public void existeUsuarioVerificacion(boolean existe, String correoElectronico, String contrasenia) {
        if(existe){ //si el usuario existe comprobamos la contraseña
            contraseniaCorrecta(correoElectronico,contrasenia);
        }else{// el usuario no existe
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        }
    }


    public void contraseniaCorrectaVerificacion(boolean contraseniaCorrecta, String correoElectronico, String contrasenia) {

        if(contraseniaCorrecta){
            entrarEnAplicacion(correoElectronico,contrasenia);
        }else{
            Toast.makeText(this, "Contrasenia incorrecta", Toast.LENGTH_SHORT).show();
        }
    }
}