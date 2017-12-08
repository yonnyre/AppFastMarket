package com.example.yonny.app1;

/**
 * Created by Yonny on 27/11/2017.
 */

public class Usuario {

    String id;
    String nombres;
    String correo;

    public Usuario() {
        super();
    }

    public Usuario(String id,String nombres,String correo) {
        this.id = id;
        this.nombres=nombres;
        this.correo=correo;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return this.nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombres='" + nombres + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
