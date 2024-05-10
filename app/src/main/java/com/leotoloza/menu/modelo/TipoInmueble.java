package com.leotoloza.menu.modelo;

import java.io.Serializable;

public class TipoInmueble implements Serializable
{
    private String tipo;
    private int borrado;

    public TipoInmueble(String tipo, int borrado) {
        this.tipo = tipo;
        this.borrado = borrado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getBorrado() {
        return borrado;
    }

    public void setBorrado(int borrado) {
        this.borrado = borrado;
    }
}
