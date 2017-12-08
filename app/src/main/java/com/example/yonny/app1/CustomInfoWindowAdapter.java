package com.example.yonny.app1;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Yonny on 19/11/2017.
 */
public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;
    private View popup=null;

    public CustomInfoWindowAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.infowindow_layout, null);
        String[] info = m.getTitle().split("&");
        String url = m.getSnippet();
      //  ((TextView)v.findViewById(R.id.info_window_nombre)).setText("Lina Cortés");

      //  ((TextView)v.findViewById(R.id.info_window_placas)).setText("Placas: SRX32");
     //   ((TextView)v.findViewById(R.id.info_window_estado)).setText("Estado: Activo");
        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        View v = inflater.inflate(R.layout.infowindow_layout, null);

        ImageView iv=(ImageView)v.findViewById(R.id.info_window_imagen);
        TextView tx1=(TextView) v.findViewById(R.id.info_window_nombre);
        TextView tx2=(TextView) v.findViewById(R.id.info_window_direccion);
        iv.setImageResource(R.drawable.background_boton_google);
        tx1.setText(m.getTitle());
        tx2.setText(m.getSnippet());
        return v;
    }

}