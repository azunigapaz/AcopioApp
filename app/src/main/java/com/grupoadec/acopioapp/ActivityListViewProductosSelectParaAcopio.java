package com.grupoadec.acopioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Adaptadores.ListaProductosAdapter;
import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;
import com.grupoadec.acopioapp.Models.TablaProductos;

import java.util.ArrayList;

public class ActivityListViewProductosSelectParaAcopio extends AppCompatActivity {

    // declaracion de variables
    SQLiteConexion objectSqLiteConexion;
    ListView objectListViewConsultaProductos;
    ArrayList<TablaProductos> objectArrayListTablaProductosLista = new ArrayList<>();
    ArrayList<String> objectArrayListStringProductos;
    TablaProductos objectTablaProveedoresListaProductos = null;
    EditText objectEditTextBusquedaproductosparaacopio_input;
    ImageView btnvolveractivitymain;
    ListaProductosAdapter objectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_productos_select_para_acopio);

        try{
            objectSqLiteConexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
            objectListViewConsultaProductos = (ListView) findViewById(R.id.productos_listview);
            objectEditTextBusquedaproductosparaacopio_input = (EditText) findViewById(R.id.busquedaproductosparaacopio_input);
            btnvolveractivitymain = (ImageView) findViewById(R.id.btnvolveractivitymain);

            // llenamos variables con los datos del putExtra
            String parPeNombres = getIntent().getStringExtra("peNombre");
            String parPeApellidos = getIntent().getStringExtra("peApellidos");
            String parPeCorreo = getIntent().getStringExtra("peCorreo");

            String parPeAccesoBajarDatos = getIntent().getStringExtra("iPeAccesoBajarDatos");
            String parPeAccesoSubirDatos = getIntent().getStringExtra("iPeAccesoSubirDatos");
            String parPeAccesoConfiguracion = getIntent().getStringExtra("iPeAccesoConfiguracion");
            String parPeAccesoRegistroAcopio = getIntent().getStringExtra("iPeAccesoRegistroAcopio");
            String parPeAccesoRegistroProductores = getIntent().getStringExtra("iPeAccesoRegistroProductores");

            String parPeProveedorClave = getIntent().getStringExtra("iptProveedorClave");
            String parPeProveedorNombre = getIntent().getStringExtra("iptProveedorNombre");
            String parPeProveedorRtn = getIntent().getStringExtra("iptProveedorRtn");

            ObtenerListaProductos();

            objectAdapter = new ListaProductosAdapter(this,objectArrayListTablaProductosLista);
            objectListViewConsultaProductos.setAdapter(objectAdapter);

            objectEditTextBusquedaproductosparaacopio_input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    objectAdapter.filtrarProductoDescripcionCodigo(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            btnvolveractivitymain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent objectIntent = new Intent(getApplicationContext(),ActivityMainAcopio.class);

                    objectIntent.putExtra("iPeNombres", parPeNombres);
                    objectIntent.putExtra("iPeApellidos", parPeApellidos);
                    objectIntent.putExtra("iPeCorreo", parPeCorreo);
                    objectIntent.putExtra("iPeAccesoConfiguracion", parPeAccesoConfiguracion);
                    objectIntent.putExtra("iPeAccesoBajarDatos", parPeAccesoBajarDatos);
                    objectIntent.putExtra("iPeAccesoSubirDatos", parPeAccesoSubirDatos);
                    objectIntent.putExtra("iPeAccesoRegistroProductores", parPeAccesoRegistroProductores);
                    objectIntent.putExtra("iPeAccesoRegistroAcopio", parPeAccesoRegistroAcopio);

                    objectIntent.putExtra("iptProveedorClave", parPeProveedorClave);
                    objectIntent.putExtra("iptProveedorNombre", parPeProveedorNombre);
                    objectIntent.putExtra("iptProveedorRtn", parPeProveedorRtn);

                    startActivity(objectIntent);
                    finish();
                }
            });

            objectListViewConsultaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TablaProductos tp = objectArrayListTablaProductosLista.get(i);
                    Intent objectIntent = new Intent(getApplicationContext(),ActivityAgregarProductosParaAcopio.class);
                    objectIntent.putExtra("iptProductoClave", tp.getProductoClave());
                    objectIntent.putExtra("iptProductoNombre", tp.getProductoDescripcion());
                    objectIntent.putExtra("iptProductoCosto", tp.getProductoCosto().toString());

                    objectIntent.putExtra("iPeNombres", parPeNombres);
                    objectIntent.putExtra("iPeApellidos", parPeApellidos);
                    objectIntent.putExtra("iPeCorreo", parPeCorreo);
                    objectIntent.putExtra("iPeAccesoConfiguracion", parPeAccesoConfiguracion);
                    objectIntent.putExtra("iPeAccesoBajarDatos", parPeAccesoBajarDatos);
                    objectIntent.putExtra("iPeAccesoSubirDatos", parPeAccesoSubirDatos);
                    objectIntent.putExtra("iPeAccesoRegistroProductores", parPeAccesoRegistroProductores);
                    objectIntent.putExtra("iPeAccesoRegistroAcopio", parPeAccesoRegistroAcopio);

                    objectIntent.putExtra("iptProveedorClave", parPeProveedorClave);
                    objectIntent.putExtra("iptProveedorNombre", parPeProveedorNombre);
                    objectIntent.putExtra("iptProveedorRtn", parPeProveedorRtn);

                    startActivity(objectIntent);
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void ObtenerListaProductos() {
        SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getReadableDatabase();

        Cursor objectCursor = objectSqLiteDatabase.rawQuery("SELECT * FROM " + Transacciones.tablaproductos, null);

        while (objectCursor.moveToNext()){
            objectTablaProveedoresListaProductos = new TablaProductos();
            objectTablaProveedoresListaProductos.setProductoClave(objectCursor.getString(0));
            objectTablaProveedoresListaProductos.setProductoDescripcion(objectCursor.getString(1));
            objectTablaProveedoresListaProductos.setProductoCosto(objectCursor.getDouble(2));
            objectTablaProveedoresListaProductos.setProductoLinea(objectCursor.getString(3));

            objectArrayListTablaProductosLista.add(objectTablaProveedoresListaProductos);
        }

        objectCursor.close();
        objectSqLiteConexion.close();

    }

}