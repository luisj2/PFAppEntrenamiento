package com.example.fithealth.AccederAplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fithealth.BaseDeDatos.Dao.DaoUsuario;
import com.example.fithealth.BaseDeDatos.FitHealthDatabase;
import com.example.fithealth.BaseDeDatos.TablaUsuarios;
import com.example.fithealth.Firebase.FirebaseHelper;
import com.example.fithealth.Activitys.ActivityPrincipal;
import com.example.fithealth.Permisos.Permisos;
import com.example.fithealth.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class InicioSesion extends AppCompatActivity {

    //ATRIBUTOS
    TextView textView;
    EditText editTxtNombreUsuario,editTxtContrasenia;
    FitHealthDatabase bbdd;
    DaoUsuario daoUsuario;
    TablaUsuarios tablaUsuarios;

    FirebaseFirestore fs;

    FirebaseHelper helper;

    Permisos permisos;

    SharedPreferences mantenerCuentaIniciada; //Comprobar si el usuario mantiene el inicio de sesion iniciado
    CheckBox boxMantenerCuentaIniciada;

    SharedPreferences.Editor editor; //editar lo que hadentro de la instacia de mantenerCuentaInicada de SharedPreferences


    public InicioSesion() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);

        enlazarComponentes();

        inicializarVariables();

        mantenerCuentaIniciada = getSharedPreferences("DatosInicio", MODE_PRIVATE);

        recuperarDatosPreferences();


        /*

        bbdd = FitHealthDatabase.getInstance(this.getApplicationContext()); //inicializar base de datos
        daoUsuario= bbdd.daoUsuario(); //inicializar el dao usuario(necesario para modificar la base de datos)

       // deleteDatabase();
        tablaUsuarios = new TablaUsuarios(daoUsuario); //objeto con los metodos para modificar la tabla de usuarios


         */

        // Obtener una instancia de SharedPreferences.Editor
         editor = mantenerCuentaIniciada.edit();




    }

    private void inicializarVariables() {
        permisos = new Permisos();
        fs = FirebaseFirestore.getInstance();
        helper = new FirebaseHelper(getApplicationContext(),this);
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
        Intent i = new Intent(this.getApplicationContext(), ActivityPrincipal.class);
        startActivity(i);
    }
    public void iniciarSesion(View view) {
        //comprobamos si la conexion es estable
        if(permisos.conexionEstable(getApplicationContext())){
            // Recuperamos los textos que ha introducido el usuario en los campos de usuario y contraseña
            String correoElectronico = editTxtNombreUsuario.getText().toString().trim();
            String contrasenia = editTxtContrasenia.getText().toString().trim();

           //comprobamos que todos los campos esten rellenados
            if(!correoElectronico.isEmpty() && !contrasenia.isEmpty()){

                if(helper.credencialesCorreoValidas(correoElectronico)){
                    //Iniciamos sesion en Firebase
                    helper.iniciarSesion(correoElectronico,contrasenia);
                }else{
                    Toast.makeText(this, "Credenciales del correo invalidas", Toast.LENGTH_SHORT).show();
                }
            }else{ //campos no rellenos
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            }

        }else{ //el usuario no esta conectado a internet
            Toast.makeText(this, "Conectate a internet para iniciar sesion", Toast.LENGTH_SHORT).show();
        }




    }








    public void moverseARegistro(View view){
        Intent i = new Intent(this.getApplicationContext(), Registro.class);
        startActivity(i);
    }

    public boolean deleteDatabase() {
         return deleteDatabase("FitHealthBase");
    }



    public void entrarEnAplicacion(String correoElectronico,String contrasenia) {
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







}