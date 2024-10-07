package com.example.fithealth.Model.BaseDeDatos;

import com.example.fithealth.Model.BaseDeDatos.Dao.DaoUsuario;
import com.example.fithealth.Model.BaseDeDatos.Tables.Usuario.DatosUsuario;

import java.util.List;

public class TablaUsuarios implements MetodosUsuario{
    DaoUsuario dao;
    List<DatosUsuario> usuarios;
    DatosUsuario usuario;

    boolean existe;
    String contrasenia;



    public TablaUsuarios(DaoUsuario daoUsuario) {
        this.dao = daoUsuario;
    }


    @Override
    public List<DatosUsuario> getUsuarios(){

        usuarios = null;
      Thread t = new Thread(new Runnable() {
          @Override
          public void run() {
              usuarios = dao.getUsuarios();
          }
      });
      t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return usuarios;

    }

    @Override
    public DatosUsuario getUsuarioForName(String nombre) {
        usuario = null;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                usuario = dao.getUsuarioForName(nombre);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    @Override
    public void insertarUsuario(DatosUsuario usuario) {

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    dao.insertarUsuario(usuario);
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

    }

    @Override
    public void eliminarUsuario(String nombre) {

    }

    @Override
    public boolean existeUsuario(String nombre) {
        existe = false;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               if(dao.getUsuarioForName(nombre) != null){
                   existe = true;
               }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return existe;
    }

    @Override
    public String devolverContrasenia(String nombreUsuario) {
        contrasenia = "";
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                DatosUsuario usuario = dao.getUsuarioForName(nombreUsuario);
                contrasenia = usuario.getContrasenia();
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return contrasenia;
    }
}
