package com.leotoloza.menu.modelo;

import java.io.Serializable;

public class Inmueble implements Serializable {
private String direccion;
private String uso;
private int tipoInmuebleid;
private TipoInmueble tipoInmueble;
private int ambientes;
private String coordenadas;
private double precio;
private Propietario propietario;
private String estado;
private String descripcion;
private String avatarUrl;

    public Inmueble(String direccion,String uso, int tipoInmuebleid, TipoInmueble tipo, int ambientes, String coordenadas, double precio, Propietario propietario, String estado, String descripcion, String avtarUrl) {
        this.direccion = direccion;
        this.uso = uso;
        this.tipoInmuebleid = tipoInmuebleid;
        this.tipoInmueble = tipo;
        this.ambientes = ambientes;
        this.coordenadas = coordenadas;
        this.precio = precio;
        this.propietario = propietario;
        this.estado = estado;
        this.descripcion = descripcion;
        this.avatarUrl = avtarUrl;
    }

    public Inmueble() {
    }

    public int getTipoInmuebleId() {
        return tipoInmuebleid;
    }

    public void setTipoInmuebleId(int tipoInmuebleid) {
        this.tipoInmuebleid = tipoInmuebleid;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public TipoInmueble getTipo() {
        return tipoInmueble;
    }

    public void setTipo(TipoInmueble tipo) {
        this.tipoInmueble = tipo;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
