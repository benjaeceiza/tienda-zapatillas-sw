package com.mycompany.tienda.zapatillas.model;

public class Usuario {
    
    // Atributos privados (Espejo exacto de tu base de datos)
    private int idUsuario;
    private String nombre;
    private String email;
    private String password;
    private String rol;

    // 1. Constructor vacío 
    public Usuario() {
    }

    // 2. Constructor completo 
    public Usuario(int idUsuario, String nombre, String email, String password, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    // --- GETTERS Y SETTERS ---

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}