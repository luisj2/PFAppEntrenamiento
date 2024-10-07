package com.example.fithealth.Model.Firebase

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.example.fithealth.Model.DataClass.Ejercicio
import com.example.fithealth.Model.DataClass.Fecha
import com.example.fithealth.Model.DataClass.Mensaje
import com.example.fithealth.Model.DataClass.SearchExerciseData
import com.example.fithealth.Model.DataClass.Contacto
import com.example.fithealth.Model.DataClass.AuthUser
import com.example.fithealth.Model.DataClass.FirestoreUser
import com.example.fithealth.R
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthWebException
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Locale

class FirebaseHelper(var context: Context) {

    private var storage: StorageReference = FirebaseStorage.getInstance().reference
    private val COLECCION_EJERCICIOS = "Ejercicio"
    private val COLECCION_RUTINAS = "Rutinas"
    private val COLECCION_USUARIOS = "usuarios"
    private val fs: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val realtimeDatabase: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://fithealthpf-default-rtdb.europe-west1.firebasedatabase.app/")




/*







    fun registerUserInRealtimeDatabase() {
        realtimeDatabase.getReference("usuarios").child(auth.currentUser!!.uid)
            .child("solicitudes_amistad")
    }




    //EJERCICIOS
    fun aniadirEjercicioAUsuario(idUsuario: String?, idEjercicio: String?) {
        val usuario = fs.collection(COLECCION_USUARIOS).document(
            idUsuario!!
        )
        val ejercicio = fs.collection(COLECCION_EJERCICIOS).document(
            idEjercicio!!
        )
        usuario.update("Ejercicios", FieldValue.arrayUnion(ejercicio))
            .addOnSuccessListener(object : OnSuccessListener<Void?> {
                override fun onSuccess(unused: Void) {
                    Log.i("AniadirEjercicio", "Añadido con exito")
                }
            }).addOnFailureListener { Log.i("AniadirEjercicio", "Fallo al añadir el ejercicio") }
    }

    fun updateEjercicio(idEjercicio: String?, clave: String?, valor: Any?) {
        val reference = fs.collection(COLECCION_EJERCICIOS).document(
            idEjercicio!!
        )
        reference.update(clave!!, valor).addOnCompleteListener(object : OnCompleteListener<Void?> {
            override fun onComplete(task: Task<Void>) {
                if (task.isSuccessful) {
                    Log.d("Operacion", "Operacion realizada con exito")
                } else {
                    Log.e("Operacion", "Operacion fallida")
                }
            }
        })
    }

    //añadimos el ejercicio pasado por parametros a la coleccion de ejercicios de firebase firestore
    fun aniadirEjercicioFirestore(ejercicio: Ejercicio, callback: DevolverEjercicio) {
        val datosEjercicio = rellenarMapConEjercicio(ejercicio)
        fs.collection(COLECCION_EJERCICIOS).add(datosEjercicio)
            .addOnSuccessListener(object : OnSuccessListener<DocumentReference?> {
                override fun onSuccess(documentReference: DocumentReference) {
                    val idMap: Map<String, Any> = object : HashMap<String?, Any?>() {
                        init {
                            put("id", documentReference.id)
                        }
                    }
                    documentReference.update(idMap)
                    callback.getId(documentReference.id)
                }
            }).addOnFailureListener {
                Toast.makeText(
                    context,
                    "Error al añadir ejercicio",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    //rellenamos un hashmap con un obn
    private fun rellenarMapConEjercicio(ejercicio: Ejercicio): Map<String, Any> {
        val datos: MutableMap<String, Any> = HashMap()
        datos["id"] = ejercicio.id
        datos["nombreEjercicio"] = ejercicio.nombreEjercicio
        datos["tipoEjercicio"] = ejercicio.tipoEjercicio
        datos["privacidad"] = ejercicio.privacidad
        return datos
    }

    //CONSULTAS FIREBASE FIRESTORE
    //CONSULTAS DE EJERCICIOS
    private fun crearejercicioConSnapshot(snapshot: DocumentSnapshot): SearchExerciseData {
        val nombreEjercicio = snapshot.getString("nombreEjercicio")
        val imageResource = snapshot.getLong("imageResource")
        val privacidad = snapshot.getString("privacidad")
        val resourcePrivacidad = getResourcePrivacidad(privacidad)
        return SearchExerciseData(
            imageResource!!.toInt(),
            nombreEjercicio,
            resourcePrivacidad
        )
    }

    fun getIdEjercicioByDatos(
        nombreEjercicio: String?,
        privacidad: String?,
        callback: IdentificadorEjercicio
    ) {
        fs.collection(COLECCION_EJERCICIOS).whereEqualTo("nombreEjercicio", nombreEjercicio)
            .whereEqualTo("privacidad", privacidad)
            .limit(1).get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot?> {
                override fun onComplete(task: Task<QuerySnapshot>) {
                    if (task.isSuccessful) {
                        val snapshot = task.result
                        if (snapshot != null && !snapshot.isEmpty) {
                            val document = snapshot.documents[0]
                            val id = document.id
                            callback.getEjercicioId(id)
                        }
                    }
                }
            })
    }

    fun getEjerciciosData(nombreEjercicio: String?, callback: GestionEjercicios) {
        fs.collection(COLECCION_EJERCICIOS).get()
            .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot?> {
                override fun onComplete(task: Task<QuerySnapshot>) {
                    if (task.isSuccessful) {
                        val ejercicios: MutableList<SearchExerciseData> = ArrayList()
                        for (snapshot in task.result) {
                            val nombreEjercicioBusqueda = snapshot.getString("nombreEjercicio")!!
                                .trim { it <= ' ' }.lowercase(Locale.getDefault())
                            if (nombreEjercicioBusqueda.contains(nombreEjercicio!!)) {
                                val datas = crearejercicioConSnapshot(snapshot)
                                ejercicios.add(datas)
                            }
                        }
                        callback.infoEjercicios(ejercicios)
                    } else {
                        checkQueryExceptions(task.exception)
                    }
                }
            })
    }

    //CONSULTAS DE USUARIOS
    fun getUsuarios(listener: CargaUsuarios) {
        fs.collection(COLECCION_USUARIOS).get()
            .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot?> {
                override fun onComplete(task: Task<QuerySnapshot>) {
                    if (task.isSuccessful) {
                        Log.i("infoUsuariosArray", "He entrado")
                        val usuarios: MutableList<Usuario> = ArrayList()
                        for (snapshot in task.result) {
                            val usuario = getUsuarioConSnapshoot(snapshot)
                            usuarios.add(usuario)
                        }

                        //llamamos al metodo de la interfaz que pasamos por parametros para poder gestionar las operaciones asincronas
                        listener.implementacionUsuariosFirestore(usuarios)
                    } else {
                        checkQueryExceptions(task.exception)
                    }
                }
            })
    }

    fun getUsuariosPorNombre(nombre: String?, listener: CargaUsuarios) {
        fs.collection(COLECCION_USUARIOS).get()
            .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot?> {
                override fun onComplete(task: Task<QuerySnapshot>) {
                    if (task.isSuccessful) {
                        var usuario: Usuario
                        var nombreBusqueda: String
                        val usuarios: MutableList<Usuario> = ArrayList()
                        for (snapshot in task.result) {
                            nombreBusqueda = snapshot.getString("UsuarioUnico")
                            if (nombreBusqueda != null && !nombreBusqueda.isEmpty() && nombreBusqueda.contains(
                                    nombre!!
                                )
                            ) {
                                usuario = getUsuarioConSnapshoot(snapshot)
                                usuarios.add(usuario)
                            }
                        }
                        listener.implementacionUsuariosFirestore(usuarios)
                    } else {
                        checkQueryExceptions(task.exception)
                    }
                }
            })
    }


    fun idUsuarioEnPreferences(correo: String?, callback: IdUsuario) {
        fs.collection(COLECCION_USUARIOS).whereEqualTo("Correo", correo).limit(1).get()
            .addOnCompleteListener(object : OnCompleteListener<QuerySnapshot?> {
                override fun onComplete(task: Task<QuerySnapshot>) {
                    if (task.isSuccessful) {
                        Log.i("IdRegistrado", task.result.toString())
                        val preferences =
                            context.getSharedPreferences("DatosInicio", Context.MODE_PRIVATE)
                        editor = preferences.edit()
                        var id = ""
                        val querySnapshot = task.result
                        if (querySnapshot != null && !querySnapshot.isEmpty) {
                            for (snapshot in task.result) {
                                id = snapshot.getString("id")
                                Log.i("IdRegistrado", "$id es el id del usuario")
                            }
                        } else {
                            Log.i("IdRegistrado", "No se encontraron resultados")
                        }
                        callback.getUserId(id, editor)
                    } else {
                        checkQueryExceptions(task.exception)
                    }
                }
            })
    }

    private fun getUsuarioConSnapshoot(snapshot: QueryDocumentSnapshot): Usuario {
        val usuario = Usuario()
        usuario.id = snapshot.getString("id")
        usuario.contrasenia = snapshot.getString("Contrasenia")
        usuario.nombreUsario = snapshot.getString("NombreUsuario")
        usuario.hastagIdentificativo = snapshot.getString("hashtagIdentificativo")
        usuario.nombreUnico = snapshot.getString("UsuarioUnico")
        usuario.email = snapshot.getString("Correo")
        return usuario
    }

    private fun getResourcePrivacidad(privacidad: String?): Int {
        var resource = 0
        resource = if (privacidad == "Publico") {
            R.drawable.ic_publico
        } else {
            R.drawable.ic_privado
        }
        return resource
    }

    private fun checkQueryExceptions(exception: Exception?) {
        Log.i("infoUsuariosArray", exception.toString())
    }

    //STORAGE(Imagenes)
    fun subirImagen(ruta: String?, url: Uri?) {
        val reference = storage!!.child(ruta!!)
        reference.putFile(url!!)
            .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot?> {
                override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot) {
                    Toast.makeText(context, "Imagen subida correctamente", Toast.LENGTH_SHORT)
                        .show()
                }
            }).addOnFailureListener {
                Toast.makeText(
                    context,
                    "Error al subir la imagen",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun subirImagen(ruta: String?, url: Uri?, callback: UrlDescargada) {
        val reference = storage!!.child(ruta!!)
        reference.putFile(url!!)
            .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot?> {
                override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot) {
                    //Obtenemos la url web de la imagen que hemos subido
                    val uriTask = taskSnapshot.storage.downloadUrl
                    var tiempoLimite = false //comprobacion del tiempo
                    val tiempoEspera =
                        System.currentTimeMillis() + 5 * 1000 //tiempo de espera limite
                    while (!uriTask.isSuccessful && !tiempoLimite) {
                        if (System.currentTimeMillis() >= tiempoEspera) {
                            tiempoLimite = true
                        }
                        if (uriTask.isSuccessful && !tiempoLimite) {
                            uriTask.addOnSuccessListener(object : OnSuccessListener<Uri?> {
                                override fun onSuccess(uri: Uri) {
                                    Toast.makeText(
                                        context,
                                        "URL obtenida correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    callback.getDowloadUrl(uri.toString())
                                }
                            }).addOnFailureListener { e ->
                                Log.e(
                                    "ErrorImagen",
                                    "Error al obtener la URL de descarga",
                                    e
                                )
                            }
                        }
                    }
                }
            }).addOnFailureListener { Log.e("ErrorImagen", "Se ha fallado al subir la imagen") }
    }

    fun solicitudesAmistadlistener(idusuario: String?, callback: ActualizarIUSnapshoot) {
        val reference = realtimeDatabase.getReference("usuarios").child(
            idusuario!!
        ).child("solicitudes_amistad")
        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                callback.contactoAniadido(snapshot)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {
                callback.contactoEliminado(snapshot)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun comprobarSolicitudExiste(
        idRemitente: String,
        nombreUnico: String,
        callback: OnExistenciaSolicitud
    ) {
        val reference = realtimeDatabase.getReference("usuarios").child(idRemitente)
            .child("solicitudes_amistad")
        reference.get().addOnSuccessListener(object : OnSuccessListener<DataSnapshot?> {
            override fun onSuccess(snapshot: DataSnapshot) {
                val referenceAmigos =
                    realtimeDatabase.getReference("usuarios").child(idRemitente).child("amigos")
                referenceAmigos.get()
                    .addOnSuccessListener(object : OnSuccessListener<DataSnapshot?> {
                        override fun onSuccess(snapshotAmigos: DataSnapshot) {
                            var existeAmigo = false
                            for (child in snapshotAmigos.children) {
                                val key = child.key!!
                                if (key == idRemitente) {
                                    existeAmigo = true
                                    break
                                }
                            }
                            val existe =
                                !existeAmigo && !recorrerSolicitudesExiste(snapshot, nombreUnico)
                            callback.isExistRequest(existe)
                        }
                    })
            }
        })
    }

    /*
    private boolean recorrerContactosUsuario(String id,String nombre) {

        DatabaseReference reference = realtimeDatabase.getReference("usuarios").child(id).child("amigos");

        reference.get

    }
     */
    private fun recorrerSolicitudesExiste(snapshot: DataSnapshot, nombre: String): Boolean {
        var existe = false
        for (child in snapshot.children) {
            val key = child.key!!
            if (key == "nombre_usuario") {
                val value = child.getValue(String::class.java)!!
                if (value == nombre) {
                    existe = true
                    break
                }
            }
        }
        return existe
    }

    fun aniadirSolicitudAmistad(idRemitente: String?) {
        val reference = realtimeDatabase.getReference("usuarios").child(
            idRemitente!!
        ).child("solicitudes_amistad")
        reference.get().addOnCompleteListener(object : OnCompleteListener<DataSnapshot?> {
            override fun onComplete(task: Task<DataSnapshot>) {
                getNombreUsuarioActual(object : ObtenerNombreUsuarioActual {
                    override fun getUsuarioActual(nombreDestinatario: String) {
                        reference.setValue(null)
                        val key = reference.push().key
                        if (key != null) {
                            val solicitud: MutableMap<String, Any> = HashMap()
                            solicitud["nombre_usuario"] = nombreDestinatario
                            reference.child(key).setValue(solicitud)
                                .addOnCompleteListener(object : OnCompleteListener<Void?> {
                                    override fun onComplete(task: Task<Void>) {
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                context,
                                                "Solicitud enviada",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Log.e(
                                                "errorSolicitud",
                                                "error al mandar la solicitud " + task.exception
                                            )
                                        }
                                    }
                                })
                        }
                    }
                })
            }
        })
    }

    fun escuchadorAmigos(id: String?, callback: ActualizarIUSnapshoot) {
        val reference = realtimeDatabase.getReference("usuarios").child(
            id!!
        ).child("amigos")
        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                callback.contactoAniadido(snapshot)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {
                callback.contactoEliminado(snapshot)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    //Mensajeria
    fun mensajesListener(idEmisor: String, idNodoMensajes: String?, callback: RecepcionMensajes) {
        val reference =
            realtimeDatabase.getReference("usuarios").child(idEmisor).child("mensajes").child(
                idNodoMensajes!!
            )
        reference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val mensaje = Mensaje()
                val fecha = Fecha()
                var value = ""
                var key = ""
                for (child in snapshot.children) {
                    key = child.key
                    assert(key != null)
                    if (key != "fecha") {
                        value = child.getValue(String::class.java)
                        if (key == "emisor") {
                            assert(value != null)
                            mensaje.emisor = value
                            if (value == idEmisor) {
                                mensaje.tipoMensaje = "enviado"
                            } else {
                                mensaje.tipoMensaje = "recibido"
                            }
                        } else if (key == "receptor") {
                            mensaje.receptor = value
                        } else if (key == "mensaje") {
                            mensaje.mensaje = value
                        }
                    } else {
                        var hora: Int
                        var minuto: Int
                        for (fechaChild in child.children) {
                            val fechaKey = fechaChild.key
                            if (fechaKey == "hora") {
                                fecha.hora = fechaChild.getValue(Int::class.java)
                            } else if (fechaKey == "minuto") {
                                fecha.minuto = fechaChild.getValue(Int::class.java)
                            }
                        }
                        mensaje.fechaMensaje = fecha
                    }
                }
                if (mensaje.tipoMensaje == "enviado") {
                    callback.mensajeEnviado(mensaje)
                } else {
                    callback.mensajeRecibido(mensaje)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun enviarMensaje(idEmisor: String, idNodo: String?, idReceptor: String, txtMensaje: String) {
        val referenceEmisor =
            realtimeDatabase.getReference("usuarios").child(idEmisor).child("mensajes").child(
                idNodo!!
            )
        val referenceReceptor =
            realtimeDatabase.getReference("usuarios").child(idReceptor).child("mensajes").child(
                idNodo
            )
        val operacionesCompletadas = intArrayOf(0)
        val mensajeKey = referenceEmisor.push().key
        val datosMensaje = HashMap<String, Any>()
        datosMensaje["emisor"] = idEmisor
        datosMensaje["receptor"] = idReceptor
        datosMensaje["mensaje"] = txtMensaje
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nowZone = ZonedDateTime.now(ZoneId.systemDefault())
            val fechaActual = nowZone.toLocalDateTime()
            val fecha = Fecha(
                fechaActual.year,
                fechaActual.monthValue,
                fechaActual.dayOfMonth,
                fechaActual.hour,
                fechaActual.minute,
                fechaActual.second
            )
            datosMensaje["fecha"] = fecha
        }
        assert(mensajeKey != null)
        referenceEmisor.child(mensajeKey!!).setValue(datosMensaje)
            .addOnSuccessListener(object : OnSuccessListener<Void?> {
                override fun onSuccess(unused: Void) {
                    operacionesCompletadas[0]++
                    if (operacionesCompletadas[0] == 2) {
                        Toast.makeText(context, "Mensaje enviado", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        referenceReceptor.child(mensajeKey).setValue(datosMensaje)
            .addOnSuccessListener(object : OnSuccessListener<Void?> {
                override fun onSuccess(unused: Void) {
                    operacionesCompletadas[0]++
                    if (operacionesCompletadas[0] == 2) {
                        Toast.makeText(context, "Mensaje enviado", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    companion object {


        @JvmStatic
        val idUsuario: String?
            get() = auth.uid

        @JvmStatic
        fun getIdUsuarioActual(callback: IdUsuarioActual) {
            val id = auth.currentUser!!.uid
            callback.getIdUsuario(id)
        }

        @JvmStatic
        fun eliminarSolicitudAmistad(idEliminado: String?, context: Context?) {
            getIdUsuarioActual(object : IdUsuarioActual {
                override fun getIdUsuario(id: String?) {
                    val nodeRef = realtimeDatabase.getReference("usuarios").child(
                        id!!
                    ).child("solicitudes_amistad").child(idEliminado!!)
                    nodeRef.removeValue().addOnSuccessListener(object : OnSuccessListener<Void?> {
                        override fun onSuccess(unused: Void) {
                            val toast = Toast.makeText(
                                context,
                                "Solicitud eliminada correctamente",
                                Toast.LENGTH_SHORT
                            )
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    })
                }
            })
        }

        fun getNombreUsuarioActual(callback: ObtenerNombreUsuarioActual) {
            getIdUsuarioActual(object : IdUsuarioActual {
                override fun getIdUsuario(id: String?) {
                    val reference = fs.collection(COLECCION_USUARIOS).document(
                        id!!
                    )
                    reference.get()
                        .addOnSuccessListener(object : OnSuccessListener<DocumentSnapshot?> {
                            override fun onSuccess(documentSnapshot: DocumentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    val nombre = documentSnapshot.getString("UsuarioUnico")
                                    if (nombre != null && !nombre.isEmpty()) {
                                        callback.getUsuarioActual(nombre)
                                    }
                                }
                            }
                        })
                }
            })
        }

        @JvmStatic
        fun aniadirAmigoSolicitud(contacto: Contacto) {
            getIdUsuarioActual(object : IdUsuarioActual {
                override fun getIdUsuario(id: String?) {
                    val nodeRef = realtimeDatabase.getReference("usuarios").child(
                        id!!
                    ).child("solicitudes_amistad").child(contacto.id)
                    nodeRef.removeValue().addOnSuccessListener(object : OnSuccessListener<Void?> {
                        override fun onSuccess(unused: Void) {
                            agregarAmigoFirebase(contacto)
                        }
                    })
                }
            })
        }

        private fun agregarAmigoFirebase(contacto: Contacto) {
            getIdUsuarioActual(object : IdUsuarioActual {
                override fun getIdUsuario(id: String?) {
                    getNombreUsuarioActual(object : ObtenerNombreUsuarioActual {
                        override fun getUsuarioActual(nombre: String) {
                            val referenceRemitente =
                                realtimeDatabase.getReference("usuarios").child(contacto.id)
                                    .child("amigos")
                            val referenceLocal = realtimeDatabase.getReference("usuarios").child(
                                id!!
                            ).child("amigos")
                            referenceRemitente.child("nombre_usuario").setValue(nombre)
                            if (contacto.rutaImagen != null) {
                                referenceRemitente.child("imagen_perfil")
                                    .setValue(contacto.rutaImagen.toString())
                            } else {
                                referenceRemitente.child("imagen_perfil").setValue("")
                            }
                            referenceLocal.child(contacto.id).child("nombre_usuario")
                                .setValue(contacto.nombre)
                        }
                    })
                }
            })
        }
 */
}
