package com.example.fithealth.Ejercicios;

public class Ejercicio {


private String id;

private String nombreEjercicio;

private Long imageResource;

private String tipoEjercicio;

private String privacidad;

    public Ejercicio(String id, String nombreEjercicio, Long imageResource, String tipoEjercicio, String privacidad) {
        this.id = id;
        this.nombreEjercicio = nombreEjercicio;
        this.imageResource = imageResource;
        this.tipoEjercicio = tipoEjercicio;
        this.privacidad = privacidad;
    }

    public Ejercicio(String nombreEjercicio, Long imageResource, String privacidad) {
        this.nombreEjercicio = nombreEjercicio;
        this.imageResource = imageResource;
        this.privacidad = privacidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public Long getImageResource() {
        return imageResource;
    }

    public void setImageResource(Long imageResource) {
        this.imageResource = imageResource;
    }

    public String getTipoEjercicio() {
        return tipoEjercicio;
    }

    public void setTipoEjercicio(String tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }

    public String getPrivacidad() {
        return privacidad;
    }

    public void setPrivacidad(String privacidad) {
        this.privacidad = privacidad;
    }
}
