package com.example.abctrabajo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.abctrabajo.db.DbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //creacion de la bd
        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //UPDATE DE TABLAS EN LA BD
        dbHelper.onCreate(db);


        if (db == null){
            Toast.makeText(MainActivity.this ,"BASE DE DATOS CREADA",Toast.LENGTH_LONG).show();
        }else{
            //Toast.makeText(MainActivity.this ,"ERROR AL CREAR BASE DE DATOS",Toast.LENGTH_LONG).show();

        }



        findViewById(R.id.btnRegistrarP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registrarProducto = new Intent(getBaseContext() ,RejistrarProducto.class);
                startActivity(registrarProducto);
            }
        });

        findViewById(R.id.btnConsultarMaterial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent consultarMaterial = new Intent(getBaseContext() ,ConsultarMaterial.class);
                startActivity(consultarMaterial);
            }
        });

        findViewById(R.id.btnPedidosMaterial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),PedidosMaterial.class);
                startActivity(intent);


            }
        });
    }
}