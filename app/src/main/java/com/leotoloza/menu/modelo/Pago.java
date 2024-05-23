package com.leotoloza.menu.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Pago implements Serializable
{
    private int id;
 private Contrato contrato;
 private String fecha_pago;
 private double importe;
 private String estado;
 private int numero_pago;
 private String detalle;

    public Pago(int id, Contrato contrato, String fecha_pago, double importe, String estado, int numero_pago, String detalle) {
        this.id = id;
        this.contrato = contrato;
        this.fecha_pago = fecha_pago;
        this.importe = importe;
        this.estado = estado;
        this.numero_pago = numero_pago;
        this.detalle = detalle;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumero_pago() {
        return numero_pago;
    }

    public void setNumero_pago(int numero_pago) {
        this.numero_pago = numero_pago;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", contrato=" + contrato +
                ", fecha_pago='" + fecha_pago + '\'' +
                ", importe=" + importe +
                ", estado='" + estado + '\'' +
                ", numero_pago=" + numero_pago +
                ", detalle='" + detalle + '\'' +
                '}';
    }
}
