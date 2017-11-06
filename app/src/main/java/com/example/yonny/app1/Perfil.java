package com.example.yonny.app1;

/**
 * Created by Yonny on 05/10/2017.
 */

public class Perfil {
    String nombre;

    public Perfil() {
        super();
    }

    public Perfil(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
