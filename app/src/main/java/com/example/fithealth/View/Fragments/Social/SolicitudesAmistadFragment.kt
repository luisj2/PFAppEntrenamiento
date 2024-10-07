package com.example.fithealth.View.Fragments.Social

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.Contacto
import com.example.fithealth.Model.Firebase.FirebaseHelper
import com.example.fithealth.R
import com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.ListaAmigosAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SolicitudesAmistadFragment : Fragment() {
    private var helper: FirebaseHelper? = null
    private var rvSolicitudesAmistad: RecyclerView? = null
    private var adapter: ListaAmigosAdapter? = null
    private var solicitudes: MutableList<Contacto>? = null

    //private SqliteHelper sqliteHelper;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*
        adapter = ListaAmigosAdapter(solicitudes)
        rvSolicitudesAmistad!!.layoutManager = LinearLayoutManager(activity)
        rvSolicitudesAmistad!!.adapter = adapter
        FirebaseHelper.getIdUsuarioActual(object : IdUsuarioActual() {
            fun getIdUsuario(id: String?) {
                helper.solicitudesAmistadlistener(id, object : ActualizarIUSnapshoot() {
                    fun contactoAniadido(solicitudSnapshoot: DataSnapshot) {
                        val idSolicitante = solicitudSnapshoot.key
                        var nombre: String? = ""
                        var imagenPerfil: Uri? = null
                        for (childSolicitudSnapshoot in solicitudSnapshoot.children) {
                            val key = childSolicitudSnapshoot.key
                            val value = childSolicitudSnapshoot.getValue(
                                String::class.java
                            )
                            assert(key != null)
                            if (key == "nombre_usuario") {
                                nombre = value
                            } else if (key == "ruta_imagen") {
                                try {
                                    imagenPerfil = Uri.parse(value)
                                } catch (e: Exception) {
                                    imagenPerfil = null
                                    Log.e("ErrorImagen", "Error al parsear la imagen")
                                }
                            }
                        }
                        val contacto = Contacto(idSolicitante!!, nombre!!, imagenPerfil)
                        if (!contactoExiste(idSolicitante)) {
                            solicitudes!!.add(contacto)
                            sqliteHelper.aniadirContacto(contacto)
                            adapter!!.setSolicitudesAmistad(solicitudes)
                            adapter!!.notifyItemChanged(solicitudes!!.size - 1)
                        }
                    }

                    fun contactoEliminado(snapshot: DataSnapshot) {
                        for (childSnapshoot in snapshot.children) {
                            childSnapshoot.ref.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot2: DataSnapshot) {
                                    //String nombre = snapshot.child("nombre_usuario").getValue(String.class);
                                    val id = snapshot.key
                                    for (i in solicitudes!!.indices) {
                                        val (id1) = solicitudes!![i]
                                        if (id1 == id) {
                                            sqliteHelper.deleteContacto(id)
                                            solicitudes!!.removeAt(i)
                                            adapter!!.setSolicitudesAmistad(solicitudes)
                                            adapter!!.notifyItemRemoved(i)
                                            break
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {}
                            })
                        }
                    }
                })
            }
        })

         */
        return inflater.inflate(R.layout.fragment_solicitudes_amistad, container, false)
    }

    private fun contactoExiste(idSolicitante: String?): Boolean {
        var existe = false
        for ((id) in solicitudes!!) {
            if (id == idSolicitante) {
                existe = true
                break
            }
        }
        return existe
    }

    private fun enlazarComponentes(view: View) {
        rvSolicitudesAmistad = view.findViewById(R.id.rvSolicitudesAmistad)
    }

}