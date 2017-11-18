package com.example.yonny.app1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment5 extends Fragment   {

    private RecyclerView recyclerView;
    private TextView textNombre1;
    private CircleImageView imageView;

    private TextView textNombre2;

    public void Fragment5(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_fragment5, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        String name  =  prefs.getString("nombre", "null");
        imageView = (CircleImageView) view.findViewById(R.id.profile_image);


        textNombre1 = (TextView)view.findViewById(R.id.text_nombre);

        textNombre1.setText(name);
        Bundle args = getArguments();
        String photo = args.getString("photo", "No photo");
        if (photo != "0"){
            Glide.with(getActivity()).load(photo).into(imageView);
        } else {
            imageView.getResources().getDrawable(R.drawable.background_boton_facebook);
        }


        return view;
    }





    }
