package com.example.fithealth.PantallasPrincipales.principales.Social;

import android.net.Uri;

public class Contacto {

    private String id;
    private String nombre;
    private Uri rutaImagen;

    public Contacto(String id, String nombre, Uri rutaImagen) {
        this.id = id;
        this.nombre = nombre;
        this.rutaImagen = rutaImagen;
    }

    public Contacto() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Uri getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(Uri rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
