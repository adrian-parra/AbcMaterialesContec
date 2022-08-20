package com.example.abctrabajo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.abctrabajo.adaptadores.ListaMaterialesAdapter;
import com.example.abctrabajo.db.DbMaterial;
import com.example.abctrabajo.entidades.Material;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ConsultarMaterial extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView listaMateriales;

    ArrayList<Material> listArrayMateriales;

    SearchView txtBuscar;

    ListaMaterialesAdapter listaMaterialesAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_material);

        txtBuscar= findViewById(R.id.txtBuscar);

        //ACTIVAR CURSOR EN TXT BUSCAR
        txtBuscar.requestFocus();



        listaMateriales = findViewById(R.id.rvListaMateriales);
        listaMateriales.setLayoutManager(new GridLayoutManager(this,1));

        DbMaterial dbMaterial = new DbMaterial(ConsultarMaterial.this);



        listArrayMateriales = new ArrayList<>();

        listaMaterialesAdapter = new ListaMaterialesAdapter(dbMaterial.MostrarMateriales());
        listaMateriales.setAdapter(listaMaterialesAdapter);

        txtBuscar.setOnQueryTextListener(this);


        //EVENTOS DE BUUTONS FLOATIN

        //AGREGAR MATERIAL
        findViewById(R.id.btn_add_material).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext() ,RejistrarProducto.class);
                startActivity(i);

            }
        });

        //PEDIDOS
        findViewById(R.id.btn_pedidos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext() ,PedidosMaterial.class);
                startActivity(i);
            }
        });

    }




    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        listaMaterialesAdapter.filtrarMaterial(s);
        return false;
    }
}