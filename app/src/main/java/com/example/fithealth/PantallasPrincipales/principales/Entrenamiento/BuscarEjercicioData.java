package com.example.fithealth.PantallasPrincipales.principales.Entrenamiento;

public class BuscarEjercicioData {

   private  int imagenEjercicio;

    private String nombreEjercicio;

    private int imagenPrivacidad;

    public BuscarEjercicioData(int imagenEjercicio, String nombreEjercicio, int imagenPrivacidad) {
        this.imagenEjercicio = imagenEjercicio;
        this.nombreEjercicio = nombreEjercicio;
        this.imagenPrivacidad = imagenPrivacidad;
    }

    public int getImagenEjercicio() {
        return imagenEjercicio;
    }

    public void setImagenEjercicio(int imagenEjercicio) {
        this.imagenEjercicio = imagenEjercicio;
    }

    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public int getImagenPrivacidad() {
        return imagenPrivacidad;
    }

    public void setImagenPrivacidad(int imagenPrivacidad) {
        this.imagenPrivacidad = imagenPrivacidad;
    }
}
