package com.grupoadec.acopioapp.Configuracion;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

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
        db.execSQL(Transacciones.CreateTableConfiguraciones);
        db.execSQL(Transacciones.CreateTableAcopioPartidaTmp);
        // create compras
        db.execSQL(Transacciones.CreateTableComprasEncabezado);
        db.execSQL(Transacciones.CreateTableComprasPartida);
        db.execSQL(Transacciones.CreateTableCuentasPorPagarConceptos);
        db.execSQL(Transacciones.CreateTableCuentasPorPagarEncabezado);
        db.execSQL(Transacciones.CreateTableCuentasPorPagarDetalle);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // eliminamos las tablas de la base de datos, funcion para limpiar la db
        db.execSQL(Transacciones.DropTableUsuario);
        db.execSQL(Transacciones.DropTableProveedores);
        db.execSQL(Transacciones.DropTableProductos);
        db.execSQL(Transacciones.DropTableAlmacenes);
        db.execSQL(Transacciones.DropTableConfiguraciones);
        db.execSQL(Transacciones.DropTableAcopioPartidaTmp);
        // drop compras
        db.execSQL(Transacciones.DropTableComprasEncabezado);
        db.execSQL(Transacciones.DropTableComprasPartida);
        db.execSQL(Transacciones.DropTableCuentasPorPagarConceptos);
        db.execSQL(Transacciones.DropTableCuentasPorPagarEncabezado);
        db.execSQL(Transacciones.DropTableCuentasPorPagarDetalle);

        // Cramos nuevamente las tablas
        onCreate(db);
    }
}
