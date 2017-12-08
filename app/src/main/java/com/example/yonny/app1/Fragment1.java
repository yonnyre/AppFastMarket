package com.example.yonny.app1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment1 extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView productosList;
    private ProductosAdapter adapter;
    private List<Producto> proList;
    private GoogleApiClient googleApiClient;



    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView textNombre1;
    List<Integer> color;
    List<String> colorName;
    private SwipeRefreshLayout refreshLayout;

    ViewPager viewPager;
    Button btnEliminar ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        productosList = (RecyclerView) view.findViewById(R.id.recycler_view);
        productosList.setLayoutManager(new LinearLayoutManager(getActivity()));

        productosList.setAdapter(new ProductosAdapter(getActivity()));

        GridLayoutManager lmGrid = new GridLayoutManager(getActivity(), 2);
        productosList.setLayoutManager(lmGrid);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srlContainer);
        // Iniciar la tarea asíncrona al revelar el indicador
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask().execute();
                    }
                }
        );

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        textNombre1 = (TextView)view.findViewById(R.id.bienvenid);
        String name  =  prefs.getString("givenname", "");
        btnEliminar = (Button) view.findViewById(R.id.eliminar);
        textNombre1.setText("¡Hola  "+ name+"!");
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        initialize();
     return  view;
    }
    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Producto>> call = service.getProductos();

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Producto> productos = response.body();
                        Log.d(TAG, "productos: " + productos);

                        ProductosAdapter adapter = (ProductosAdapter) productosList.getAdapter();
                        adapter.setProductos(productos);
                        adapter.notifyDataSetChanged();


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){   }
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
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
            return proList;
        }

        @Override
        protected void onPostExecute(List result) {
            super.onPostExecute(result);


            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

    }


}
