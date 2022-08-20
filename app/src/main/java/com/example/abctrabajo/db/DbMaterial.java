package com.example.abctrabajo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.abctrabajo.entidades.Material;

import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

public class DbMaterial extends DbHelper {

    Context context;
    public DbMaterial(@Nullable Context context) {
        super(context);

        this.context = context;
    }


    public long insertarMaterial(String NoMaterial ,String LoTiendita ,String LoAlmacen , String Descripcion) {
        long id = 0;
       try {
           DbHelper dbHelper = new DbHelper(context);
           SQLiteDatabase db = dbHelper.getWritableDatabase();

           ContentValues contentValues = new ContentValues();
           contentValues.put("NuMaterial" ,NoMaterial);
           contentValues.put("LoTiendita",LoTiendita);
           contentValues.put("LoAlmacen",LoAlmacen);
           contentValues.put("Descripcion",Descripcion);

           id = db.insert(TABLE_MATERIAL ,null ,contentValues);
       }catch (Exception e){
           e.toString();
       }


       return  id;
    }

    public ArrayList<Material> MostrarMateriales(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Material> listarMateriales = new ArrayList<>();
        Material material = null;
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM "+TABLE_MATERIAL ,null);

        if(cursor.moveToFirst()){
            do {
                material = new Material();
                material.setMaterial(cursor.getString(0));
                material.setTiendita(cursor.getString(1));
                material.setAlmacen(cursor.getString(2));
                material.setDescripcion(cursor.getString(3));

                listarMateriales.add(material);
            }while (cursor.moveToNext());
        }

        cursor.close();

        return listarMateriales;
    }


    public Material VerMaterial(String IdMaterial){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        Material material = null;
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM "+TABLE_MATERIAL + " WHERE NuMaterial = "+ IdMaterial +" LIMIT 1" ,null);

        if(cursor.moveToFirst()){

                material = new Material();
                material.setMaterial(cursor.getString(0));
                material.setTiendita(cursor.getString(1));
                material.setAlmacen(cursor.getString(2));
                material.setDescripcion(cursor.getString(3));



        }

      cursor.close();

        return material;
    }

    public boolean editarMaterial(String NoMaterial ,String LoTiendita ,String LoAlmacen , String Descripcion) {
        Boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {

            /*
            ContentValues contentValues = new ContentValues();
            contentValues.put("NuMaterial" ,NoMaterial);
            contentValues.put("LoTiendita",LoTiendita);
            contentValues.put("LoAlmacen",LoAlmacen);
            contentValues.put("Descripcion",Descripcion);

             */

            //db.update(TABLE_MATERIAL ,contentValues, "NuMaterial=?",new String[]{NoMaterial});
            db.execSQL("UPDATE "+TABLE_MATERIAL + " SET NuMaterial = '"+NoMaterial+"', LoTiendita = '"+LoTiendita+"',LoAlmacen = '"+LoAlmacen+"',Descripcion = '"+Descripcion+"' WHERE NuMaterial = '"+NoMaterial+"'");




            correcto = true;
        }catch (Exception e){
            e.toString();
            correcto = false;
        }finally {
           // db.close();
        }


        return correcto;
    }

    public boolean eliminarMaterial(String NoMaterial){
        Boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM "+TABLE_MATERIAL + " WHERE NuMaterial ='"+NoMaterial+"'");
            correcto = true;
        }catch (Exception e){
            correcto = false;
        }

        return correcto;
    }

}
