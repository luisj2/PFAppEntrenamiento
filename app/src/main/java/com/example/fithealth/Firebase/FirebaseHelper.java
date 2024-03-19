package com.example.fithealth.Firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fithealth.Usuario.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


public class FirebaseHelper {

    FirebaseFirestore fs;
    FirebaseAuth auth;
    Context context;


    public FirebaseHelper(Context context) {
        fs = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        this.context = context;
    }



    public void registrarUsuario(Usuario usuario){
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








}
