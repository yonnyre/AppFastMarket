package com.example.yonny.app1;

/**
 * Created by Yonny on 27/11/2017.
 */

public class Factura {

    String idfactura;
    String detalle;
    String fecha;
    String monto ;
    String peso ;
    String id_usuario;

    public Factura() {
        super();
    }

    public Factura(String idfactura, String detalle, String fecha, String monto, String peso, String id_usuario) {
        this.idfactura = idfactura;
        this.detalle = detalle;
        this.fecha = fecha;
        this.monto = monto;
        this.peso = peso;
        this.id_usuario = id_usuario;
    }

    public String getIdfactura() {
        return this.idfactura;
    }

    public void setIdfactura(String idfactura) {
        this.idfactura = idfactura;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMonto() {
        return this.monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getPeso() {
        return this.peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getId_usuario() {
        return this.id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "idfactura='" + idfactura + '\'' +
                ", detalle='" + detalle + '\'' +
                ", fecha='" + fecha + '\'' +
                ", monto='" + monto + '\'' +
                ", peso='" + peso + '\'' +
                ", id_usuario='" + id_usuario + '\'' +
                '}';
    }
}
