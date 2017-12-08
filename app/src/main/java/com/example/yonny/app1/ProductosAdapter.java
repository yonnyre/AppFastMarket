package com.example.yonny.app1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yonny on 26/10/2017.
 */

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {

    private List<Producto> productos;
    private Activity activity;

    public ProductosAdapter(Activity activity){
        this.productos = new ArrayList<>();
        this.activity = activity;
    }


    public void setProductos(List<Producto> productos){
        this.productos = productos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView fotoImage;
        public TextView nombreText;
        public TextView precioText;
      //  public TextView codigoText;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImage = (ImageView) itemView.findViewById(R.id.foto_image);
            nombreText = (TextView) itemView.findViewById(R.id.nombre_text);
            precioText = (TextView) itemView.findViewById(R.id.precio_text);
       //     codigoText = (TextView) itemView.findViewById(R.id.codigoProducto);
        }
    }                                              

    @Override
    public ProductosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductosAdapter.ViewHolder viewHolder, int position) {

        final Producto producto = this.productos.get(position);
        final Context context = viewHolder.itemView.getContext();

        viewHolder.nombreText.setText(producto.getNombre());
        viewHolder.precioText.setText("S/. " + producto.getPrecio());

        String url = ApiService.API_BASE_URL + "/images/" + producto.getImagen();
        Picasso.with(viewHolder.itemView.getContext()).load(url).into(viewHolder.fotoImage);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
                intent.putExtra("nombre", producto.getNombre());
                intent.putExtra("precio", producto.getPrecio());
                intent.putExtra("detalle", producto.getDetalles());
                intent.putExtra("imagen", producto.getImagen());
                intent.putExtra("codigo_barras", producto.getCodigo_barras());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.productos.size();
    }

}
