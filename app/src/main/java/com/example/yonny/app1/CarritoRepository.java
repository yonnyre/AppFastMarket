package com.example.yonny.app1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorge on 27/11/2017.
 */

public class CarritoRepository {
    private static final String TAG = CarritoRepository.class.getSimpleName();

    private static List<Carrito> carritos = new ArrayList<>();

    public static List<Carrito> getCarritos() {
        return carritos;
    }

    private static long sequence = 1;

    public static Carrito add(Carrito carrito){
        //carrito.setIdcarrito(sequence++);
        carritos.add(carrito);

        return carrito;
    }

    public static Carrito get(Long id){
        for (Carrito carrito : carritos) {
          //    if(carrito.getIdcarrito().equals(id))
            return carrito;
        }
        return null;
    }
        /*
    public static Carrito remove(Carrito id){
        for(Iterator<Carrito> i = carritos.iterator(); i.hasNext();) {
            Carrito carrito = i.next();
            if(carrito.getIdcarrito().equals(id)) {
                i.remove();
                return carrito;
            }
        }
        return null;
    }
          */
}
