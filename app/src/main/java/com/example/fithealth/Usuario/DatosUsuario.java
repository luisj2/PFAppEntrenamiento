package com.example.fithealth.Usuario;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuario")
public class DatosUsuario {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nombre")
    public String nombreUsuario;

    @ColumnInfo(name = "contrasenia")
    @NonNull
    public String contrasenia;

    public DatosUsuario(@NonNull String nombreUsuario, @NonNull String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public DatosUsuario() {
    }

    @NonNull
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(@NonNull String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @NonNull
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(@NonNull String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return "DatosUsuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }
}
