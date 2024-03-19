package com.example.fithealth.Firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fithealth.AccederAplicacion.InicioSesion;
import com.example.fithealth.Usuario.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class FirebaseHelper {

    FirebaseFirestore fs;
    FirebaseAuth auth;
    Context context;

    InicioSesion sesion;


    public FirebaseHelper(Context context) {
        fs = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public FirebaseHelper(Context context,InicioSesion sesion) {
        this.sesion = sesion;
        fs = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        this.context = context;
    }





    public void registrarUsuario(Usuario usuario){
        if(sesion != null){
            auth.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getContrasenia()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if(task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();



                        if(user != null){
                            usuario.setId(user.getUid());
                        }else{
                            Log.i("Registro","Usuario nulo");
                        }

                        if(!usuario.getId().isEmpty()){

                            registrarUsuarioFirestore(usuario);
                        }
                    }else{
                        Log.i("Registro","Error al registrar el usuario");
                    }



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(context, "Error, comprueba que la contrasenia tenga al menos 6 caractereres", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }



    private void registrarUsuarioFirestore(Usuario usuario){
        HashMap<String, Object> datosUsuario = rellenarHashMapConUsuario(usuario);



        fs.collection("usuarios").document(usuario.getId()).set(datosUsuario).addOnSuccessListener(aVoid -> {
                    // Éxito en la operación
                    Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Manejar el error
                    Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                });
    }
    private HashMap<String, Object> rellenarHashMapConUsuario(Usuario usuario) {
        HashMap<String, Object> datosUsuario = new HashMap<>();

        datosUsuario.put("id",usuario.getId());
        datosUsuario.put("Correo", usuario.getEmail());
        datosUsuario.put("NombreUsuario", usuario.getNombreUsario());
        datosUsuario.put("Contrasenia", usuario.getContrasenia());

        return datosUsuario;
    }

    public void iniciarSesion (String correo,String contrasenia){
        
        if(sesion != null){
            auth.signInWithEmailAndPassword(correo,contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        sesion.entrarEnAplicacion(correo,contrasenia);
                    }else{
                        Toast.makeText(context, "Error inesperado comprueba la contrasenia", Toast.LENGTH_SHORT).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            });
        }
    
    }

    public void existeUsuarioVerificacion(boolean existe, String correoElectronico, String contrasenia) {
        if(existe){ //si el usuario existe comprobamos la contraseña
            contraseniaCorrecta(correoElectronico,contrasenia);
        }else{// el usuario no existe
            Toast.makeText(context, "El usuario no existe", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(context, "El usuario no existe", Toast.LENGTH_SHORT).show();
            }



        }).addOnFailureListener(e -> {

            Log.e("ErrorContrasenia","Ha habido un error al introducir la contrasenia");
        });

    }

    public void contraseniaCorrectaVerificacion(boolean contraseniaCorrecta, String correoElectronico, String contrasenia) {

        if(contraseniaCorrecta){
            sesion.entrarEnAplicacion(correoElectronico,contrasenia);
        }else{
            Toast.makeText(context, "Contrasenia incorrecta", Toast.LENGTH_SHORT).show();
        }
    }










}
