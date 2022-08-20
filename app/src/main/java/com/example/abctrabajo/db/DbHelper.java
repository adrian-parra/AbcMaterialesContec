package com.example.abctrabajo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "abctrabajo.db";
    public static final String TABLE_MATERIAL = "material";
    public static final String TABLE_PEDIDO = "pedidoMaterial";

    Context context;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table if not exists "+TABLE_MATERIAL+"(NuMaterial varchar primary key NOT NULL,LoTiendita varchar NOT NULL,LoAlmacen varchar NOT NULL,Descripcion varchar NOT NULL)");
        sqLiteDatabase.execSQL("create table if not exists "+TABLE_PEDIDO+"(CodigoMaterial varchar NOT NULL,HoraPedido varchar NOT NULL,HoraEntregaMaterial varchar NOT NULL ,Estado varchar NOT NULL ,Cantidad int not null,Id integer primary key autoincrement not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
