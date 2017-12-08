package com.example.yonny.app1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class Detallle extends AppCompatActivity {


    private static final String TAG = Detallle.class.getSimpleName();

    private String codigo_barras;

    private ImageView fotoImage;
    private TextView nombreText;
    private TextView detallesText;
    private TextView precioText;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_carrtio);

       // fotoImage = (ImageView)findViewById(R.id.foto_image);
       // nombreText = (TextView)findViewById(R.id.nombre_text);
       // detallesText = (TextView)findViewById(R.id.detalles_text);
       // precioText = (TextView)findViewById(R.id.precio_text);

       // codigo_barras = getIntent().getExtras().getString("codigo_barras");
      //  Log.e(TAG, "codigo_barras:" + codigo_barras);

     //   initialize();

    }





}
