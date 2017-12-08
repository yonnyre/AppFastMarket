package com.example.yonny.app1;

/**
 * Created by Yonny on 06/12/2017.
 */

public class Carrito {

    String codigo_barras;
    String nombres;
    String peso;
    String imagen;
    String precio;
    String detalle;
    String estado;

    public Carrito() {
        super();
    }

    public Carrito(String codigo_barras, String nombres, String peso, String imagen, String precio, String detalle, String estado) {
        this.codigo_barras = codigo_barras;
        this.nombres = nombres;
        this.peso = peso;
        this.imagen = imagen;
        this.precio = precio;
        this.detalle = detalle;
        this.estado = estado;
    }

    public String getCodigo_barras() {
        return this.codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPeso() {
        return this.peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPrecio() {
        return this.precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDetalle() {
        return this.detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Carrito{" +
                "codigo_barras='" + codigo_barras + '\'' +
                ", nombres='" + nombres + '\'' +
                ", peso='" + peso + '\'' +
                ", imagen='" + imagen + '\'' +
                ", precio='" + precio + '\'' +
                ", detalle='" + detalle + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
