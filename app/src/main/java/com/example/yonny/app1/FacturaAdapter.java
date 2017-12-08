package com.example.yonny.app1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class FacturaAdapter extends RecyclerView.Adapter<FacturaAdapter.ViewHolder> {

    private List<Factura> facturas;
    private Activity activity;
    Context context;


    public FacturaAdapter(Activity activity){
        this.facturas = new ArrayList<>();

    }

    public FacturaAdapter(List<Factura> facList, Context applicationContext){
        this.facturas = facList;
        this.context = applicationContext;
    }

    public FacturaAdapter(PerfilRepository perfilRepository, Context applicationContext) {

    }




    public void setFactura(List<Factura> facturas){
        this.facturas = facturas;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView idfaturaText;
        public TextView fechaText;

        public TextView precioText;

        public ViewHolder(View itemView) {
            super(itemView);
           // fotoImage = (ImageView) itemView.findViewById(R.id.foto_image);

            idfaturaText = (TextView) itemView.findViewById(R.id.idfactura);
            precioText = (TextView) itemView.findViewById(R.id.total);
            fechaText = (TextView) itemView.findViewById(R.id.fechapago);
        }
    }


    @Override
    public FacturaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_factura, parent, false);
        return new ViewHolder(itemView);
    }

    public void clearApplications() {
        int size = this.facturas.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                facturas.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
    @Override
    public void onBindViewHolder(FacturaAdapter.ViewHolder viewHolder, int position) {


        final Factura factura = this.facturas.get(position);
        final Context context = viewHolder.itemView.getContext();

        viewHolder.idfaturaText.setText(factura.getIdfactura());
        viewHolder.precioText.setText("S/ "+factura.getMonto());
        viewHolder.fechaText.setText(factura.getFecha());


        //String url = ApiService.API_BASE_URL + "/images/" + factura.getImagen();
       // Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);

       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), DetalleFactura.class);
                intent.putExtra("idfactura", factura.getIdfactura());
                intent.putExtra("precio", factura.getMonto());
                intent.putExtra("peso", factura.getPeso());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {

        return facturas == null ? 0 : facturas.size();
    }

}
