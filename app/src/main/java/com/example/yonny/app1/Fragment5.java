package com.example.yonny.app1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment5 extends Fragment implements GoogleApiClient.OnConnectionFailedListener{

    private RecyclerView recyclerView;
    private TextView textNombre1;
    private CardView config;
    private CardView misdatos;
    private CircleImageView imageView;
    private TextView textNombre2;
    private CardView cerrarsesion;
    private GoogleApiClient googleApiClient;
    public void Fragment5(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_fragment5, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        String name  =  prefs.getString("nombre", "null");
        String email  =  prefs.getString("email", "null");
        imageView = (CircleImageView) view.findViewById(R.id.profile_image);
        config=(CardView) view.findViewById(R.id.configuracion)  ;
        misdatos=(CardView) view.findViewById(R.id.misdddatos)  ;

        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),SettingsActivity.class);
                startActivity(i);
            }
        });
        misdatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Misdatos.class);
                startActivity(intent);
            }
        });
        textNombre1 = (TextView)view.findViewById(R.id.text_nombre);
        textNombre2 = (TextView)view.findViewById(R.id.text_correo);

        textNombre1.setText(name);
        textNombre2.setText(email);



        Bundle args = getArguments();
        String photo = args.getString("photo", "No photo");
        if (photo != "0"){
            Glide.with(getActivity()).load(photo).into(imageView);
        } else {
            imageView.getResources().getDrawable(R.drawable.background_boton_facebook);
        }
        cerrarsesion=(CardView)view.findViewById(R.id.cerrarsesion);
        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               LoginManager.getInstance().logOut();
               goLogInScreen();
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                            goLogInScreen();

                    }
                });

            }
        });

        return view;
    }



    public void goLogInScreen() {
        Intent intent = new Intent(getActivity(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
