package com.example.yonny.app1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetalleFactura extends AppCompatActivity {
    TextView precioT,pesoT;
    List<DetalleFac> detalleFacs;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_factura);

        detalleFacs = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerHFact);
        precioT=(TextView)findViewById(R.id.precioTotal);
        pesoT=(TextView)findViewById(R.id.pesoTotal);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.setAdapter(new PerfilAdapter(this));
        String numfac = getIntent().getExtras().getString("idfactura");
        String precio = getIntent().getExtras().getString("precio");
        String total = getIntent().getExtras().getString("peso");

        precioT.setText("S/ "+precio);
        pesoT.setText(total +"g");

        button=(Button)findViewById(R.id.atras);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        enviarCarr(numfac);
    }


    public void enviarCarr(String id_factura ) {
        final String idUsuario=id_factura.toString();


        com.android.volley.Response.Listener<String> responseListener=new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject jsonResponse=new JSONObject(response);
                    JSONArray success=jsonResponse.getJSONArray("message");
                    int i;
                    for ( i = 0; i < success.length(); i++) {
                        JSONObject Jasonobject = null;
                        Jasonobject = success.getJSONObject(i);

                        String codigo_barras=Jasonobject.getString("codigo_barras") ;
                        String nombre= Jasonobject.getString("nombre");
                        String peso= Jasonobject.getString("peso");
                      //  String imagen= Jasonobject.getString("imagen");
                        String precio = Jasonobject.getString("precio");
                        //   String estado = Jasonobject.getString("estado");

                        DetalleFac detalleFac=new DetalleFac();
                        detalleFac.setNombre(nombre);
                        detalleFac.setCodigo_barras(codigo_barras);
                        detalleFac.setMonto(precio);
                        detalleFac.setPeso(peso);

                        detalleFacs.add(detalleFac);
                       // Log.d(TAG, "productos: " + carrito);

                    }
                    adapter = new DetalleFacAdapter(detalleFacs,getApplicationContext());
                    recyclerView.setAdapter(adapter);

                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

        };


        HistofacRequest usuarioRequest=new HistofacRequest(idUsuario,responseListener);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(usuarioRequest);

    }
}
