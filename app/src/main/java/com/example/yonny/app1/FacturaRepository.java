package com.example.yonny.app1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jorge on 27/11/2017.
 */

public class FacturaRepository {
    private static final String TAG = FacturaRepository.class.getSimpleName();

    private static List<Factura> facturas = new ArrayList<>();

    public static List<Factura> getFacturas() {
        return facturas;
    }

    private static long sequence = 1;

    public static Factura add(Factura factura){
        //factura.setIdfactura(sequence++);
        facturas.add(factura);

        return factura;
    }

    public static Factura get(Long id){
        for (Factura factura : facturas) {
              if(factura.getIdfactura().equals(id))
            return factura;
        }
        return null;
    }

    public static Factura remove(Factura id){
        for(Iterator<Factura> i = facturas.iterator(); i.hasNext();) {
            Factura factura = i.next();
            if(factura.getIdfactura().equals(id)) {
                i.remove();
                return factura;
            }
        }
        return null;
    }

}
