package com.example.yonny.app1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by Yonny on 05/10/2017.
 */

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.ViewHolder> {
    public List<Perfil> perfils;
    private Activity activity;
    Context context;

    public PerfilAdapter(Activity activity){
        this.perfils = new ArrayList<>();
    }

    public PerfilAdapter(List<Perfil> perfils, Context applicationContext) {
        this.perfils = perfils;
        this.context = applicationContext;
    }




    public void setPerfils(List<Perfil> perfils) {
        this.perfils = perfils;
        notifyDataSetChanged();

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        Button btnEliminar ;
        public ImageView fotoImage;
        public TextView fullname;
        public String codigoBarras;
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
    public PerfilAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perfil, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    public void clearApplications() {
        int size = this.perfils.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                perfils.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public void onBindViewHolder(PerfilAdapter.ViewHolder viewHolder, final int position) {
        final Perfil person = this.perfils.get(position);
        final Context context = viewHolder.itemView.getContext();

        viewHolder.fullname.setText(person.getNombre());
        viewHolder.fullprecio.setText("S/. " +person.getPrecio());
        String url = ApiService.API_BASE_URL + "/images/" + person.getImagen();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(context.getApplicationContext(), DetailActivity.class);
                inte.putExtra("nombre", person.getNombre());
                inte.putExtra("precio", person.getPrecio());
                inte.putExtra("detalle", person.getDetalle());
                inte.putExtra("imagen", person.getImagen());
                inte.putExtra("codigo_barras", person.getCodigo_barras());
                context.startActivity(inte);
            }
        });



        viewHolder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ApiService service = ApiServiceGenerator.createService(ApiService.class);

                Call<ResponseMessage> call = service.destroyProducto(person.getCodigo_barras());

                call.enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        try {

                            int statusCode = response.code();
                            Log.d(TAG, "HTTP status code: " + statusCode);

                            if (response.isSuccessful()) {

                                ResponseMessage responseMessage = response.body();
                                Log.d(TAG, "responseMessage: " + responseMessage);

                                // Eliminar item del recyclerView y notificar cambios
                                perfils.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, perfils.size());

                                Toast.makeText(v.getContext(), responseMessage.getMessage(), Toast.LENGTH_LONG).show();

                            } else {
                                Log.e(TAG, "onError: " + response.errorBody().string());
                                throw new Exception("Error en el servicio");
                            }

                        } catch (Throwable t) {
                            try {
                                Log.e(TAG, "onThrowable: " + t.toString(), t);
                                Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }catch (Throwable x){}
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.toString());
                        Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });

            }
        });


    }


    @Override
    public int getItemCount() {
        return perfils == null ? 0 : perfils.size();
    }

}
