package com.example.abctrabajo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.abctrabajo.entidades.Material;
import com.example.abctrabajo.entidades.Pedido;

import java.util.ArrayList;
import java.util.Calendar;

public class DbPedido extends  DbHelper {
    Context context;
    public DbPedido(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarPedido(String CodigoMaterial ,String HoraPedido,String HoraEntrega ,String Estado ,int Cantidad) {

        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("CodigoMaterial",CodigoMaterial);
            contentValues.put("HoraPedido",HoraPedido);
            contentValues.put("HoraEntregaMaterial",HoraEntrega);
            contentValues.put("Estado", Estado);
            contentValues.put("Cantidad", Cantidad);

            id = db.insert(TABLE_PEDIDO ,null ,contentValues);
        }catch (Exception e){
            e.toString();
        }


        return  id;

    }

    public ArrayList<Pedido> MostrarPedidos(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Pedido> listarMateriales = new ArrayList<>();
        Pedido pedido = null;
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM "+TABLE_PEDIDO + " p,"+TABLE_MATERIAL +" m where m.NuMaterial = P.CodigoMaterial" ,null);

        if(cursor.moveToFirst()){
            do {
                pedido = new Pedido();
                pedido.setMaterial(cursor.getString(0));
                pedido.setHoraPedido(cursor.getString(1));
                pedido.setHoraEntrega(cursor.getString(2));
                pedido.setEstado(cursor.getString(3));

                pedido.setCantidad(cursor.getInt(4));

                pedido.setId(cursor.getInt(5));

                pedido.setTiendita(cursor.getString(7));
                pedido.setAlmacen(cursor.getString(8));
                pedido.setDescripcion(cursor.getString(9));





                listarMateriales.add(pedido);
            }while (cursor.moveToNext());
        }

        cursor.close();

        return listarMateriales;
    }


    public boolean eliminarPedidos(){
        Boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM "+TABLE_PEDIDO);
            correcto = true;
        }catch (Exception e){
            correcto = false;
        }

        return correcto;
    }

    public boolean verificarCodigoMaterial(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = null;



        return true;
    }

    public boolean updateTablePedido(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());


        db.execSQL("UPDATE "+TABLE_PEDIDO + " SET HoraEntregaMaterial = '"+mydate+"' ,Estado = 'ENTREGADO' WHERE Id= '"+id+"'");

        return true;
    }



}
