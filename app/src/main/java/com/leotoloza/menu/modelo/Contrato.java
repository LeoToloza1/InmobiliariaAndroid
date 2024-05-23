package com.leotoloza.menu.modelo;

import java.io.Serializable;

public class Contrato implements Serializable {
    private int id;
    private int inquilinoid;
    private int inmuebleid;
    private String fecha_inicio;
    private String fecha_fin;
    private String fecha_efectiva;
    private double monto;
    private boolean borrado;
    private Inquilino inquilino;
    private Inmueble inmueble;

    public Contrato(int id, int inquilinoid, int inmuebleid, String fecha_inicio, String fecha_fin, String fecha_efectiva, double monto, boolean borrado, Inquilino inquilino, Inmueble inmueble) {
        this.id = id;
        this.inquilinoid = inquilinoid;
        this.inmuebleid = inmuebleid;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.fecha_efectiva = fecha_efectiva;
        this.monto = monto;
        this.borrado = borrado;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInquilinoid() {
        return inquilinoid;
    }

    public void setInquilinoid(int inquilinoid) {
        this.inquilinoid = inquilinoid;
    }

    public int getInmuebleid() {
        return inmuebleid;
    }

    public void setInmuebleid(int inmuebleid) {
        this.inmuebleid = inmuebleid;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getFecha_efectiva() {
        return fecha_efectiva;
    }

    public void setFecha_efectiva(String fecha_efectiva) {
        this.fecha_efectiva = fecha_efectiva;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", inquilinoid=" + inquilinoid +
                ", inmuebleid=" + inmuebleid +
                ", fecha_inicio='" + fecha_inicio + '\'' +
                ", fecha_fin='" + fecha_fin + '\'' +
                ", fecha_efectiva='" + fecha_efectiva + '\'' +
                ", monto=" + monto +
                ", borrado=" + borrado +
                ", inquilino=" + inquilino +
                ", inmueble=" + inmueble +
                '}';
    }
}
