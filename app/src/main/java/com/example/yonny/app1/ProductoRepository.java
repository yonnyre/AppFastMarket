package com.example.yonny.app1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yonny on 05/10/2017.
 */

public class ProductoRepository {
    private static final String TAG = ProductoRepository.class.getSimpleName();

    private static List<Producto> productos = new ArrayList<>();

    public static List<Producto> getProductos() {
        return productos;
    }

    private static long sequence = 1;

    public static Producto add(Producto producto){
//        producto.setId(sequence++);
        productos.add( producto);
        return producto;
    }

    public static Producto get(Long id){
        for (Producto producto : productos) {
        //    if(producto.getId().equals(id))
                return producto;
        }
        return null;
    }

    public static Producto remove(Long id){
        for(Iterator<Producto> i = productos.iterator(); i.hasNext();) {
            Producto producto = i.next();
         /*   if(producto.getId().equals(id)) {
                i.remove();
                return producto;
            }*/
        }
        return null;
    }

}
