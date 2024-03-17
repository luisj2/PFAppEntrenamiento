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
import com.example.fithealth.MainActivity;
import com.example.fithealth.R;

public class InicioSesion extends AppCompatActivity {

    //ATRIBUTOS
    TextView textView;
    EditText editTxtNombreUsuario,editTxtContrasenia;
    FitHealthDatabase bbdd;
    DaoUsuario daoUsuario;
    TablaUsuarios tablaUsuarios;

    SharedPreferences mantenerCuentaIniciada; //Comprobar si el usuario mantiene el inicio de sesion iniciado
    CheckBox boxMantenerCuentaIniciada;


    SharedPreferences.Editor editor; //editar lo que hadentro de la instacia de mantenerCuentaInicada de SharedPreferences


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);

        enlazarComponentes();

        mantenerCuentaIniciada = getSharedPreferences("DatosInicio", MODE_PRIVATE);
        //si le dio a mantener cuenta iniciada le manda a Home
        if (mantenerCuentaIniciada != null) {
            if(mantenerCuentaIniciada.getBoolean("iniciada", false)){
                moverseAMain();
            }else{  //y sino le autocompleta los campos con el ultimo usuario que intrudujo
                String ultimoNombreUsuarioUsado = mantenerCuentaIniciada.getString("nombreUsuario", "");
                String ultimaContraseniaUsada = mantenerCuentaIniciada.getString("contraseniaUsuario", "");
                if (!ultimoNombreUsuarioUsado.isEmpty()) {
                    editTxtNombreUsuario.setText(ultimoNombreUsuarioUsado);
                }

                if (!ultimaContraseniaUsada.isEmpty()) {
                    editTxtContrasenia.setText(ultimaContraseniaUsada);
                }
            }
            }



        bbdd = FitHealthDatabase.getInstance(this.getApplicationContext()); //inicializar base de datos
        daoUsuario= bbdd.daoUsuario(); //inicializar el dao usuario(necesario para modificar la base de datos)

       // deleteDatabase();
        tablaUsuarios = new TablaUsuarios(daoUsuario); //objeto con los metodos para modificar la tabla de usuarios

        // Obtener una instancia de SharedPreferences.Editor
         editor = mantenerCuentaIniciada.edit();




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
    public void iniciarSesion (View view) {
        //recuperamos los textos que ha introducido el usuario en los campos de usuario y contraseña
        String nombreUsuario = editTxtNombreUsuario.getText().toString();
        String contrasenia = editTxtContrasenia.getText().toString();

        if (tablaUsuarios.existeUsuario(nombreUsuario)) { //comprobar usuario existe

            //Aqui el usuario existe, comprobamos si  la contraseña coincide
            if (contrasenia.equals(tablaUsuarios.devolverContrasenia(nombreUsuario))) {
                Toast.makeText(this, "Usuario correcto", Toast.LENGTH_SHORT).show();

                //Comprobamos si el usuario quiere mantener la cuenta iniciada despues de iniciar ahora

                if(boxMantenerCuentaIniciada.isChecked()){ //quiere mantenerla iniciada
                    editor.putBoolean("iniciada",true);

                }else{ //no quiere mantenerla iniciada
                    editor.putBoolean("iniciada",false);
                }
                //guardamos en el preferences el nombre y la contraseña para futuros usos
                editor.putString("nombreUsuario",nombreUsuario);
                editor.putString("contraseniaUsuario",contrasenia);

                editor.commit(); //actualizamos cambios

                moverseAMain(); //nos desplazamos al activity Main

            }else{ //contraseña incorrecta
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }

        } else { //no ha encontrado al usuario
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        }
    }
    public void moverseARegistro(View view){
        Intent i = new Intent(this.getApplicationContext(), Registro.class);
        startActivity(i);
    }

    public boolean deleteDatabase() {
         return deleteDatabase("FitHealthBase");
    }
}