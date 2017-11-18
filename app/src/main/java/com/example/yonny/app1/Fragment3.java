package com.example.yonny.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class Fragment3 extends Fragment implements BillingProcessor.IBillingHandler,SwipeRefreshLayout.OnRefreshListener {

    // private static final String TAG = Fragment3.class.getSimpleName();
    //private static final int REGISTER_FORM_REQUEST = 100;
    private static final String TAG = Fragment3.class.getSimpleName();
    private RecyclerView productosList;
    private SwipeRefreshLayout swipeContainer;
    private ApiService mAPIService;
    private TextView mResponseTv;
    public PerfilAdapter mAdapter;
    public List<String> perfils;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView textView;
    private EditText txtcodigo;
    BillingProcessor bp;
    TextView resultado;
    Button btningresar;
    Button button2;
    public Fragment3() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        //mResponseTv = (TextView)view.findViewById(R.id.tv_response);

        //textView=(TextView)view.findViewById(R.id.text_tex);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        bp = new BillingProcessor(getActivity(), "YOUR LICENSE KEY FROM GOOGLE PLAY CONSOLE HERE", this);
        button2=(Button)view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bp.purchase(getActivity(), "android.test.purchased");
            }
        });
       // swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.srlContainer);
      //  swipeContainer.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        PerfilAdapter adapter = new PerfilAdapter();
        List<Perfil> perfils = PerfilRepository.getPerfils();
        bp.loadOwnedPurchasesFromGoogle();
        adapter.setPerfils(perfils);
        recyclerView.setAdapter(adapter);
        final ArrayList<Perfil> dato = new ArrayList<Perfil>();




       /* productosList = (RecyclerView) view.findViewById(R.id.recyclerview);
        productosList.setLayoutManager(new LinearLayoutManager(getActivity()));
        productosList.setAdapter(new ProductosAdapter());
*/

        // initialize();

/*        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String codigo_barra=txtcodigo.getText().toString();

                com.android.volley.Response.Listener<String> responseListener=new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonResponse=new JSONObject(response);
                            JSONArray success=jsonResponse.getJSONArray("message");

                            for (int i = 0; i < success.length(); i++) {
                                JSONObject Jasonobject = null;
                                Jasonobject = success.getJSONObject(i);
                                Toast.makeText(getActivity(),"Scanned: no existe",Toast.LENGTH_LONG).show();
// get an output on the screen
                                String name = Jasonobject.getString("nombre");
                                Toast.makeText(getActivity(),"Scanned: "+ name,Toast.LENGTH_LONG).show();

                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                ProductoRequest productoRequest=new ProductoRequest(codigo_barra,responseListener);
                RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
                requestQueue.add(productoRequest);
            }
        });*/

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

    public void refreshData() {
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

        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(getActivity(), "canceled", Toast.LENGTH_LONG).show();
                // Toast.makeText(this,"Escanner cancelado",Toast.LENGTH_LONG).show();
            } else {
                String textView1=result.getContents().toString();
                //   textView.setText(textView1);
                //Perfil perfil = PerfilRepository.addPerfil(textView1);
                Log.d("MainActivity", "Scanned");
                Log.d("MainActivity", result.getContents());
                enviar(textView1);

                swipeContainer = (SwipeRefreshLayout) getView().findViewById(R.id.srlContainer);

                swipeContainer.isClickable();

               /* Perfil perfil = new Perfil();
                perfil.setNombre(textView1);
                PerfilRepository.add(perfil);
               */ /*
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Resultado del escaner ");
                builder.setMessage(result.getText());
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                mScannerView.resumeCameraPreview(this);



               Toast.makeText(getActivity(),result.getContents(),Toast.LENGTH_LONG).show();
*/
            }
        } else {
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
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Producto>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void enviar(String codigo) {
        final String codigo_barra=codigo.toString();

        com.android.volley.Response.Listener<String> responseListener=new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    JSONObject jsonResponse=new JSONObject(response);
                    JSONArray success=jsonResponse.getJSONArray("message");

                    for (int i = 0; i < success.length(); i++) {
                        JSONObject Jasonobject = null;
                        Jasonobject = success.getJSONObject(i);
                        String name = Jasonobject.getString("nombre");
                        String precio = Jasonobject.getString("precio");
                        String detalle = Jasonobject.getString("detalle");
                        Perfil perfil = new Perfil();
                        perfil.setNombre(name);
                        perfil.setDetalle(detalle);
                        perfil.setPrecio(precio);
                        PerfilRepository.add(perfil);



                    }


                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

        };
        ProductoRequest productoRequest=new ProductoRequest(codigo_barra,responseListener);
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(productoRequest);

    }

    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof PerfilAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            //String name = perfils.get(viewHolder.getAdapterPosition()).getNombre();

            // backup of removed item for undo purpose
           // final Item deletedItem = perfils.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option

            /*   Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();                */

        }
    }


    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
            Toast.makeText(getActivity(),"YOY'VE PURCHASED SOMETHING",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(getActivity(),"SOMETHING WERE WRONG!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                     /* perfils.add(Calendar.getInstance().toString());
                      swipeContainer.setRefreshing(false);

            }
        },3000); */
    }
}


