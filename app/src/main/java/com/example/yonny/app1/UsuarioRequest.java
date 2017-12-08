package com.example.yonny.app1;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yonny on 27/11/2017.
 */

public class UsuarioRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="https://fast-shop-jrgflores.c9users.io/login";
    private Map<String ,String> params;


    public UsuarioRequest(String id_usuario,String nombres,String correo ,Response.Listener<String>listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("id_usuario",id_usuario);
        params.put("nombres",nombres);
        params.put("correo",correo);

    }



    @Override
    public Map<String ,String> getParams(){
        return params;
    }

}
