package com.example.fithealth.Firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.fithealth.Usuario.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FirebaseHelper {

    FirebaseFirestore fs;
    Context context;
    public FirebaseHelper(Context context) {
        fs = FirebaseFirestore.getInstance();
        this.context = context;
    }

    public boolean existeCorreo(String correo){

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        boolean existe;

        fs.collection("usuarios").document(correo).get().addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                future.complete(document.exists());
            }else{
                Toast.makeText(context, "Task no se ha completado", Toast.LENGTH_SHORT).show();
            }
        });

        try {
            existe = future.get();
        } catch (Exception e) {
            Toast.makeText(context, "Exception", Toast.LENGTH_SHORT).show();
        }

        return existe;
    }

    public void registrarUsuario(Usuario usuario){
        HashMap<String, Object> datosUsuario = rellenarHashMapConUsuario(usuario);

        fs.collection("usuarios").document(usuario.getEmail()).set(datosUsuario).addOnSuccessListener(aVoid -> {
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

        datosUsuario.put("Correo", usuario.getEmail());
        datosUsuario.put("NombreUsuario", usuario.getNombreUsario());
        datosUsuario.put("Contrasenia", usuario.getContrasenia());

        return datosUsuario;
    }

    public boolean contraseniaCorrecta (String correo,String contrasenia){

        boolean esCorrecta = false;

       Task <DocumentSnapshot> task =  fs.collection("usuarios").document(correo).get();

        DocumentSnapshot document = task.getResult();

        if(document.exists() && document.getString("Contrasenia").equals(contrasenia)){

            esCorrecta = true;
        }

        return esCorrecta;
    }





}
