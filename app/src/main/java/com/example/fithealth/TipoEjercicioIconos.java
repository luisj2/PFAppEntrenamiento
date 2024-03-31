package com.example.fithealth;



public class TipoEjercicioIconos {


    private String nombre;

    private int imagen;

    public TipoEjercicioIconos(String nombre,int imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }




    public int getImagen() {
        return imagen;
    }




    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }


}
