package com.grupoadec.acopioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;
import com.grupoadec.acopioapp.Models.TablaAlmacenes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class ActivityAgregarProductosParaAcopio extends AppCompatActivity {
    Button btnagregarproducto;
    ImageView btnvolveractivityproductos;
    TextView nombreproductoparaacopio_input;
    EditText cantidadproductoparaacopio_input, precioconfiguracion_input;
    SQLiteConexion conexion;
    String parPeProductoClave,parPeProductoNombre,parPeProductoCosto;
    Double calculoSubTotalPartida;

    ArrayList<String> objectArrayListStringAlmacenes;
    ArrayList<TablaAlmacenes> objectArrayListAlmacenesLista;

    String parPeAlmacenClave;
    String parPeAlmacenDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_productos_para_acopio);

        try{
            conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null, 1);
            btnagregarproducto = (Button) findViewById(R.id.btnagregarproducto);
            btnvolveractivityproductos = (ImageView) findViewById(R.id.btnvolveractivityproductos);
            nombreproductoparaacopio_input = (TextView) findViewById(R.id.nombreproductoparaacopio_input);
            cantidadproductoparaacopio_input = (EditText) findViewById(R.id.cantidadproductoparaacopio_input);
            precioconfiguracion_input = (EditText) findViewById(R.id.precioconfiguracion_input);

            // llenamos variables con los datos del putExtra
            String parPeNombres = getIntent().getStringExtra("iPeNombres");
            String parPeApellidos = getIntent().getStringExtra("iPeApellidos");
            String parPeCorreo = getIntent().getStringExtra("iPeCorreo");

            String parPeAccesoBajarDatos = getIntent().getStringExtra("iPeAccesoBajarDatos");
            String parPeAccesoSubirDatos = getIntent().getStringExtra("iPeAccesoSubirDatos");
            String parPeAccesoConfiguracion = getIntent().getStringExtra("iPeAccesoConfiguracion");
            String parPeAccesoRegistroAcopio = getIntent().getStringExtra("iPeAccesoRegistroAcopio");
            String parPeAccesoRegistroProductores = getIntent().getStringExtra("iPeAccesoRegistroProductores");

            String parPeProveedorClave = getIntent().getStringExtra("iptProveedorClave");
            String parPeProveedorNombre = getIntent().getStringExtra("iptProveedorNombre");
            String parPeProveedorRtn = getIntent().getStringExtra("iptProveedorRtn");

            parPeProductoClave = getIntent().getStringExtra("iptProductoClave");
            parPeProductoNombre = getIntent().getStringExtra("iptProductoNombre");
            parPeProductoCosto = getIntent().getStringExtra("iptProductoCosto");

            parPeAlmacenClave = getIntent().getStringExtra("ipeAlmacenClave");
            parPeAlmacenDescripcion = getIntent().getStringExtra("ipeAlmacenDescripcion");

            nombreproductoparaacopio_input.setText(parPeProductoNombre);
            precioconfiguracion_input.setText(parPeProductoCosto);

            // Llenamos el Spinner Almacenes
            ObtenerListaAlmacenes();

            btnvolveractivityproductos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent objectIntent = new Intent(getApplicationContext(),ActivityMainAcopio.class);

                    objectIntent.putExtra("iptProveedorClave", parPeProveedorClave);
                    objectIntent.putExtra("iptProveedorNombre", parPeProveedorNombre);
                    objectIntent.putExtra("iptProveedorRtn", parPeProveedorRtn);

                    objectIntent.putExtra("iPeNombres", parPeNombres);
                    objectIntent.putExtra("iPeApellidos", parPeApellidos);
                    objectIntent.putExtra("iPeCorreo", parPeCorreo);
                    objectIntent.putExtra("iPeAccesoConfiguracion", parPeAccesoConfiguracion);
                    objectIntent.putExtra("iPeAccesoBajarDatos", parPeAccesoBajarDatos);
                    objectIntent.putExtra("iPeAccesoSubirDatos", parPeAccesoSubirDatos);
                    objectIntent.putExtra("iPeAccesoRegistroProductores", parPeAccesoRegistroProductores);
                    objectIntent.putExtra("iPeAccesoRegistroAcopio", parPeAccesoRegistroAcopio);

                    objectIntent.putExtra("iPeNuevaFactura", "0");

                    objectIntent.putExtra("ipeAlmacenClave", parPeAlmacenClave);
                    objectIntent.putExtra("ipeAlmacenDescripcion", parPeAlmacenDescripcion);

                    startActivity(objectIntent);
                    finish();
                }
            });

            btnagregarproducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        if(cantidadproductoparaacopio_input.getText().length()>0 && precioconfiguracion_input.getText().length()>0){
                            Intent objectIntent = new Intent(getApplicationContext(),ActivityMainAcopio.class);

                            objectIntent.putExtra("iptProveedorClave", parPeProveedorClave);
                            objectIntent.putExtra("iptProveedorNombre", parPeProveedorNombre);
                            objectIntent.putExtra("iptProveedorRtn", parPeProveedorRtn);

                            objectIntent.putExtra("iPeNombres", parPeNombres);
                            objectIntent.putExtra("iPeApellidos", parPeApellidos);
                            objectIntent.putExtra("iPeCorreo", parPeCorreo);
                            objectIntent.putExtra("iPeAccesoConfiguracion", parPeAccesoConfiguracion);
                            objectIntent.putExtra("iPeAccesoBajarDatos", parPeAccesoBajarDatos);
                            objectIntent.putExtra("iPeAccesoSubirDatos", parPeAccesoSubirDatos);
                            objectIntent.putExtra("iPeAccesoRegistroProductores", parPeAccesoRegistroProductores);
                            objectIntent.putExtra("iPeAccesoRegistroAcopio", parPeAccesoRegistroAcopio);

                            objectIntent.putExtra("iptProductoClave", parPeProductoClave);
                            objectIntent.putExtra("iptProductoNombre", parPeProductoNombre);
                            objectIntent.putExtra("iptProductoCosto", precioconfiguracion_input.getText());
                            objectIntent.putExtra("iptProductoCantidad", cantidadproductoparaacopio_input.getText());

                            calculoSubTotalPartida = Double.parseDouble(String.format(cantidadproductoparaacopio_input.getText().toString(), "%.2f")) * Double.parseDouble(String.format(precioconfiguracion_input.getText().toString(), "%.2f"));

                            objectIntent.putExtra("iptSubTotalPartida", calculoSubTotalPartida.toString());

                            objectIntent.putExtra("iPeNuevaFactura", "0");

                            objectIntent.putExtra("ipeAlmacenClave", parPeAlmacenClave);
                            objectIntent.putExtra("ipeAlmacenDescripcion", parPeAlmacenDescripcion);

                            AgregarProducto();

                            startActivity(objectIntent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Debe establecer una cantidad y un precio", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void AgregarProducto() {

        try{
            SQLiteDatabase db = conexion.getWritableDatabase();

            Integer AcopioPartidaNo = 1;

            String consultaSql = "SELECT * FROM tblacopiopartidatmp";
            Cursor cursor = db.rawQuery(consultaSql, null);

            if(cursor.moveToNext()){
                AcopioPartidaNo = 2;
                while (cursor.moveToNext()){
                    AcopioPartidaNo++;
                }
            }

            ContentValues valores = new ContentValues();
            valores.put(Transacciones.AcopioPartidaNo, AcopioPartidaNo);
            valores.put(Transacciones.AcopioPartidaProductoClave, parPeProductoClave);
            valores.put(Transacciones.AcopioPartidaProductoDescripcion, parPeProductoNombre);
            valores.put(Transacciones.AcopioPartidaProductoCantidad, Double.parseDouble(cantidadproductoparaacopio_input.getText().toString()));
            valores.put(Transacciones.AcopioPartidaProductoPrecio, Double.parseDouble(precioconfiguracion_input.getText().toString()));
            valores.put(Transacciones.AcopioPartidaProductoSubTotal, Double.parseDouble(String.format(calculoSubTotalPartida.toString(), "%.2f")));

            Long resultado = db.insert(Transacciones.tablaacopiopartidatmp, null, valores);

            if(resultado != -1){
                //Toast.makeText(getApplicationContext(),"Se agrego el producto: " + parPeProductoClave + " " + parPeProductoNombre,Toast.LENGTH_SHORT).show();
                db.close();
            }else{
                Toast.makeText(getApplicationContext(),"No se agrego el producto ",Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    private void ObtenerListaAlmacenes() {
        SQLiteDatabase objectSqLiteDatabase = conexion.getReadableDatabase();
        TablaAlmacenes objectTablaAlmacenesLista = null;
        objectArrayListAlmacenesLista = new ArrayList<TablaAlmacenes>();

        Cursor objectCursor = objectSqLiteDatabase.rawQuery("SELECT * FROM " + Transacciones.tablaalmacenes, null);

        while (objectCursor.moveToNext()){
            objectTablaAlmacenesLista = new TablaAlmacenes();
            objectTablaAlmacenesLista.setAlmacenClave(objectCursor.getInt(0));
            objectTablaAlmacenesLista.setAlmacenDescripcion(objectCursor.getString(1));

            objectArrayListAlmacenesLista.add(objectTablaAlmacenesLista);
        }

        objectCursor.close();

        LlenarspinnerSelectAlmacen();
    }

    private void LlenarspinnerSelectAlmacen() {
        objectArrayListStringAlmacenes = new ArrayList<String>();
        for(int i = 0; i < objectArrayListAlmacenesLista.size(); i++){
            objectArrayListStringAlmacenes.add(objectArrayListAlmacenesLista.get(i).getAlmacenClave().toString() + " | " +
                    objectArrayListAlmacenesLista.get(i).getAlmacenDescripcion());
        }
    }


}