package com.example.fithealth.Usuario;

public class Usuario {

    private String id;
    private String email;
    private String nombreUsario;
    private String contrasenia;



    public Usuario(String email, String nombreUsario, String contrasenia) {
        this.email = email;
        this.nombreUsario = nombreUsario;
        this.contrasenia = contrasenia;
    }

    public Usuario(String id, String email, String nombreUsario, String contrasenia) {
        this.id = id;
        this.email = email;
        this.nombreUsario = nombreUsario;
        this.contrasenia = contrasenia;
    }

    public Usuario() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreUsario() {
        return nombreUsario;
    }

    public void setNombreUsario(String nombreUsario) {
        this.nombreUsario = nombreUsario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", nombreUsario='" + nombreUsario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }
}
