package com.example.yonny.app1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NosotrosActivity extends AppCompatActivity {
    private Button nosotros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);

        nosotros = (Button) findViewById(R.id.nosotros);

        nosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NosotrosActivity.this, "Proximamente nos podras llamar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
