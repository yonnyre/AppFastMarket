package com.example.yonny.app1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailCompra2 extends AppCompatActivity {

    private static final String TAG = DetailCompra2.class.getSimpleName();


    private TextView nombreTxt;
    private TextView detalleTxt;
    private TextView precioTxt;
    private ImageView fotoImage;
    private TextView codigobarras;
    Button button;
    String codigo_ba;

    public DetailCompra2() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_carrtio);
        nombreTxt = (TextView) findViewById(R.id.nombre_text2);
        detalleTxt = (TextView) findViewById(R.id.detalles_text2);
        codigobarras = (TextView) findViewById(R.id.codigoProducto2);
        precioTxt = (TextView) findViewById(R.id.precio_text2);
        fotoImage=(ImageView)findViewById(R.id.foto_image2);



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
        Picasso.with(DetailCompra2.this).load(url).into(fotoImage);

      //  String url = ApiService.API_BASE_URL + "/images/" + producto.getImagen();
     // Picasso.with(DetailActivity.this).load(imagen).into(fotoImage);


        nombreTxt.setText(nombr);
        detalleTxt.setText(detalle);
        precioTxt.setText("S/ "+precio);
        codigobarras.setText(codigo_ba);
        //fotoImage.setImageURI(imagen);
    }



}
