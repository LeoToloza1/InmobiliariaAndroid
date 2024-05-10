package com.leotoloza.menu.modelo;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;
public class Propietario implements Serializable {
    private String nombre;
    private String apellido;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    private long telefono;

    public Propietario(String nombre, String apellido, String email, String password, long telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
    }

    public Propietario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }
}
