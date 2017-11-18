package com.example.yonny.app1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();


    private TextView nombreTxt;
    private TextView detalleTxt;
    private TextView precioTxt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nombreTxt = (TextView) findViewById(R.id.nombre_text);
        detalleTxt = (TextView) findViewById(R.id.detalles_text);
        precioTxt = (TextView) findViewById(R.id.precio_text);

        String nombr = getIntent().getExtras().getString("nombre");
        String detalle = getIntent().getExtras().getString("detalle");
        String precio = getIntent().getExtras().getString("precio");

        nombreTxt.setText(nombr);
        detalleTxt.setText(detalle);
        precioTxt.setText(precio);
    }


}
