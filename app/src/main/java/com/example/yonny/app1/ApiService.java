package com.example.yonny.app1;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by Yonny on 26/10/2017.
 */

public interface ApiService {
    String API_BASE_URL = "https://fast-shop-jrgflores.c9users.io";

    @GET("/ofertas")
    retrofit2.Call<List<Producto>> getProductos();


    @POST("/filtrarProductos")
    @FormUrlEncoded
    retrofit2.Call<Producto> savePost(@Field("codigo_barras") String codigo_barras);


}
