package com.example.yonny.app1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Yonny on 05/10/2017.
 */

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.ViewHolder> {
    public List<Perfil> perfils;
    private Activity activity;

    public PerfilAdapter(){
        this.perfils = new ArrayList<>();
    }



    public void setPerfils(List<Perfil> perfils) {
        this.perfils = perfils;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Button btnEliminar ;
        public TextView fullname;
        public TextView fullprecio;
        int position = 0;
        public RelativeLayout viewBackground, viewForeground;

        public ViewHolder(View itemView) {
            super(itemView);
            fullname = (TextView) itemView.findViewById(R.id.fullname_text);
            fullprecio = (TextView) itemView.findViewById(R.id.precio_text);
            btnEliminar = (Button)itemView.findViewById(R.id.eliminar);
        }
    }

    @Override
    public PerfilAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perfil, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PerfilAdapter.ViewHolder viewHolder, final int position) {
        final Perfil person = this.perfils.get(position);
        viewHolder.fullname.setText(person.getNombre());
        viewHolder.fullprecio.setText("S/. " +person.getPrecio());


         final Context context = viewHolder.itemView.getContext();
        final Context mContext = getApplicationContext();
        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Perfil itemLabel = perfils.get(position);
                perfils.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,perfils.size());

                // Show the removed item label
                Toast.makeText(mContext ,"Removed : " + itemLabel,Toast.LENGTH_SHORT).show();


            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
                intent.putExtra("nombre", person.getNombre());
                intent.putExtra("precio", person.getPrecio());
                intent.putExtra("detalle", person.getDetalle());
                context.startActivity(intent);
            }
        });

    }
    public void removeItem(int position) {
        perfils.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animatidons
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return this.perfils.size();
    }

}
