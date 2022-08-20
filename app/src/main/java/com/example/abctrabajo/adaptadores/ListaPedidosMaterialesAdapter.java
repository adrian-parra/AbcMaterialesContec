package com.example.abctrabajo.adaptadores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abctrabajo.ConsultarMaterial;
import com.example.abctrabajo.MainActivity;
import com.example.abctrabajo.R;
import com.example.abctrabajo.RejistrarProducto;
import com.example.abctrabajo.db.DbPedido;
import com.example.abctrabajo.entidades.Pedido;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ListaPedidosMaterialesAdapter extends RecyclerView.Adapter<ListaPedidosMaterialesAdapter.PedidosViewHolder> {
   ArrayList<Pedido> listaPedidos;
   Context context;

   public ListaPedidosMaterialesAdapter(ArrayList<Pedido> listaPedidos){
       this.listaPedidos = listaPedidos;



   }

    @NonNull
    @Override
    public PedidosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_pedido ,null ,false);
        return  new PedidosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosViewHolder holder, int position) {
        holder.ViewMaterial.setText(listaPedidos.get(position).getMaterial());
        holder.ViewTiendita.setText(listaPedidos.get(position).getTiendita());
        holder.ViewAlmacen.setText(listaPedidos.get(position).getAlmacen());
        holder.ViewDescripcion.setText(listaPedidos.get(position).getDescripcion());



        holder.ViewEstado.setText(listaPedidos.get(position).getEstado());

        if(holder.ViewEstado.getText().toString().equals("ENTREGADO")){
            holder.ViewEstado.setTextColor(holder.itemView.getResources().getColor(R.color.principal));
        }

        holder.ViewHoraPedido.setText(listaPedidos.get(position).getHoraPedido());
        holder.ViewHoraEntrega.setText(listaPedidos.get(position).getHoraEntrega());

        holder.ViewCantidad.setText(Integer.toString(listaPedidos.get(position).getCantidad()));

        //OBTENER LOS ULTIMOS 4 DIJITOS DE NO. MATERRIAL

        String material = listaPedidos.get(position).getMaterial();

        String ShortMaterial = material.substring(material.length() - 4);

        holder.ViewMaterialShort.setText(ShortMaterial);








    }

    public void update(){
       listaPedidos.clear();
       notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public class PedidosViewHolder extends RecyclerView.ViewHolder {


        TextView ViewMaterial ,ViewTiendita ,ViewAlmacen ,ViewDescripcion  ,ViewHoraPedido ,ViewHoraEntrega,ViewEstado ,ViewCantidad ,ViewMaterialShort;

        public PedidosViewHolder(@NonNull View itemView) {
            super(itemView);

            ViewMaterial = itemView.findViewById(R.id.txtViewMaterial);
            ViewTiendita = itemView.findViewById(R.id.txtViewTiendita);
            ViewAlmacen = itemView.findViewById(R.id.txtViewAlmacen);
            ViewDescripcion = itemView.findViewById(R.id.txtViewDescripcion);

            ViewHoraPedido = itemView.findViewById(R.id.txtViewPedido);
            ViewHoraEntrega = itemView.findViewById(R.id.txtViewHoraEntrega);


            ViewEstado = itemView.findViewById(R.id.txtViewEstado);



            ViewCantidad = itemView.findViewById(R.id.txtViewCantidad);

            ViewMaterialShort = itemView.findViewById(R.id.txtViewMaterialShort);






            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Toast.makeText(view.getContext(), "hola ", Toast.LENGTH_SHORT).show();

                    //Toast.makeText(view.getContext(), Integer.toString(listaPedidos.get(getPosition()).getId()), Toast.LENGTH_SHORT).show();
                    DbPedido dbPedido = new DbPedido(view.getContext());

                    dbPedido.updateTablePedido(listaPedidos.get(getPosition()).getId());

                    if(listaPedidos.get(getPosition()).getEstado().equals("PENDIENTE")){
                        ViewEstado.setText("ENTREGADO");
                        ViewEstado.setTextColor(view.getResources().getColor(R.color.principal));
                    }

                    // intent.putExtra("idMaterial" ,listaMateriales.get(getAdapterPosition()).getMaterial());
                //context.startActivity(intent);
                }
            });



        }



    }
}
