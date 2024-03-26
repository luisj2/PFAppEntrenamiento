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
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseAuthWebException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

        auth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getContrasenia()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (firebaseUser != null) {
                        rellenarConUsuarioFirebase(firebaseUser,usuario);
                    } else {
                        Log.i("Registro", "Usuario nulo");
                    }
                } else {
                   comprobarErrorRegistro(task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error comprueba que la cuenta no exista o que la contraseña contenga al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rellenarConUsuarioFirebase(FirebaseUser firebaseUser, Usuario usuario) {
        usuario.setId(firebaseUser.getUid());
        usuario.setHastagIdentificativo(usuario.getId().substring(0,5).toUpperCase());
        usuario.setNombreUnico(usuario.getNombreUsario() + " #" + usuario.getHastagIdentificativo());
        registrarUsuarioFirestore(usuario);
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
        datosUsuario.put("UsuarioUnico",usuario.getNombreUnico());
        datosUsuario.put("hashtagIdentificativo",usuario.getHastagIdentificativo());
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
                        comprobarErrorInicioSesion(task.getException());

                    }

                }
            });
        }
    
    }

    private void comprobarErrorInicioSesion(Exception error) {
        if(error instanceof FirebaseAuthWeakPasswordException){ //error de contrasenia debil
            Toast.makeText(context, "Contraseña debil o no valida", Toast.LENGTH_SHORT).show();
        }
        else if(error instanceof FirebaseAuthWebException){ //error en la conexion
            Toast.makeText(context, "Error en la red", Toast.LENGTH_SHORT).show();
        }
        else if(error instanceof FirebaseAuthInvalidUserException){ //error de correo no registrado
            Toast.makeText(context, "El correo electronico no esta registrado", Toast.LENGTH_SHORT).show();

        }
        else  if(error instanceof FirebaseAuthInvalidCredentialsException) { //error de probablemente la contraaseña
            Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }
        else if(error instanceof FirebaseTooManyRequestsException){
            Toast.makeText(context, "Demasiados intentos intentalo mas tarde", Toast.LENGTH_SHORT).show();
        }

        else { //cualquier otro error
            Log.e("ErrorFirebase","Error inesperado "+error);
            Toast.makeText(context, "Error inesperado", Toast.LENGTH_SHORT).show();
        }
    }

    private void comprobarErrorRegistro(Exception error) {
        if(error instanceof FirebaseAuthWeakPasswordException){ //error de contrasenia debil
            Toast.makeText(context, "Contraseña debil o no valida", Toast.LENGTH_SHORT).show();
        }
        else if(error instanceof FirebaseAuthWebException){ //error en la conexion
            Toast.makeText(context, "Error en la red", Toast.LENGTH_SHORT).show();
        }
        else if(error instanceof FirebaseAuthUserCollisionException){ //error de correo ya esta registrado
            Toast.makeText(context, "El correo electronico no esta registrado", Toast.LENGTH_SHORT).show();

        }
        else  if(error instanceof FirebaseAuthInvalidCredentialsException) { //error de probablemente la contraaseña
            Toast.makeText(context, "comprueba que el correo y la contraseña sean correctas", Toast.LENGTH_SHORT).show();
        }

        else { //cualquier otro error
            Log.e("ErrorFirebase","Error inesperado "+error);
            Toast.makeText(context, "Error inesperado", Toast.LENGTH_SHORT).show();
        }
    }

    //Comprobaer las credenciales del correo
    public boolean credencialesCorreoValidas(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Comprobar las credenciales del usuario
    public boolean credencialesUsuarioValidas(String nombreUsuario) {
        String usernameRegex = "^[a-zA-Z0-9_]+$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(nombreUsuario);
        return matcher.matches();
    }


}
