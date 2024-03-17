package com.example.fithealth.BaseDeDatos.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.fithealth.Usuario.DatosUsuario;

import java.util.List;

@Dao
public interface DaoUsuario {

    @Query("SELECT * FROM usuario")  List<DatosUsuario> getUsuarios();


    @Query("SELECT * FROM usuario WHERE nombre = :nombre")  DatosUsuario getUsuarioForName(String nombre);


    @Insert  void insertarUsuario(DatosUsuario usuario);


    @Query("DELETE FROM usuario WHERE nombre = :nombre")  void eliminarUsuario(String nombre);





 }
