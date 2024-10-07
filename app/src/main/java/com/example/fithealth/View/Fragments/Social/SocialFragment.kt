package com.example.fithealth.View.Fragments.Social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.fithealth.R

class SocialFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


       // onClickBtnBuscar()
        return inflater.inflate(R.layout.fragment_social, container, false)
    }

    //OnClick del boton de buscar donde
    /*
    private fun onClickBtnBuscar() {
        btnBuscar!!.setOnClickListener { consultaBusqueda() }
    }

    private fun enlazarComponentes(view: View?) {
        btnBuscar = view!!.findViewById<Button>(R.id.btnBuscarUsuarioSocial)
        svBarraBusqueda = view.findViewById<SearchView>(R.id.barraBusquedaSocial)
        rvSocialUsuarios = view.findViewById<RecyclerView>(R.id.rvListaUsuarios)
    }

    //Realizamos una consulta en Firebase y la  mostramos en el RecyclerView con el adaptador
    private fun consultaBusqueda() {
        //Obtenemos lo que ha escrito el usuario en la barra de busqueda
        val nombreBusqueda = svBarraBusqueda!!.query.toString()
        helper.getUsuariosPorNombre(nombreBusqueda, object : CargaUsuarios() {
            fun implementacionUsuariosFirestore(usuarios: List<Usuario>) {
                crearAdapterBusquedaSocial(usuarios)
            }
        })
    }

    private fun crearAdapterBusquedaSocial(usuarios: List<Usuario>) {
        adapter = SocialAdapter(usuarios, helper)
        rvSocialUsuarios.setLayoutManager(LinearLayoutManager(requireContext()))
        rvSocialUsuarios.setAdapter(adapter)
    }
     */
}