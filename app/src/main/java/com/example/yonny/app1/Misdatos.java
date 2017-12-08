package com.example.yonny.app1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Misdatos extends AppCompatActivity {
    private Button button;

    EditText correEditText,nombreEditText,dniText,telText;
    KeyListener mKeyListener,mKeyListener2,mKeyListener3,mKeyListener4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_datos);

        correEditText=(EditText)findViewById(R.id.mail_Text);
        nombreEditText=(EditText)findViewById(R.id.nom_text);
        dniText=(EditText)findViewById(R.id.dni_txt);
        telText=(EditText)findViewById(R.id.telefo_txt);



        SharedPreferences prefs = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String name  =  prefs.getString("givenname", "");
        nombreEditText.setText(name);


        mKeyListener = correEditText.getKeyListener();
        correEditText.setKeyListener(null);

        mKeyListener2 = nombreEditText.getKeyListener();
        nombreEditText.setKeyListener(null);

        mKeyListener3 = dniText.getKeyListener();
        dniText.setKeyListener(null);

        mKeyListener4= telText.getKeyListener();
        telText.setKeyListener(null);


        button=(Button)findViewById(R.id.atras);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void editarNom(View view){
        nombreEditText.setKeyListener(mKeyListener2);
        dniText.setKeyListener(mKeyListener3);
        telText.setKeyListener(mKeyListener4);

    }
    public void editarDni(View view){
        nombreEditText.setKeyListener(mKeyListener2);
        dniText.setKeyListener(mKeyListener3);
        telText.setKeyListener(mKeyListener4);    }
    public void editarTel(View view){
        nombreEditText.setKeyListener(mKeyListener2);
        dniText.setKeyListener(mKeyListener3);
        telText.setKeyListener(mKeyListener4);    }

    public void guardarDatos(View view){

        mKeyListener2 = nombreEditText.getKeyListener();
        nombreEditText.setKeyListener(null);

        mKeyListener3 = dniText.getKeyListener();
        dniText.setKeyListener(null);

        mKeyListener4= telText.getKeyListener();
        telText.setKeyListener(null);
    }

}
