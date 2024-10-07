package com.example.fithealth.Model.DataClass;

import java.time.LocalDate;

public class ElementoFecha {

    private LocalDate fecha;
    private String txtFecha;

    public ElementoFecha(LocalDate fecha, String txtFecha) {
        this.fecha = fecha;
        this.txtFecha = txtFecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTxtFecha() {
        return txtFecha;
    }

    public void setTxtFecha(String txtFecha) {
        this.txtFecha = txtFecha;
    }
}
