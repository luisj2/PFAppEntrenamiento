package com.example.fithealth.Model.DataClass;

public class Mensaje {
    private String emisor;
    private String receptor;

    private String mensaje;

    private Fecha fechaMensaje;

    private String tipoMensaje;

    public Mensaje(String emisor, String receptor, String mensaje) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.mensaje = mensaje;
    }

    public Fecha getFechaMensaje() {
        return fechaMensaje;
    }

    public void setFechaMensaje(Fecha fechaMensaje) {
        this.fechaMensaje = fechaMensaje;
    }

    public Mensaje() {
    }

    public String getEmisor() {
        return emisor;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


}
