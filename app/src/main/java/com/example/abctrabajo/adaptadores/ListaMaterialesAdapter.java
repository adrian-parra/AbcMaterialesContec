package com.example.abctrabajo.adaptadores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abctrabajo.ConsultarMaterial;
import com.example.abctrabajo.R;
import com.example.abctrabajo.RejistrarProducto;
import com.example.abctrabajo.db.DbHelper;
import com.example.abctrabajo.db.DbPedido;
import com.example.abctrabajo.entidades.Material;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class ListaMaterialesAdapter extends RecyclerView.Adapter<ListaMaterialesAdapter.MaterialViewHolder> {

    ArrayList<Material> listaMateriales;
    ArrayList<Material> listaOriginal;

    public ListaMaterialesAdapter(ArrayList<Material> listaMateriales){
        this.listaMateriales = listaMateriales;

        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaMateriales);

    }
    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_material ,null ,false);
        return  new MaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        holder.ViewMaterial.setText(listaMateriales.get(position).getMaterial());
        holder.ViewTiendita.setText(listaMateriales.get(position).getTiendita());
        holder.ViewAlmacen.setText(listaMateriales.get(position).getAlmacen());
        holder.ViewDescripcion.setText(listaMateriales.get(position).getDescripcion());

        //OBTENER LOS ULTIMOS 4 DIJITOS DE NO. MATERRIAL

        String material = listaMateriales.get(position).getMaterial();

        String ShortMaterial = material.substring(material.length() - 4);
        
        holder.ViewMaterialShort.setText(ShortMaterial);
        
        
        
        //EVENTOS BTN EDITAR MATERIAL
        holder.btnEditarMaterial.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext() , RejistrarProducto.class);

            intent.putExtra("idMaterial" ,listaMateriales.get(position).getMaterial());
            v.getContext().startActivity(intent);

        });

        //EVENTO BTN AGREGAR PEDIDO
        holder.btnAgregarPedido.setOnClickListener(v -> {
            Context context = v.getContext();

            DbPedido dbPedido = new DbPedido(context);
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

            long resul = dbPedido.insertarPedido(listaMateriales.get(position).getMaterial() ,mydate ,"0" , "PENDIENTE",1);

            if(resul > 0){
                Snackbar.make(v, "PEDIDO AGREGADO", Snackbar.LENGTH_SHORT)
                        .setTextColor(context.getResources().getColor(R.color.white))
                        .setBackgroundTint(context.getResources().getColor(R.color.principal))
                        .setDuration(1000)
                        .show();

            }else{
                Snackbar.make(v, "ERROR", Snackbar.LENGTH_LONG)
                        .setTextColor(context.getResources().getColor(R.color.white))
                        .setBackgroundTint(context.getResources().getColor(R.color.red))
                        .show();
            }
        });
    }


    //METODO PARA FILTRAR NO MATERIAL
    public void filtrarMaterial(String txtBuscar){
        int longitud = txtBuscar.length();

        if(longitud == 0){
            listaMateriales.clear();
            listaMateriales.addAll(listaOriginal);

        }else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Material> collection = listaMateriales.stream().filter(i -> i.getMaterial().contains(txtBuscar)).collect(Collectors.toList());

                listaMateriales.clear();

                listaMateriales.addAll(collection);

            }else {
                for(Material material: listaOriginal){
                    if (material.getMaterial().contains(txtBuscar)){
                        listaMateriales.add(material);
                    }
                }
            }
        }


        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        return listaMateriales.size();
    }

    public class MaterialViewHolder extends RecyclerView.ViewHolder {

        TextView ViewMaterial ,ViewTiendita ,ViewAlmacen ,ViewDescripcion ,ViewMaterialShort;
        FloatingActionButton btnEditarMaterial ,btnAgregarPedido;

        public MaterialViewHolder(@NonNull View itemView) {
            super(itemView);

            ViewMaterial = itemView.findViewById(R.id.txtViewMaterial);
            ViewTiendita = itemView.findViewById(R.id.txtViewTiendita);
            ViewAlmacen = itemView.findViewById(R.id.txtViewAlmacen);
            ViewDescripcion = itemView.findViewById(R.id.txtViewDescripcion);
            ViewMaterialShort = itemView.findViewById(R.id.txtViewMaterialShort);
            
            btnAgregarPedido = itemView.findViewById(R.id.btn_float_agregar_pedido);
            btnEditarMaterial = itemView.findViewById(R.id.btn_float_editar_material);



            //EVENTO CLICK DE CARD VIEW

            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context , RejistrarProducto.class);

                    String idMaterial = listaMateriales.get(getAdapterPosition()).getMaterial();

                    showCustomDialog(context ,idMaterial);


                    //intent.putExtra("idMaterial" ,listaMateriales.get(getAdapterPosition()).getMaterial());
                    //context.startActivity(intent);
                }
            });


             */
        }

        //Function to display the custom dialog.
        public void showCustomDialog(Context context ,String idMaterial) {
            final Dialog dialog = new Dialog(context);
            //We have added a title in the custom layout. So let's disable the default title.
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
            dialog.setCancelable(true);
            //Mention the name of the layout of your custom dialog.
            dialog.setContentView(R.layout.alert_pedido);

            //Initializing the views of the dialog.
            MaterialButton btnAgregar = dialog.findViewById(R.id.btnAlerAgregar);
            MaterialButton btnModificar = dialog.findViewById(R.id.btnAlerModificar);


            btnModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(context , RejistrarProducto.class);

                    intent.putExtra("idMaterial" ,idMaterial);
                    context.startActivity(intent);

                    dialog.dismiss();


                }
            });

            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //VERIFICAR REJISTRO EN BD TABLE PEDIDO




                    //INSERTAR PEDIDO A BD


                    DbPedido dbPedido = new DbPedido(context);
                    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                   long resul = dbPedido.insertarPedido(idMaterial ,mydate ,"0" , "PENDIENTE",1);

                   if(resul > 0){
                       Snackbar.make(v, "PEDIDO AGREGADO", Snackbar.LENGTH_LONG)
                               .setTextColor(context.getResources().getColor(R.color.white))
                               .setBackgroundTint(context.getResources().getColor(R.color.principal))
                               .show();

                   }else{
                       Snackbar.make(v, "ERROR", Snackbar.LENGTH_LONG)
                               .setTextColor(context.getResources().getColor(R.color.white))
                               .setBackgroundTint(context.getResources().getColor(R.color.red))
                               .show();
                   }

                   //dialog.dismiss();
                }
            });

            dialog.show();

        }


    }
}
