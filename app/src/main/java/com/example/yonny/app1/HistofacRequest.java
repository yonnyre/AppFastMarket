package com.example.yonny.app1;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yonny on 27/11/2017.
 */

public class HistofacRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL="https://fast-shop-jrgflores.c9users.io/detallefact";
    private Map<String ,String> params;


    public HistofacRequest(String num_factura , Response.Listener<String>listener){
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params=new HashMap<>();

        params.put("num_factura",num_factura);



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
