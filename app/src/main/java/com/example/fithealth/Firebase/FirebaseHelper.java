package com.example.fithealth.Firebase;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fithealth.AccederAplicacion.InicioSesion;
import com.example.fithealth.PantallaCarga;
import com.example.fithealth.PantallasPrincipales.principales.Entrenamiento.BuscarEjercicioData;
import com.example.fithealth.Ejercicios.Ejercicio;
import com.example.fithealth.PantallasPrincipales.principales.Social.Contacto;
import com.example.fithealth.PantallasPrincipales.principales.Social.ListaAmigosAdapter;
import com.example.fithealth.R;
import com.example.fithealth.Usuario.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.Document;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FirebaseHelper {

    FirebaseFirestore fs;
    static FirebaseAuth auth;

    private static FirebaseDatabase realtimeDatabase;

    StorageReference storage;
    Context context;

    InicioSesion sesion;

    SharedPreferences preferences;

    SharedPreferences.Editor editor;

    final String COLECCION_USUARIOS = "usuarios";
    final String COLECCION_EJERCICIOS = "Ejercicio";

    final String COLECCION_RUTINAS = "Rutinas";


    public FirebaseHelper(Context context) {
        fs = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance().getReference();
        realtimeDatabase = FirebaseDatabase.getInstance("https://fithealthpf-default-rtdb.europe-west1.firebasedatabase.app/");
        this.context = context;
    }

    public FirebaseHelper(Context context, InicioSesion sesion) {
        this.sesion = sesion;
        fs = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        realtimeDatabase = FirebaseDatabase.getInstance("https://fithealthpf-default-rtdb.europe-west1.firebasedatabase.app/");
        this.context = context;
    }

    //INTERFACES USADAS PARA CONSULTAS
    public interface CargaUsuarios {
        void implementacionUsuariosFirestore(List<Usuario> usuarios);
    }

    public interface OnExistenciaUsuarioListener {
        void onExistenciaUsuario(boolean existe);
    }

    public interface IdUsuario {
        void getUserId(String id, SharedPreferences.Editor editor);
    }

    public interface GestionEjercicios {
        void infoEjercicios(List<BuscarEjercicioData> ejercicios);
    }

    public interface IdentificadorEjercicio {
        void getEjercicioId(String id);
    }

    public interface DevolverEjercicio {
        void getId(String id);
    }

    public interface UrlDescargada {
        void getDowloadUrl(String urlDescarga);
    }

    public interface IdUsuarioActual {
        void getIdUsuario(String id);
    }

    public interface ActualizarIUSnapshoot {
        void contactoAniadido(DataSnapshot snapshot);

        void contactoEliminado(DataSnapshot snapshot);
    }

    public interface ObtenerContactosAmigos {
        void getAmigos(List <Contacto> contactos);

    }


    //REGISTRO DE USUARIO

    //crear un usuario en firebase auth
    public void registrarUsuario(Usuario usuario) {

        Dialog dialog = PantallaCarga.cargarPantallaCarga(context);
        dialog.show();
        auth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getContrasenia()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (firebaseUser != null) {
                        rellenarConUsuarioFirebase(firebaseUser, usuario);
                    } else {
                        Log.i("Registro", "Usuario nulo");
                    }
                    PantallaCarga.esconderDialog(dialog);
                } else {
                    PantallaCarga.esconderDialog(dialog);
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

    //crear modificar el usuario con el objeto de firebseUser
    private void rellenarConUsuarioFirebase(FirebaseUser firebaseUser, Usuario usuario) {
        usuario.setId(firebaseUser.getUid());
        usuario.setHastagIdentificativo(usuario.getId().substring(0, 6));
        usuario.setNombreUnico(usuario.getNombreUsario() + " #" + usuario.getHastagIdentificativo());
        registrarUsuarioFirestore(usuario);
    }

    //registrar el usuasrio con los datos de parametros a la coleccion de usuarios de firebase firestore
    private void registrarUsuarioFirestore(Usuario usuario) {
        HashMap<String, Object> datosUsuario = rellenarHashMapConUsuario(usuario);


        fs.collection(COLECCION_USUARIOS).document(usuario.getId()).set(datosUsuario).addOnSuccessListener(aVoid -> {
                    // Éxito en la operación
                    DatabaseReference reference = realtimeDatabase.getReference("usuarios").child(usuario.getId()).child("solicitudes_amistad");

                    Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();


                })
                .addOnFailureListener(e -> {
                    // Manejar el error
                    Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                });
    }

    //convertir los datos del objeto de usuarios en un hashnmap
    private HashMap<String, Object> rellenarHashMapConUsuario(Usuario usuario) {
        HashMap<String, Object> datosUsuario = new HashMap<>();

        datosUsuario.put("id", usuario.getId());
        datosUsuario.put("UsuarioUnico", usuario.getNombreUnico());
        datosUsuario.put("hashtagIdentificativo", usuario.getHastagIdentificativo());
        datosUsuario.put("Correo", usuario.getEmail());
        datosUsuario.put("NombreUsuario", usuario.getNombreUsario());
        datosUsuario.put("Contrasenia", usuario.getContrasenia());

        return datosUsuario;
    }

    //metodo de inicio sesion a un usuario previamente registrado con el objeto Auth de firebase
    public void iniciarSesion(String correo, String contrasenia) {

        if (sesion != null) {
            auth.signInWithEmailAndPassword(correo, contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {


                        sesion.entrarEnAplicacion(correo, contrasenia);
                    } else {
                        comprobarErrorInicioSesion(task.getException());

                    }

                }
            });
        }

    }

    //comprobaciones de exceptions en el inicio sesion para dar informacion al usuario de por que no se puede meter en la aplicacion
    private void comprobarErrorInicioSesion(Exception error) {
        if (error instanceof FirebaseAuthWeakPasswordException) { //error de contrasenia debil
            Toast.makeText(context, "Contraseña debil o no valida", Toast.LENGTH_SHORT).show();
        } else if (error instanceof FirebaseAuthWebException) { //error en la conexion
            Toast.makeText(context, "Error en la red", Toast.LENGTH_SHORT).show();
        } else if (error instanceof FirebaseAuthInvalidUserException) { //error de correo no registrado
            Toast.makeText(context, "El correo electronico no esta registrado", Toast.LENGTH_SHORT).show();

        } else if (error instanceof FirebaseFirestoreException) {
            Toast.makeText(context, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
        } else if (error instanceof FirebaseAuthInvalidCredentialsException) { //error de probablemente la contraaseña
            Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        } else if (error instanceof FirebaseTooManyRequestsException) {
            Toast.makeText(context, "Demasiados intentos intentalo mas tarde", Toast.LENGTH_SHORT).show();
        } else { //cualquier otro error
            Log.e("ErrorFirebase", "Error inesperado " + error);
            Toast.makeText(context, "Error inesperado", Toast.LENGTH_SHORT).show();
        }
    }

    //comprobaciones de errores para el registrro
    private void comprobarErrorRegistro(Exception error) {
        if (error instanceof FirebaseAuthWeakPasswordException) { //error de contrasenia debil
            Toast.makeText(context, "Contraseña debil o no valida", Toast.LENGTH_SHORT).show();
        } else if (error instanceof FirebaseAuthWebException) { //error en la conexion
            Toast.makeText(context, "Error en la red", Toast.LENGTH_SHORT).show();
        } else if (error instanceof FirebaseAuthUserCollisionException) { //error de correo ya esta registrado
            Toast.makeText(context, "El correo electronico no esta registrado", Toast.LENGTH_SHORT).show();

        } else if (error instanceof FirebaseAuthInvalidCredentialsException) { //error de probablemente la contraaseña
            Toast.makeText(context, "comprueba que el correo y la contraseña sean correctas", Toast.LENGTH_SHORT).show();
        } else { //cualquier otro error
            Log.e("ErrorFirebase", "Error inesperado " + error);
            Toast.makeText(context, "Error inesperado", Toast.LENGTH_SHORT).show();
        }
    }

    //FUNCIONALIDADES EXTRA A FIREBASE

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

    //EJERCICIOS


    public void aniadirEjercicioAUsuario(String idUsuario, String idEjercicio) {
        DocumentReference usuario = fs.collection(COLECCION_USUARIOS).document(idUsuario);

        DocumentReference ejercicio = fs.collection(COLECCION_EJERCICIOS).document(idEjercicio);

        usuario.update("Ejercicios", FieldValue.arrayUnion(ejercicio)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("AniadirEjercicio", "Añadido con exito");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("AniadirEjercicio", "Fallo al añadir el ejercicio");
            }
        });
    }

    public void updateEjercicio(String idEjercicio, String clave, Object valor) {

        DocumentReference reference = fs.collection(COLECCION_EJERCICIOS).document(idEjercicio);

        reference.update(clave, valor).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("Operacion", "Operacion realizada con exito");
                } else {
                    Log.e("Operacion", "Operacion fallida");
                }
            }
        });
    }

    //añadimos el ejercicio pasado por parametros a la coleccion de ejercicios de firebase firestore
    public void aniadirEjercicioFirestore(Ejercicio ejercicio, DevolverEjercicio callback) {

        Map<String, Object> datosEjercicio = rellenarMapConEjercicio(ejercicio);

        fs.collection(COLECCION_EJERCICIOS).add(datosEjercicio).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Map<String, Object> idMap = new HashMap<String, Object>() {{
                    put("id", documentReference.getId());
                }};
                documentReference.update(idMap);
                callback.getId(documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error al añadir ejercicio", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //rellenamos un hashmap con un obn
    private Map<String, Object> rellenarMapConEjercicio(Ejercicio ejercicio) {
        Map<String, Object> datos = new HashMap<>();

        datos.put("id", ejercicio.getId());
        datos.put("nombreEjercicio", ejercicio.getNombreEjercicio());
        datos.put("tipoEjercicio", ejercicio.getTipoEjercicio());
        datos.put("privacidad", ejercicio.getPrivacidad());

        return datos;
    }


    //CONSULTAS FIREBASE FIRESTORE


    //CONSULTAS DE EJERCICIOS

    private BuscarEjercicioData crearejercicioConSnapshot(DocumentSnapshot snapshot) {
        String nombreEjercicio = snapshot.getString("nombreEjercicio");
        Long imageResource = snapshot.getLong("imageResource");
        String privacidad = snapshot.getString("privacidad");

        int resourcePrivacidad = getResourcePrivacidad(privacidad);

        return new BuscarEjercicioData(imageResource.intValue(), nombreEjercicio, resourcePrivacidad);
    }

    public void getIdEjercicioByDatos(String nombreEjercicio, String privacidad, IdentificadorEjercicio callback) {

        fs.collection(COLECCION_EJERCICIOS).whereEqualTo("nombreEjercicio", nombreEjercicio)
                .whereEqualTo("privacidad", privacidad)
                .limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            if (snapshot != null && !snapshot.isEmpty()) {
                                DocumentSnapshot document = snapshot.getDocuments().get(0);

                                String id = document.getId();

                                callback.getEjercicioId(id);


                            }
                        }
                    }
                });
    }

    public void getEjerciciosData(String nombreEjercicio, GestionEjercicios callback) {

        fs.collection(COLECCION_EJERCICIOS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    List<BuscarEjercicioData> ejercicios = new ArrayList<>();

                    for (DocumentSnapshot snapshot : task.getResult()) {
                        String nombreEjercicioBusqueda = snapshot.getString("nombreEjercicio").trim().toLowerCase();

                        if (nombreEjercicioBusqueda.contains(nombreEjercicio)) {
                            BuscarEjercicioData datas = crearejercicioConSnapshot(snapshot);
                            ejercicios.add(datas);
                        }
                    }

                    callback.infoEjercicios(ejercicios);

                } else {

                    comprobarErroresConsultas(task.getException());
                }
            }
        });
    }


    //CONSULTAS DE USUARIOS
    public void getUsuarios(CargaUsuarios listener) {

        fs.collection(COLECCION_USUARIOS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    Log.i("infoUsuariosArray", "He entrado");
                    List<Usuario> usuarios = new ArrayList<Usuario>();

                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        Usuario usuario = getUsuarioConSnapshoot(snapshot);
                        usuarios.add(usuario);
                    }

                    //llamamos al metodo de la interfaz que pasamos por parametros para poder gestionar las operaciones asincronas
                    listener.implementacionUsuariosFirestore(usuarios);


                } else {
                    comprobarErroresConsultas(task.getException());
                }


            }
        });

    }

    public static void getIdUsuarioActual(IdUsuarioActual callback) {

        String id = auth.getCurrentUser().getUid();

        callback.getIdUsuario(id);

    }

    public void getUsuariosPorNombre(String nombre, CargaUsuarios listener) {

        fs.collection(COLECCION_USUARIOS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Usuario usuario;
                    String nombreBusqueda;
                    List<Usuario> usuarios = new ArrayList<>();
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        nombreBusqueda = snapshot.getString("UsuarioUnico");

                        if (nombreBusqueda != null && !nombreBusqueda.isEmpty() && nombreBusqueda.contains(nombre)) {
                            usuario = getUsuarioConSnapshoot(snapshot);
                            usuarios.add(usuario);
                        }
                    }

                    listener.implementacionUsuariosFirestore(usuarios);

                } else {
                    comprobarErroresConsultas(task.getException());
                }


            }
        });
    }

    public void existeUsuario(String correo, OnExistenciaUsuarioListener listener) {
        CollectionReference reference = fs.collection(COLECCION_USUARIOS);
        reference.whereEqualTo("Correo", correo).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshot = task.getResult();
                        if (snapshot != null && !snapshot.isEmpty()) {
                            listener.onExistenciaUsuario(true);
                        } else {
                            listener.onExistenciaUsuario(false);
                        }
                    } else {
                        comprobarErroresConsultas(task.getException());
                    }
                });
    }

    public void idUsuarioEnPreferences(String correo, IdUsuario callback) {


        fs.collection(COLECCION_USUARIOS).whereEqualTo("Correo", correo).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    Log.i("IdRegistrado", String.valueOf(task.getResult()));
                    SharedPreferences preferences = context.getSharedPreferences("DatosInicio", MODE_PRIVATE);
                    editor = preferences.edit();
                    String id = "";

                    QuerySnapshot querySnapshot = task.getResult();

                    if (querySnapshot != null && !querySnapshot.isEmpty()) {

                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            id = snapshot.getString("id");
                            Log.i("IdRegistrado", id + " es el id del usuario");
                        }
                    } else {
                        Log.i("IdRegistrado", "No se encontraron resultados");
                    }

                    callback.getUserId(id, editor);


                } else {
                    comprobarErroresConsultas(task.getException());
                }

            }
        });
    }


    private Usuario getUsuarioConSnapshoot(QueryDocumentSnapshot snapshot) {
        Usuario usuario = new Usuario();
        usuario.setId(snapshot.getString("id"));
        usuario.setContrasenia(snapshot.getString("Contrasenia"));
        usuario.setNombreUsario(snapshot.getString("NombreUsuario"));
        usuario.setHastagIdentificativo(snapshot.getString("hashtagIdentificativo"));
        usuario.setNombreUnico(snapshot.getString("UsuarioUnico"));
        usuario.setEmail(snapshot.getString("Correo"));

        return usuario;
    }


    private int getResourcePrivacidad(String privacidad) {
        int resource = 0;
        if (privacidad.equals("Publico")) {
            resource = R.drawable.ic_publico;
        } else {
            resource = R.drawable.ic_privado;
        }

        return resource;
    }

    private void comprobarErroresConsultas(Exception exception) {

        Log.i("infoUsuariosArray", exception.toString());


    }

    //STORAGE(Imagenes)

    public void subirImagen(String ruta, Uri url) {
        StorageReference reference = storage.child(ruta);

        reference.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Imagen subida correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void subirImagen(String ruta, Uri url, UrlDescargada callback) {
        StorageReference reference = storage.child(ruta);

        reference.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Obtenemos la url web de la imagen que hemos subido
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                boolean tiempoLimite = false; //comprobacion del tiempo
                long tiempoEspera = System.currentTimeMillis() + (5 * 1000); //tiempo de espera limite

                while (!uriTask.isSuccessful() && !tiempoLimite) {
                    if (System.currentTimeMillis() >= tiempoEspera) {
                        tiempoLimite = true;
                    }
                    if (uriTask.isSuccessful() && !tiempoLimite) {
                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(context, "URL obtenida correctamente", Toast.LENGTH_SHORT).show();
                                callback.getDowloadUrl(uri.toString());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("ErrorImagen", "Error al obtener la URL de descarga", e);
                            }
                        });
                    }

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("ErrorImagen", "Se ha fallado al subir la imagen");
            }
        });
    }

    public void solicitudesAmistadlistener(String idusuario, ActualizarIUSnapshoot callback) {
        DatabaseReference reference = realtimeDatabase.getReference("usuarios").child(idusuario).child("solicitudes_amistad");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                callback.contactoAniadido(snapshot);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                callback.contactoEliminado(snapshot);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public static void eliminarSolicitudAmistad(String idEliminado, Context context) {
        getIdUsuarioActual(new IdUsuarioActual() {
            @Override
            public void getIdUsuario(String id) {
                DatabaseReference nodeRef = realtimeDatabase.getReference("usuarios").child(id).child("solicitudes_amistad").child(idEliminado);

                nodeRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast toast = Toast.makeText(context, "Solicitud eliminada correctamente", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });


            }
        });

    }



    public void aniadirSolicitudAmistad(String idRemitente, String nombreDestinatario) {

        DatabaseReference reference = realtimeDatabase.getReference("usuarios").child(idRemitente).child("solicitudes_amistad");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                reference.setValue(null);
                String key = reference.push().getKey();

                if (key != null) {
                    Map<String, Object> solicitud = new HashMap<>();
                    solicitud.put("nombre_usuario", nombreDestinatario);

                    reference.child(key).setValue(solicitud)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "Solicitud enviada", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("errorSolicitud", "error al mandar la solicitud " + task.getException());
                                    }
                                }
                            });
                }
            }
        });

    }

    public static void aniadirAmigoSolicitud(Contacto contacto) {

        getIdUsuarioActual(new IdUsuarioActual() {
            @Override
            public void getIdUsuario(String id) {
                DatabaseReference nodeRef = realtimeDatabase.getReference("usuarios").child(id).child("solicitudes_amistad").child(contacto.getId());

                nodeRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        agregarAmigoFirebase(contacto);
                    }
                });


            }
        });

    }

    private static void agregarAmigoFirebase(Contacto contacto){
        DatabaseReference reference = realtimeDatabase.getReference("usuarios").child(contacto.getId()).child("amigos");

        reference.child("nombre_usuario").setValue(contacto.getNombre());
        if(contacto.getRutaImagen() != null){
            reference.child("imagen_perfil").setValue(contacto.getRutaImagen().toString());
        }else{
            reference.child("imagen_perfil").setValue("");
        }


    }

    public void escuchadorAmigos (String id,ActualizarIUSnapshoot callback){

        DatabaseReference reference = realtimeDatabase.getReference("usuarios").child(id).child("amigos");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                callback.contactoAniadido(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                callback.contactoEliminado(snapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
