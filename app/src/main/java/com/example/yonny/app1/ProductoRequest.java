package com.example.yonny.app1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Yonny on 11/11/2017.
 */

public class ProductoRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="https://fast-shop-jrgflores.c9users.io/filtrarProductos";
    private Map<String ,String> params;
    public ProductoRequest(String codigo_barras,String id_usuario, Response.Listener<String>listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("codigo_barras",codigo_barras);
        params.put("id_usuario",id_usuario);

    }

    @Override
    public Map<String ,String> getParams(){
        return params;
    }


}
