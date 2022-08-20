package com.example.abctrabajo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.abctrabajo.adaptadores.ListaPedidosMaterialesAdapter;
import com.example.abctrabajo.db.DbPedido;
import com.example.abctrabajo.entidades.Pedido;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class PedidosMaterial extends AppCompatActivity {
    RecyclerView listaPedidos;

    ArrayList<Pedido> listaPedidoArray;

    ListaPedidosMaterialesAdapter listaPedidosMaterialesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_material);


        listaPedidos = findViewById(R.id.rvListaPedidos);
        listaPedidos.setLayoutManager(new GridLayoutManager(this,1));

        DbPedido dbPedido = new DbPedido(PedidosMaterial.this);


        listaPedidoArray = new ArrayList<>();


        listaPedidosMaterialesAdapter = new ListaPedidosMaterialesAdapter(dbPedido.MostrarPedidos());

        listaPedidos.setAdapter(listaPedidosMaterialesAdapter);



        findViewById(R.id.btn_float_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dbPedido.eliminarPedidos()){
                    Snackbar.make(v, "PEDIDOS ELIMINADOS", Snackbar.LENGTH_LONG)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.principal))
                            .show();
                            listaPedidosMaterialesAdapter.update();

                }else {
                    Snackbar.make(v, "error", Snackbar.LENGTH_LONG)
                            .setTextColor(getResources().getColor(R.color.white))
                            .setBackgroundTint(getResources().getColor(R.color.red))
                            .show();
                }
            }
        });



    }
}