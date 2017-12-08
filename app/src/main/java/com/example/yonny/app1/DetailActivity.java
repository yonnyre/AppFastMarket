package com.example.yonny.app1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();


    private TextView nombreTxt;
    private TextView detalleTxt;
    private TextView precioTxt;
    private FragmentManager fragmentManager;

    private ImageView fotoImage;
    private Button button;
    private TextView codigobarras;
    private String idUsuario;
    private Button agregarCarrito;
    String codigo_ba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nombreTxt = (TextView) findViewById(R.id.nombre_text);
        detalleTxt = (TextView) findViewById(R.id.detalles_text);
        codigobarras = (TextView) findViewById(R.id.codigoProducto);
        precioTxt = (TextView) findViewById(R.id.precio_text);
        fotoImage=(ImageView)findViewById(R.id.foto_image);
        agregarCarrito=(Button)findViewById(R.id.agregaracarrito);


        SharedPreferences prefs = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        idUsuario = prefs.getString("idgoogle", "null");

        button=(Button)findViewById(R.id.atras);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   finish();

            }
        });

        String nombr = getIntent().getExtras().getString("nombre");
         codigo_ba = getIntent().getExtras().getString("codigo_barras");
        String detalle = getIntent().getExtras().getString("detalle");
        String precio = getIntent().getExtras().getString("precio");
        String imagen = getIntent().getExtras().getString("imagen");

        String url = ApiService.API_BASE_URL + "/images/" + imagen;
        Picasso.with(DetailActivity.this).load(url).into(fotoImage);

      //  String url = ApiService.API_BASE_URL + "/images/" + producto.getImagen();
     // Picasso.with(DetailActivity.this).load(imagen).into(fotoImage);


        nombreTxt.setText(nombr);
        detalleTxt.setText(detalle);
        precioTxt.setText("S/ "+precio);
        codigobarras.setText(codigo_ba);
        //fotoImage.setImageURI(imagen);
      //  enviar(codigo_ba,idUsuario);

        Button button;
        button=(Button)findViewById(R.id.enviarcarito)  ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String codigo_barra=codigo_ba;
                final String idUsu=idUsuario.toString();
                com.android.volley.Response.Listener<String> responseListener=new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonResponse=new JSONObject(response);
                            JSONArray success=jsonResponse.getJSONArray("message");

                            for (int i = 0; i < success.length(); i++) {

                                JSONObject Jasonobject = null;
                                Jasonobject = success.getJSONObject(i);
                            }


                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }

                };
                ProductoRequest productoRequest=new ProductoRequest(codigo_barra,idUsu,responseListener);
                RequestQueue requestQueue= Volley.newRequestQueue(DetailActivity.this);
                requestQueue.add(productoRequest);
                fragmentManager = getSupportFragmentManager();
                Toast.makeText(DetailActivity.this,"Agregado al carrito",Toast.LENGTH_SHORT).show();
                finish();

            }

        });
    }

             /*
    public void enviar(View view) {
        final String codigo_barra=codigo_ba;
        final String idUsu=idUsuario.toString();
        com.android.volley.Response.Listener<String> responseListener=new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject jsonResponse=new JSONObject(response);
                    JSONArray success=jsonResponse.getJSONArray("message");

                    for (int i = 0; i < success.length(); i++) {

                        JSONObject Jasonobject = null;
                        Jasonobject = success.getJSONObject(i);
                    }


                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

        };
        ProductoRequest productoRequest=new ProductoRequest(codigo_barra,idUsu,responseListener);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(productoRequest);
        fragmentManager = getSupportFragmentManager();
        Toast.makeText(DetailActivity.this,"Agregado al carrito",Toast.LENGTH_SHORT).show();

    }
               */

}
