package com.example.yonny.app1;

/**
 * Created by Yonny on 05/10/2017.
 */

public class Perfil {
    String nombre;
    String precio;
    String detalle;

    public Perfil() {
        super();
    }

    public Perfil(String nombre, String precio, String detalle) {
        this.nombre = nombre;
        this.precio = precio;
        this.detalle = detalle;
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

    @Override
    public String toString() {
        return "Perfil{" +
                "nombre='" + nombre + '\'' +
                ", precio='" + precio + '\'' +
                ", detalle='" + detalle + '\'' +
                '}';
    }
}
