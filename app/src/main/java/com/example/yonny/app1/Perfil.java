package com.example.yonny.app1;

/**
 * Created by Yonny on 05/10/2017.
 */

public class Perfil {
    String nombre;
    String precio;
    String detalle;
    String codigo_barras;
    String imagen;

    public Perfil() {
        super();
    }

    public Perfil(String nombre, String precio, String detalle,String imagen,String coidgo_barras) {
        this.nombre = nombre;
        this.precio = precio;
        this.detalle = detalle;
        this.codigo_barras=codigo_barras;
        this.imagen = imagen;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCodigo_barras() {
        return this.codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "nombre='" + nombre + '\'' +
                ", precio='" + precio + '\'' +
                ", detalle='" + detalle + '\'' +
                ", codigo_barras='" + codigo_barras + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
