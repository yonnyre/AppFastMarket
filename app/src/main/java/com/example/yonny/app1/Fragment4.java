package com.example.yonny.app1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Fragment4 extends Fragment {
    private RecyclerView facturaList;
    Factura factura;
    RecyclerView.Adapter adapter;
    private SwipeRefreshLayout refreshLayout;
    List<Factura> facList;
    private TextView textView1,textView2;
    String idGoogle     ;
    String nombres ;
    String email ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_fragment4, container, false);
        facturaList = (RecyclerView) view.findViewById(R.id.recyclerview2);
        facturaList.setLayoutManager(new LinearLayoutManager(getActivity()));

        facturaList.setAdapter(new FacturaAdapter(getActivity()));

        List<Factura> perfils = FacturaRepository.getFacturas();
        facList = new ArrayList<>();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srlContainer2);
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask().execute();
                    }
                }
        );

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

          idGoogle = prefs.getString("idgoogle", "null");
          nombres = prefs.getString("nombres", "null");
          email = prefs.getString("email", "null");

           inicializar();

        return view;

    }
    public void inicializar(){
        enviar(idGoogle,nombres,email);
    }



    public void enviar( String id_usuario , String nombres,String email) {
        final String idUsuario=id_usuario.toString();
        final String nombUsu=nombres.toString();
        final String corrUsu=email.toString();


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
                        String monto = Jasonobject.getString("monto");
                        String idfactura = Jasonobject.getString("num_factura");
                        String fecha= Jasonobject.getString("fecha");
                        String peso= Jasonobject.getString("peso");

                        Factura factura=new Factura();
                        factura.setIdfactura(idfactura);
                        factura.setMonto(monto);
                        factura.setPeso(peso);
                        factura.setFecha(fecha);

                        facList.add(factura);

                    }
                    adapter = new FacturaAdapter(facList,getApplicationContext());
                    facturaList.setAdapter(adapter);

                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

        };

        UsuarioRequest usuarioRequest=new UsuarioRequest(idUsuario,nombUsu,corrUsu,responseListener);
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(usuarioRequest);

    }
    public class HackingBackgroundTask extends AsyncTask<Void, Void, List<Producto>> {

        static final int DURACION = 3 * 1000; // 3 segundos de carga

        @Override
        protected List doInBackground(Void... params) {
            // Simulación de la carga de items
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Retornar en nuevos elementos para el adaptador
            return facList;
        }

        @Override
        protected void onPostExecute(List result) {
            super.onPostExecute(result);


            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

    }

}
