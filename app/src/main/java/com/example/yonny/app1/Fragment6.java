package com.example.yonny.app1;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment6 extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Posicionar el mapa en una localización y con un nivel de zoom
        LatLng latLng = new LatLng(-12.0868332, -77.03437059999999);
        LatLng latLng2 = new LatLng(-12.04156077208951, -77.02208340167999);
        // Un zoom mayor que 13 hace que el emulador falle, pero un valor deseado para
        // callejero es 17 aprox.

        float zoom = 13;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, zoom));
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);

        //((TextView)getView().findViewById(R.id.info_window_nombre)).setText(latLng.toString());

        //  ((TextView)v.findViewById(R.id.info_window_placas)).setText("Placas: SRX32");
       // ((TextView)getView().findViewById(R.id.info_window_estado)).setText("Estado: Activo");
        // Colocar un marcador en la misma posición
        map.addMarker(new MarkerOptions().position(latLng).title("Plaza Vea - Risso").snippet("Miraflores, Avenida Arequipa 4651, Lima, 15046"));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getActivity())));
        map.addMarker(new MarkerOptions().position(latLng2).title("Plaza Vea Acho").snippet("Marañón 601, Rímac Lima"));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
        // Más opciones para el marcador en:
        // https://developers.google.com/maps/documentation/android/marker

        // Otras configuraciones pueden realizarse a través de UiSettings
        // UiSettings settings = getMap().getUiSettings();
    }

}