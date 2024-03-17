package com.example.fithealth.BaseDeDatos;

import com.example.fithealth.Usuario.DatosUsuario;

import java.util.List;

public interface MetodosUsuario {
    List<DatosUsuario> getUsuarios();
    DatosUsuario getUsuarioForName(String nombre);
    void insertarUsuario(DatosUsuario usuario);
    void eliminarUsuario(String nombre);

    boolean existeUsuario(String nombre);
    String devolverContrasenia(String nombreUsuario);
}
