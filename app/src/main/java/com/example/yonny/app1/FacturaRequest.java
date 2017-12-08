package com.example.yonny.app1;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yonny on 27/11/2017.
 */

public class FacturaRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="https://fast-shop-jrgflores.c9users.io/facturas";
    private Map<String ,String> params;


    public FacturaRequest(String idfactura, String fecha,String monto ,String peso, String id_usuario ,Response.Listener<String>listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params=new HashMap<>();

        params.put("idfactura",idfactura);
        params.put("fecha",fecha);
        params.put("monto",monto);
        params.put("peso",peso);
        params.put("id_usuario",id_usuario);


    }

    @Override
    public Map<String ,String> getParams(){
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/x-www-form-urlencoded");
        return params;
    }

}
