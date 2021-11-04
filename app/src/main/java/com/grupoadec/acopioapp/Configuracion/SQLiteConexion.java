package com.grupoadec.acopioapp.Configuracion;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


public class SQLiteConexion extends SQLiteOpenHelper {


    public SQLiteConexion(@Nullable Context context, @Nullable String dbname, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, version);
    }

    public SQLiteConexion(@Nullable Context context, @Nullable String dbname, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, dbname, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public SQLiteConexion(@Nullable Context context, @Nullable String dbname, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, dbname, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creamos las tablas de la base de datos
        db.execSQL(Transacciones.CreateTableUsuarios);
        db.execSQL(Transacciones.CreateTableProveedores);
        db.execSQL(Transacciones.CreateTableProductos);
        db.execSQL(Transacciones.CreateTableAlmacenes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // eliminamos las tablas de la base de datos, funcion para limpiar la db
        db.execSQL(Transacciones.DropTableUsuario);
        db.execSQL(Transacciones.DropTableProveedores);
        db.execSQL(Transacciones.DropTableProductos);
        db.execSQL(Transacciones.DropTableAlmacenes);

        // Cramos nuevamente las tablas
        onCreate(db);
    }
}
