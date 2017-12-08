package com.example.yonny.app1;

/**
 * Created by Yonny on 27/11/2017.
 */

public class DetalleFac {

    String codigo_barras;
    String nombre;
    String monto ;
    String peso ;

    public DetalleFac() {
        super();
    }

    public DetalleFac(String codigo_barras, String nombre, String monto, String peso) {
        this.codigo_barras = codigo_barras;
        this.nombre = nombre;
        this.monto = monto;
        this.peso = peso;
    }

    public String getCodigo_barras() {
        return this.codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "DetalleFac{" +
                "codigo_barras='" + codigo_barras + '\'' +
                ", nombre='" + nombre + '\'' +
                ", monto='" + monto + '\'' +
                ", peso='" + peso + '\'' +
                '}';
    }
}
