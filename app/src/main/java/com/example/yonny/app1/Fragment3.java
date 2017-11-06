package com.example.yonny.app1;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Date;
import java.util.List;

import retrofit2.Callback;


import retrofit2.Response;

public class Fragment3 extends Fragment  {

   // private static final String TAG = Fragment3.class.getSimpleName();
    //private static final int REGISTER_FORM_REQUEST = 100;
    private static final String TAG = Fragment3.class.getSimpleName();
    private RecyclerView productosList;

    private RecyclerView recyclerView;
    private TextView textView;

    public Fragment3(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment3, container, false);

       //textView=(TextView)view.findViewById(R.id.text_tex);


        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        PerfilAdapter adapter=new PerfilAdapter();

        List<Perfil> perfils = PerfilRepository.getPerfils();
        adapter.setPerfils(perfils);


        recyclerView.setAdapter(adapter);

        productosList = (RecyclerView) view.findViewById(R.id.recyclerview);
        productosList.setLayoutManager(new LinearLayoutManager(getActivity()));

        productosList.setAdapter(new ProductosAdapter());

        //  initialize();
        //recyclerView=(RecyclerView)getView().findViewById(R.id.recycler);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      //  this.recyclerView.setAdapter(new PerfilAdapter(getActivity()));
        //refreshData();
        return view;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

    }
    public void refreshData(){
        PerfilAdapter adapter = (PerfilAdapter) recyclerView.getAdapter();
        List<Perfil> perfils = PerfilRepository.getPerfils();
        adapter.setPerfils(perfils);

      adapter.notifyDataSetChanged(); // Refrezca los cambios en el RV
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.escanner) {

           IntentIntegrator integrator = IntentIntegrator.forSupportFragment(Fragment3.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();

            //scanFromFragment();
            /*IntentIntegrator integrator=new IntentIntegrator.forFragment(this).initiateScan();


            integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void scanFromFragment() {

        IntentIntegrator.forSupportFragment(this).initiateScan();

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result != null){
            if(result.getContents()==null){
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(getActivity(), "canceled",Toast.LENGTH_LONG).show();

               // Toast.makeText(this,"Escanner cancelado",Toast.LENGTH_LONG).show();
            }else{
                String textView1=result.getContents().toString();
             //   textView.setText(textView1);
                //Perfil perfil = PerfilRepository.addPerfil(textView1);
                Log.d("MainActivity", "Scanned");
                Toast.makeText(getActivity(),"Scanned: " + result.getContents(),Toast.LENGTH_LONG).show();

                Perfil perfil = new Perfil();
                perfil.setNombre(textView1);
                PerfilRepository.add(perfil);

                /*
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Resultado del escaner ");
                builder.setMessage(result.getText());
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                mScannerView.resumeCameraPreview(this);
*/
          //      Toast.makeText(getActivity(),result.getContents(),Toast.LENGTH_LONG).show();

            }
        }else{

            super.onActivityResult(requestCode, resultCode, data);


        }
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        retrofit2.Call<List<Producto>> call = service.getProductos();

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Producto>> call, Response<List<Producto>> response) {
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
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Producto>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
    public String enviarDatoGET(String codigobarra){
        URL

    }
}
