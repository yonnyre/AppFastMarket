package com.example.yonny.app1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Yonny on 05/10/2017.
 */

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {
    public List<Carrito> carritos;
    private Activity activity;

    public CarritoAdapter(){
        this.carritos = new ArrayList<>();
    }

    public CarritoAdapter(List<Carrito> carritos, Context applicationContext) {
    }


    public void setCarritos(List<Carrito> carritos) {
        this.carritos = carritos;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        Button btnEliminar ;
        public ImageView fotoImage;
        public TextView fullname;
        public TextView fullprecio;
        int position = 0;
        public RelativeLayout viewBackground, viewForeground;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = (ImageView) itemView.findViewById(R.id.foto_image);
            fullname = (TextView) itemView.findViewById(R.id.fullname_text);
            fullprecio = (TextView) itemView.findViewById(R.id.precio_text);
            btnEliminar = (Button)itemView.findViewById(R.id.eliminar);
        }
    }

    @Override
    public CarritoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perfil, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    public void clearApplications() {
        int size = this.carritos.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                carritos.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public void onBindViewHolder(CarritoAdapter.ViewHolder viewHolder, final int position) {
        final Carrito person = this.carritos.get(position);
        viewHolder.fullname.setText(person.getNombres());
        viewHolder.fullprecio.setText("S/. " +person.getPrecio());
        String url = ApiService.API_BASE_URL + "/images/" + person.getImagen();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);


         final Context context = viewHolder.itemView.getContext();
        final Context mContext = getApplicationContext();
        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carrito itemLabel = carritos.get(position);
                carritos.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,carritos.size());

                // Show the removed item label
                Toast.makeText(mContext ,"Removed : " + itemLabel,Toast.LENGTH_SHORT).show();


            }
        });

      /*  viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
                String p=person.getPrecio();
                p= String.valueOf(Integer.parseInt(person.getPrecio())+0);
                intent.putExtra("nombre", person.getNombre());
                intent.putExtra("precio", person.getPrecio());
                intent.putExtra("detalle", person.getDetalle());
                context.startActivity(intent);
            }
        });                      */

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
                intent.putExtra("nombre", person.getNombres());
                intent.putExtra("precio", person.getPrecio());
               // intent.putExtra("detalle", person.getDetalle());
                intent.putExtra("imagen", person.getImagen());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return carritos.size();
    }

    public void removeItem(int position) {
        carritos.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animatidons
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }


/*    @Override
    public int getItemCount() {

        return this.carritos.size();
    }
  */
}
