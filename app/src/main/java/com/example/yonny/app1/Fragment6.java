package com.example.yonny.app1;

import android.os.Bundle;
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
        LatLng latLng = new LatLng(-12.0868332,-77.03437059999999);
        LatLng latLng2 = new LatLng(-12.04156077208951,-77.02208340167999);
        // Un zoom mayor que 13 hace que el emulador falle, pero un valor deseado para
        // callejero es 17 aprox.
        float zoom = 13;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, zoom));
        map.setMyLocationEnabled(true);


        // Colocar un marcador en la misma posición
        map.addMarker(new MarkerOptions().position(latLng).title("Plaza Vea - Risso"));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        map.addMarker(new MarkerOptions().position(latLng2).title("Plaza Vea Acho"));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
        // Más opciones para el marcador en:
        // https://developers.google.com/maps/documentation/android/marker

        // Otras configuraciones pueden realizarse a través de UiSettings
        // UiSettings settings = getMap().getUiSettings();
    }

}