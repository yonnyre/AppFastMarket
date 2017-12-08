package com.example.yonny.app1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Yonny on 26/10/2017.
 */

public interface ApiService {
    String API_BASE_URL = "https://fast-shop-jrgflores.c9users.io";

    @GET("/ofertas")
    retrofit2.Call<List<Producto>> getProductos();

    @GET("/carritos/"+"106731546773836059151")
    Call<List<Carrito>> setCarritos();

    @GET("/facturas")
    retrofit2.Call<List<Factura>> getFacturas();

    @POST("/filtrarProductos")
    @FormUrlEncoded
    retrofit2.Call<Producto> savePost(@Field("codigo_barras") String codigo_barras);


    @GET("/ofertas/{codigo_barras}")
    Call<Producto> showProducto(@Path("codigo_barras") String codigo_barras);

    @DELETE("/carritos/{codigo_barras}")
    Call<ResponseMessage> destroyProducto(@Path("codigo_barras") String codigo_barras);

    //@GET("/carritos/{id_usuario}")
    //Call<Perfil> showCarrito(@Path("id_usuario") String id_usuario);


}
