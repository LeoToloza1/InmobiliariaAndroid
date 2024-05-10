package com.leotoloza.menu.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Contrato implements Serializable {
    private Inquilino inquilino;
    private Inmueble inmueble;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private LocalDate fecha_efectiva;
    private double monto;

    public Contrato(Inquilino inquilino, Inmueble inmueble, LocalDate fecha_inicio, LocalDate fecha_fin, LocalDate fecha_efectiva, double monto) {
        this.inquilino = inquilino;
        this.inmueble = inmueble;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.fecha_efectiva = fecha_efectiva;
        this.monto = monto;
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

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public LocalDate getFecha_efectiva() {
        return fecha_efectiva;
    }

    public void setFecha_efectiva(LocalDate fecha_efectiva) {
        this.fecha_efectiva = fecha_efectiva;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
