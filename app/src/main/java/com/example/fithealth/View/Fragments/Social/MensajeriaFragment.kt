package com.example.fithealth.View.Fragments.Social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.Contacto
import com.example.fithealth.Model.Firebase.FirebaseHelper
import com.example.fithealth.R
import com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.BusquedaSocial.ContactosAdapter

class MensajeriaFragment : Fragment() {
    private var rvContactos: RecyclerView? = null
    private var amigos: MutableList<Contacto>? = null
   // private var sqlHelper: SqliteHelper? = null
    private var helper: FirebaseHelper? = null
    private var adapter: ContactosAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mensajeria, container, false)
        /*
        adapter = ContactosAdapter(amigos)
        rvContactos!!.layoutManager = LinearLayoutManager(requireContext())
        rvContactos!!.adapter = adapter
        amigosListener()
         */
        return view
    }



    /*
    private fun amigosListener() {
        getIdUsuarioActual(object : IdUsuarioActual() {
            fun getIdUsuario(id: String?) {
                helper!!.escuchadorAmigos(id, object : ActualizarIUSnapshoot() {
                    fun contactoAniadido(snapshot: DataSnapshot) {
                        aniadirAmigoIU(snapshot)
                    }

                    fun contactoEliminado(snapshot: DataSnapshot) {
                        eliminarAmigoIU(snapshot)
                    }
                })
            }
        })
    }

    private fun eliminarAmigoIU(snapshot: DataSnapshot) {
        val (id) = obtenerContactoDeSnapshoot(snapshot)
        for (i in amigos!!.indices) {
            val (id1) = amigos!![i]
            if (id1 == id) {
                amigos!!.removeAt(i)
                sqlHelper!!.eliminarAmigo(id1)
                adapter!!.setContactos(amigos)
                adapter!!.notifyItemChanged(i)
                break
            }
        }
    }

    private fun aniadirAmigoIU(snapshot: DataSnapshot) {
        val contacto = obtenerContactoDeSnapshoot(snapshot)
        if (!amigoExiste(contacto)) {
            amigos!!.add(contacto)
            sqlHelper!!.aniadirAmigo(contacto)
            adapter!!.setContactos(amigos)
            adapter!!.notifyItemChanged(amigos!!.size - 1)
        }
    }

    private fun amigoExiste(contacto: Contacto): Boolean {
        var existe = false
        for ((id) in amigos!!) {
            if (contacto.id == id) {
                existe = true
                break
            }
        }
        return existe
    }

    private fun obtenerContactoDeSnapshoot(snapshot: DataSnapshot): Contacto {
        val idAmigo = snapshot.key
        var nombre: String? = ""
        var imagenPerfil: Uri? = null
        for (childSnapshot in snapshot.children) {
            val key = childSnapshot.key
            val value = childSnapshot.getValue(String::class.java)
            if (key == "nombre_usuario") {
                nombre = value
            } else if (key == "imagen_perfil") {
                try {
                    imagenPerfil = Uri.parse(value)
                } catch (e: Exception) {
                    imagenPerfil = null
                    Log.e("ErrorAmigos", "Fallo al parsear la imagen de usuario")
                }
            }
        }
        return Contacto(idAmigo!!, nombre!!, imagenPerfil)
    }
     */
}