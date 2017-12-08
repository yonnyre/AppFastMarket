package com.example.yonny.app1;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yonny on 27/11/2017.
 */

public class DetalleFacAdapter extends RecyclerView.Adapter<DetalleFacAdapter.ViewHolder> {

    private List<DetalleFac> facturas;
    private Activity activity;
    Context context;


    public DetalleFacAdapter(Activity activity){
        this.facturas = new ArrayList<>();

    }

    public DetalleFacAdapter(List<DetalleFac> facList, Context applicationContext){
        this.facturas = facList;
        this.context = applicationContext;
    }

  /*  public DetalleFacAdapter(PerfilRepository perfilRepository, Context applicationContext) {

    }  */




    public void setDetalleFac(List<DetalleFac> facturas){
        this.facturas = facturas;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView nombre;
        public TextView precio;
        public TextView peso;
        public TextView codigo_barras;

        public ViewHolder(View itemView) {
            super(itemView);
            // fotoImage = (ImageView) itemView.findViewById(R.id.foto_image);

            nombre = (TextView) itemView.findViewById(R.id.fullname_text);
            precio = (TextView) itemView.findViewById(R.id.precio_text);
            codigo_barras= (TextView) itemView.findViewById(R.id.codigo_text);
            peso = (TextView) itemView.findViewById(R.id.peso_text);
        }
    }


    @Override
    public DetalleFacAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, parent, false);
        return new ViewHolder(itemView);
    }
            /*
    public void clearApplications() {
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                facturas.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }           */
    @Override
    public void onBindViewHolder(DetalleFacAdapter.ViewHolder viewHolder, int position) {


        final DetalleFac factura = this.facturas.get(position);
        final Context context = viewHolder.itemView.getContext();

        viewHolder.nombre.setText(factura.getNombre());
        viewHolder.precio.setText( "S/ "+factura.getMonto());
        viewHolder.codigo_barras.setText("codigo_barras :"+factura.getCodigo_barras());
        viewHolder.peso.setText(factura.getPeso()+"g");


        //String url = ApiService.API_BASE_URL + "/images/" + factura.getImagen();
       // Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);

    }


    @Override
    public int getItemCount() {

        return facturas == null ? 0 : facturas.size();
    }

}
