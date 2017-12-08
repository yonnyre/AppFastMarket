package com.example.yonny.app1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PayuConfig;
import com.payu.india.Model.PayuHashes;
import com.payu.india.Payu.Payu;
import com.payu.india.Payu.PayuConstants;
import com.payu.payuui.Activity.PayUBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.yonny.app1.R.id.total;
import static com.facebook.FacebookSdk.getApplicationContext;

public class Fragment3 extends Fragment implements BillingProcessor.IBillingHandler {

    private static final String TAG = Fragment3.class.getSimpleName();
    private SwipeRefreshLayout refreshLayout;
    public PerfilAdapter mAdapter;
     List<Perfil> perfils;
    public List<Carrito> carritos;
     RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    String idGoogle;
    String idFace;
    String nombres ;
    String email ;
    BillingProcessor bp;
    String totalPrecio;
    String totalPeso ;
    String amount;
    private String merchantKey,userCredentials;
    private PaymentParams mPaymentParams;
    private PayuConfig payuConfig;


    public Fragment3() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);



        bp = new BillingProcessor(getActivity(), "YOUR LICENSE KEY FROM GOOGLE PLAY CONSOLE HERE", this);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srlContainer);

        perfils = new ArrayList<>();


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




        bp.loadOwnedPurchasesFromGoogle();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewcompras);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

      /*  PerfilAdapter adapter = new PerfilAdapter(getActivity());
        List<Perfil> perfils = PerfilRepository.getPerfils();
       adapter.setPerfils(perfils);*/
        recyclerView.setAdapter(new PerfilAdapter(getActivity()));

       // final ArrayList<Perfil> dato = new ArrayList<Perfil>();

        carritos = new ArrayList<>();


        Payu.setInstance(getActivity());

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

           //initialize();

        SharedPreferences prefs2 = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        idGoogle = prefs2.getString("idgoogle", "null");
        idFace = prefs2.getString("idfacebook", "null");

        nombres = prefs2.getString("nombres", "null");
        email = prefs2.getString("email", "null");
       // enviarCarr(idGoogle,nombres,email);
              //   initialize();
        enviarCarr(idGoogle);
        recibirSuma(idGoogle);
//        Toast.makeText(getActivity(),idFace,Toast.LENGTH_SHORT).show();

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



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            if (data != null) {

                String idfac=mPaymentParams.getTxnId();
                String mont=mPaymentParams.getAmount();

                final android.support.v4.app.NotificationCompat.Builder mBuilder;
                final NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

                int icono = R.mipmap.ic_launcher;
                Intent i=new Intent(getActivity(), Fragment4.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, i, 0);

                final Notification notification = new NotificationCompat.Builder(getActivity())
                        .setContentTitle("Gracias por su compra ")
                        .setContentText("Comprobate : "+idfac)
                        .setSmallIcon(icono)
                        .setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();


               final NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);


                new AlertDialog.Builder(getActivity())
                        .setCancelable(false)
                        .setMessage("Gracias por su Compra le enviaremos una notificacion de confirmacion  ")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();

                String fecha = dateFormat.format(date);

                enviarFactu(idfac, fecha,totalPrecio,totalPeso,idGoogle);

                new CountDownTimer(2000, 2000) {
                    public void onFinish() {
                        notificationManager.notify(0, notification);
                    }

                    public void onTick(long millisUntilFinished) {
                    }
                }.start();

            } else {
                Toast.makeText(getActivity(), getString(R.string.could_not_receive_data), Toast.LENGTH_LONG).show();
            }
        }

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
                Log.d("MainActivity", "Scanned");
                Log.d("MainActivity", result.getContents());
                enviar(textView1,idGoogle);
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }


    }

    public void enviarFactu( String idfactura,String fecha,String monto,String peso,String id_usuario ) {
         String idfac=idfactura.toString();
         String id_usuar=id_usuario.toString();
         String fech=fecha.toString();
         String mont=monto.toString();
         String pesoo=peso.toString();

        com.android.volley.Response.Listener<String> responseListener= new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

            }
        };
        FacturaRequest facturaRequest=new FacturaRequest(idfac,fech,mont,pesoo,id_usuar,responseListener);
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(facturaRequest);
    }



    public void enviar(String codigo ,String idUsuario) {
        final String codigo_barra=codigo.toString();
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
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(productoRequest);

    }

    public void enviarCarr(String id_usuario ) {
        final String idUsuario=id_usuario.toString();


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
                        String imagen= Jasonobject.getString("imagen");
                        String precio = Jasonobject.getString("precio");
                     //   String estado = Jasonobject.getString("estado");

                        Perfil carrito=new Perfil();
                        carrito.setNombre(nombre);
                        carrito.setPrecio(precio);
                        carrito.setImagen(imagen);
                        carrito.setCodigo_barras(codigo_barras);



                        perfils.add(carrito);
                        Log.d(TAG, "productos: " + carrito);

                    }
                    adapter = new PerfilAdapter(perfils,getApplicationContext());
                    recyclerView.setAdapter(adapter);

                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

        };


        CarritoRequest usuarioRequest=new CarritoRequest(idUsuario,responseListener);
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(usuarioRequest);

    }

    public void recibirSuma(String id_usuario ) {
        final String idUsuario=id_usuario.toString();


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
                         totalPrecio=Jasonobject.getString("precio") ;
                         totalPeso=Jasonobject.getString("peso") ;
                        Log.d(TAG, "total: " + total);


                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

        };


        TotalRequest usuarioRequest=new TotalRequest(idUsuario,responseListener);
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(usuarioRequest);

    }


   /*
    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Perfil>> call = service.setCarritos();

        call.enqueue(new Callback<List<Carrito>>() {
            @Override
            public void onResponse(Call<List<Carrito>> call, Response<List<Carrito>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Carrito> perfils = response.body();
                        Log.d(TAG, "productos: " + perfils);

                        CarritoAdapter adapter = (CarritoAdapter) recyclerView.getAdapter();
                        adapter.setCarritos(perfils);
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
            public void onFailure(Call<List<Carrito>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
              //  Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

       */


    /*
    public void load() {
        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        PerfilAdapter adapter = new PerfilAdapter();
        List<Perfil> perfils = PerfilRepository.getPerfils();
        adapter.setPerfils(perfils);
        recyclerView.setAdapter(adapter);


    }          */



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

            return true;
        }else if(id == R.id.pagar){


            merchantKey = "gtKFFx";
           /* String myNum = "0";
            myNum = String.valueOf(Integer.parseInt(precio)+Integer.parseInt(myNum));
          */
            int environment = PayuConstants.STAGING_ENV;
            String email = "payutest@gmail.com";
             amount = totalPrecio;
             String peso = totalPeso;
            userCredentials = merchantKey + ":" + email;
            //String value = environmentSpinner.getSelectedItem().toString();
           /* String TEST_ENVIRONMENT = getResources().getString(R.string.test);
           if (value.equals(TEST_ENVIRONMENT))
              environment = PayuConstants.STAGING_ENV;
           else
            environment = PayuConstants.PRODUCTION_ENV;
                                        */

            //TODO Below are mandatory params for hash genetation
            mPaymentParams = new PaymentParams();
            /**
             * For Test Environment, merchantKey = "gtKFFx"
             * For Production Environment, merchantKey should be your live key or for testing in live you can use "0MQaQP"
             */
            mPaymentParams.setKey(merchantKey);
            mPaymentParams.setAmount(amount);
            mPaymentParams.setProductInfo(peso);
            mPaymentParams.setFirstName("firstname");
            mPaymentParams.setEmail(email);

        /*
        * Transaction Id should be kept unique for each transaction.
        * */
            mPaymentParams.setTxnId("" + System.currentTimeMillis());

            /**
             * Surl --> Success url is where the transaction response is posted by PayU on successful transaction
             * Furl --> Failre url is where the transaction response is posted by PayU on failed transaction
             */
            mPaymentParams.setSurl("https://payu.herokuapp.com/success");
            mPaymentParams.setFurl("https://payu.herokuapp.com/failure");

        /*
         * udf1 to udf5 are options params where you can pass additional information related to transaction.
         * If you don't want to use it, then send them as empty string like, udf1=""
         * */
            mPaymentParams.setUdf1("udf1");
            mPaymentParams.setUdf2("udf2");
            mPaymentParams.setUdf3("udf3");
            mPaymentParams.setUdf4("udf4");
            mPaymentParams.setUdf5("udf5");

            /**
             * These are used for store card feature. If you are not using it then user_credentials = "default"
             * user_credentials takes of the form like user_credentials = "merchant_key : user_id"
             * here merchant_key = your merchant key,
             * user_id = unique id related to user like, email, phone number, etc.
             * */
            mPaymentParams.setUserCredentials(userCredentials);

            //TODO Pass this param only if using offer key
            //mPaymentParams.setOfferKey("cardnumber@8370");

            //TODO Sets the payment environment in PayuConfig object
            payuConfig = new PayuConfig();
            payuConfig.setEnvironment(environment);

            //TODO It is recommended to generate hash from server only. Keep your key and salt in server side hash generation code.
            generateHashFromServer(mPaymentParams);

            /**
             * Below approach for generating hash is not recommended. However, this approach can be used to test in PRODUCTION_ENV
             * if your server side hash generation code is not completely setup. While going live this approach for hash generation
             * should not be used.
             * */
            //String salt = "13p0PXZk";
            //generateHashFromSDK(mPaymentParams, salt);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof PerfilAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            //String name = perfils.get(viewHolder.getAdapterPosition()).getNombre();

            // backup of removed item for undo purpose
           // final Item deletedItem = perfils.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view


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


    /**
     * This method generates hash from server.
     *
     * @param mPaymentParams payments params used for hash generation
     */
    public void generateHashFromServer(PaymentParams mPaymentParams) {
        //nextButton.setEnabled(false); // lets not allow the user to click the button again and again.

        // lets create the post params
        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayuConstants.KEY, mPaymentParams.getKey()));
        postParamsBuffer.append(concatParams(PayuConstants.AMOUNT, mPaymentParams.getAmount()));
        postParamsBuffer.append(concatParams(PayuConstants.TXNID, mPaymentParams.getTxnId()));
        postParamsBuffer.append(concatParams(PayuConstants.EMAIL, null == mPaymentParams.getEmail() ? "" : mPaymentParams.getEmail()));
        postParamsBuffer.append(concatParams(PayuConstants.PRODUCT_INFO, mPaymentParams.getProductInfo()));
        postParamsBuffer.append(concatParams(PayuConstants.FIRST_NAME, null == mPaymentParams.getFirstName() ? "" : mPaymentParams.getFirstName()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF1, mPaymentParams.getUdf1() == null ? "" : mPaymentParams.getUdf1()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF2, mPaymentParams.getUdf2() == null ? "" : mPaymentParams.getUdf2()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF3, mPaymentParams.getUdf3() == null ? "" : mPaymentParams.getUdf3()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF4, mPaymentParams.getUdf4() == null ? "" : mPaymentParams.getUdf4()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF5, mPaymentParams.getUdf5() == null ? "" : mPaymentParams.getUdf5()));
        postParamsBuffer.append(concatParams(PayuConstants.USER_CREDENTIALS, mPaymentParams.getUserCredentials() == null ? PayuConstants.DEFAULT : mPaymentParams.getUserCredentials()));

        // for offer_key
        if (null != mPaymentParams.getOfferKey())
            postParamsBuffer.append(concatParams(PayuConstants.OFFER_KEY, mPaymentParams.getOfferKey()));

        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();

        // lets make an api call
        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
        getHashesFromServerTask.execute(postParams);
    }


    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }

    /**
     * This AsyncTask generates hash from server.
     */
    private class GetHashesFromServerTask extends AsyncTask<String, String, PayuHashes> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected PayuHashes doInBackground(String... postParams) {
            PayuHashes payuHashes = new PayuHashes();
            try {

                //TODO Below url is just for testing purpose, merchant needs to replace this with their server side hash generation url
                URL url = new URL("https://payu.herokuapp.com/get_hash");

                // get the payuConfig first
                String postParam = postParams[0];

                byte[] postParamsByte = postParam.getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postParamsByte);

                InputStream responseInputStream = conn.getInputStream();
                StringBuffer responseStringBuffer = new StringBuffer();
                byte[] byteContainer = new byte[1024];
                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                    responseStringBuffer.append(new String(byteContainer, 0, i));
                }

                JSONObject response = new JSONObject(responseStringBuffer.toString());

                Iterator<String> payuHashIterator = response.keys();
                while (payuHashIterator.hasNext()) {
                    String key = payuHashIterator.next();
                    switch (key) {
                        //TODO Below three hashes are mandatory for payment flow and needs to be generated at merchant server
                        /**
                         * Payment hash is one of the mandatory hashes that needs to be generated from merchant's server side
                         * Below is formula for generating payment_hash -
                         *
                         * sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||SALT)
                         *
                         */
                        case "payment_hash":
                            payuHashes.setPaymentHash(response.getString(key));
                            break;
                        /**
                         * vas_for_mobile_sdk_hash is one of the mandatory hashes that needs to be generated from merchant's server side
                         * Below is formula for generating vas_for_mobile_sdk_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be "default"
                         *
                         */
                        case "vas_for_mobile_sdk_hash":
                            payuHashes.setVasForMobileSdkHash(response.getString(key));
                            break;
                        /**
                         * payment_related_details_for_mobile_sdk_hash is one of the mandatory hashes that needs to be generated from merchant's server side
                         * Below is formula for generating payment_related_details_for_mobile_sdk_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be user credentials. If you are not using user_credentials then use "default"
                         *
                         */
                        case "payment_related_details_for_mobile_sdk_hash":
                            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(response.getString(key));
                            break;

                        //TODO Below hashes only needs to be generated if you are using Store card feature
                        /**
                         * delete_user_card_hash is used while deleting a stored card.
                         * Below is formula for generating delete_user_card_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be user credentials. If you are not using user_credentials then use "default"
                         *
                         */
                        case "delete_user_card_hash":
                            payuHashes.setDeleteCardHash(response.getString(key));
                            break;
                        /**
                         * get_user_cards_hash is used while fetching all the cards corresponding to a user.
                         * Below is formula for generating get_user_cards_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be user credentials. If you are not using user_credentials then use "default"
                         *
                         */
                        case "get_user_cards_hash":
                            payuHashes.setStoredCardsHash(response.getString(key));
                            break;
                        /**
                         * edit_user_card_hash is used while editing details of existing stored card.
                         * Below is formula for generating edit_user_card_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be user credentials. If you are not using user_credentials then use "default"
                         *
                         */
                        case "edit_user_card_hash":
                            payuHashes.setEditCardHash(response.getString(key));
                            break;
                        /**
                         * save_user_card_hash is used while saving card to the vault
                         * Below is formula for generating save_user_card_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be user credentials. If you are not using user_credentials then use "default"
                         *
                         */
                        case "save_user_card_hash":
                            payuHashes.setSaveCardHash(response.getString(key));
                            break;

                        //TODO This hash needs to be generated if you are using any offer key
                        /**
                         * check_offer_status_hash is used while using check_offer_status api
                         * Below is formula for generating check_offer_status_hash -
                         *
                         * sha512(key|command|var1|salt)
                         *
                         * here, var1 will be Offer Key.
                         *
                         */
                        case "check_offer_status_hash":
                            payuHashes.setCheckOfferStatusHash(response.getString(key));
                            break;
                        default:
                            break;
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return payuHashes;
        }

        @Override
        protected void onPostExecute(PayuHashes payuHashes) {
            super.onPostExecute(payuHashes);

            progressDialog.dismiss();
            launchSdkUI(payuHashes);
        }
    }

    /**
     * This method adds the Payuhashes and other required params to intent and launches the PayuBaseActivity.java
     *
     * @param payuHashes it contains all the hashes generated from merchant server
     */
    public void launchSdkUI(PayuHashes payuHashes) {

        Intent intent = new Intent(getActivity(), PayUBaseActivity.class);
        intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
        intent.putExtra(PayuConstants.PAYMENT_PARAMS, mPaymentParams);
        intent.putExtra(PayuConstants.PAYU_HASHES, payuHashes);

        startActivityForResult(intent,PayuConstants.PAYU_REQUEST_CODE);

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
            return perfils;
        }

        @Override
        protected void onPostExecute(List result) {
            super.onPostExecute(result);


            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

    }


}

