package com.example.yonny.app1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yonny on 05/10/2017.
 */

public class PerfilRepository {
    private static final String TAG = PerfilRepository.class.getSimpleName();

    private static List<Perfil> perfils = new ArrayList<>();

    public static List<Perfil> getPerfils() {
        return perfils;
    }

    private static long sequence = 1;

    public static Perfil add(Perfil perfil){
//        perfil.setId(sequence++);
        perfils.add( perfil);
        return perfil;
    }

    public static Perfil get(Long id){
        for (Perfil perfil : perfils) {
        //    if(perfil.getId().equals(id))
                return perfil;
        }
        return null;
    }

    public static Perfil remove(Long id){
        for(Iterator<Perfil> i = perfils.iterator(); i.hasNext();) {
            Perfil perfil = i.next();
         /*   if(perfil.getId().equals(id)) {
                i.remove();
                return perfil;
            }*/
        }
        return null;
    }

}
