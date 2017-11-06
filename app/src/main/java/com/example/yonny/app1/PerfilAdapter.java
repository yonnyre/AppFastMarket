package com.example.yonny.app1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yonny on 05/10/2017.
 */

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.ViewHolder> {
    private List<Perfil> perfils;


    public PerfilAdapter(){
        this.perfils = new ArrayList<>();
    }

    public void setPerfils(List<Perfil> perfils) {
        this.perfils = perfils;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView fullname;

        public ViewHolder(View itemView) {
            super(itemView);
          fullname = (TextView) itemView.findViewById(R.id.fullname_text);
        }
    }

    @Override
    public PerfilAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perfil, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PerfilAdapter.ViewHolder viewHolder, int position) {
        Perfil person = this.perfils.get(position);
        viewHolder.fullname.setText(person.getNombre());

        Context context = viewHolder.itemView.getContext();

    }

    @Override
    public int getItemCount() {
        return this.perfils.size();
    }

}
