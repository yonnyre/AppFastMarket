package com.example.yonny.app1;

/**
 * Created by Yonny on 26/10/2017.
 */

public class Producto {

    private String nombre;
    private String precio;
    private String imagen;
    private String codigo_barras;
    private String detalle;
    private String estado;

    public Producto() {
        super();
    }

    public String getCodigo_barras() {
        return codigo_barras;
    }

    public Producto(String nombre, String precio, String imagen, String codigo_barras, String detalle, String estado) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.codigo_barras = codigo_barras;
        this.detalle = detalle;
        this.estado = estado;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDetalles() {
        return detalle;
    }

    public void setDetalles(String detalle) {
        this.detalle = detalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "  nombre='" + nombre + '\'' +
                ", precio='" + precio + '\'' +
                ", imagen='" + imagen + '\'' +
                ", codigo_barras='" + codigo_barras + '\'' +
                ", detalle='" + detalle + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
