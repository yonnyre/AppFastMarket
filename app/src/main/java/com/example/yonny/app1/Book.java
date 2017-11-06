package com.example.yonny.app1;

import com.orm.SugarRecord;

/**
 * Created by Yonny on 16/10/2017.
 */
public class Book extends SugarRecord {
    String nombre;

    public Book(){
    }

    public Book(String nombre){
        this.nombre = nombre;
    }

}