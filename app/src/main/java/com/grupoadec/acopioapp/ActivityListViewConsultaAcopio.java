package com.grupoadec.acopioapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Adaptadores.ListaAcopiosAdapter;
import com.grupoadec.acopioapp.Adaptadores.ListaProductoresAdapter;
import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;
import com.grupoadec.acopioapp.Models.TablaConsultaAcopio;
import com.grupoadec.acopioapp.Models.TablaProveedores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class ActivityListViewConsultaAcopio extends AppCompatActivity {
    //declaracion de variables globales
    SQLiteConexion objectSqLiteConexion;
    ListView acopio_listview;
    ArrayList<TablaConsultaAcopio> objectArrayListTablaConsultaAcopioLista = new ArrayList<>();
    TablaConsultaAcopio objectTablaConsultaAcopioListaAcopios;
    ListaAcopiosAdapter objectAdapter;

    String [] objectListItem;
    AlertDialog.Builder objectAlertDialogBuilderOpciones;

    ImageView btnvolveractivitymainacopio;
    EditText consultaracopios_input;

    String parPeProveedorClave;
    String parPeProveedorNombre;
    String parPeProveedorRtn;

    String parPeAlmacenClave;
    String parPeAlmacenDescripcion;

    // variables para obtener fecha y hora actual
    Calendar objectCalendar;
    android.icu.text.SimpleDateFormat objectSimpleDateFormatFechaDocumento;
    String fechaActual, fechaInicialFiltro;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_consulta_acopio);

        try{
            objectSqLiteConexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
            btnvolveractivitymainacopio = (ImageView) findViewById(R.id.btnvolveractivitymainacopio);
            acopio_listview = (ListView) findViewById(R.id.acopio_listview);
            consultaracopios_input = (EditText) findViewById(R.id.consultaracopios_input);

            objectAlertDialogBuilderOpciones = new AlertDialog.Builder(this);

            // llenamos variables con los datos del putExtra
            String parPeNombres = getIntent().getStringExtra("peNombre");
            String parPeApellidos = getIntent().getStringExtra("peApellidos");
            String parPeCorreo = getIntent().getStringExtra("peCorreo");

            String parPeAccesoBajarDatos = getIntent().getStringExtra("iPeAccesoBajarDatos");
            String parPeAccesoSubirDatos = getIntent().getStringExtra("iPeAccesoSubirDatos");
            String parPeAccesoConfiguracion = getIntent().getStringExtra("iPeAccesoConfiguracion");
            String parPeAccesoRegistroAcopio = getIntent().getStringExtra("iPeAccesoRegistroAcopio");
            String parPeAccesoRegistroProductores = getIntent().getStringExtra("iPeAccesoRegistroProductores");
            String parPeValidacionNuevaFactura = getIntent().getStringExtra("iPeNuevaFactura");

            parPeProveedorClave = getIntent().getStringExtra("iptProveedorClave");

            objectCalendar = Calendar.getInstance();
            objectSimpleDateFormatFechaDocumento = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
            fechaActual = objectSimpleDateFormatFechaDocumento.format(objectCalendar.getTime());
            objectCalendar.add(Calendar.MONTH, -2);
            fechaInicialFiltro = objectSimpleDateFormatFechaDocumento.format(objectCalendar.getTime());

            ObtenerListaAcopios();

            objectAdapter = new ListaAcopiosAdapter(this,objectArrayListTablaConsultaAcopioLista);
            acopio_listview.setAdapter(objectAdapter);

            btnvolveractivitymainacopio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent objectIntent = new Intent(getApplicationContext(),ActivityListViewProveedoresSelect.class);

                    objectIntent.putExtra("iPeNombres", parPeNombres);
                    objectIntent.putExtra("iPeApellidos", parPeApellidos);
                    objectIntent.putExtra("iPeCorreo", parPeCorreo);
                    objectIntent.putExtra("iPeAccesoConfiguracion", parPeAccesoConfiguracion);
                    objectIntent.putExtra("iPeAccesoBajarDatos", parPeAccesoBajarDatos);
                    objectIntent.putExtra("iPeAccesoSubirDatos", parPeAccesoSubirDatos);
                    objectIntent.putExtra("iPeAccesoRegistroProductores", parPeAccesoRegistroProductores);
                    objectIntent.putExtra("iPeAccesoRegistroAcopio", parPeAccesoRegistroAcopio);

                    objectIntent.putExtra("ipeAlmacenClave", parPeAlmacenClave);
                    objectIntent.putExtra("ipeAlmacenDescripcion", parPeAlmacenDescripcion);

                    startActivity(objectIntent);
                    finish();
                }
            });


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void ObtenerListaAcopios() {
        try{
            SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getReadableDatabase();

            String ConsultaSql;
            Cursor objectCursor;

            ConsultaSql = "SELECT A.CompraEncabezadoDocumento,A.CompraEncabezadoFecha,A.CompraEncabezadoTotal," +
                    "C.ProveedorNombre "+
                    " FROM " + Transacciones.tablacomprasencabezado + " A " +
                    " INNER JOIN "+Transacciones.tablaproveedores+" C ON C."+Transacciones.ProveedorClave+"=A."+Transacciones.CompraEncabezadoProveedorClave+
                    " WHERE A.CompraEncabezadoProveedorClave = '" + parPeProveedorClave + "' AND A.CompraEncabezadoFecha >='" + fechaInicialFiltro +"' AND A.CompraEncabezadoFecha <='" + fechaActual + "'";

            objectCursor = objectSqLiteDatabase.rawQuery(ConsultaSql,null);

            while (objectCursor.moveToNext()){
                objectTablaConsultaAcopioListaAcopios = new TablaConsultaAcopio();
                objectTablaConsultaAcopioListaAcopios.setAcopioDocumento(objectCursor.getString(0));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                objectTablaConsultaAcopioListaAcopios.setAcopioFecha(sdf.parse(objectCursor.getString(1)));

                objectTablaConsultaAcopioListaAcopios.setAcopioImporte(objectCursor.getDouble(2));
                objectTablaConsultaAcopioListaAcopios.setAcopioNombreProductor(objectCursor.getString(3));


                objectArrayListTablaConsultaAcopioLista.add(objectTablaConsultaAcopioListaAcopios);
            }

            objectCursor.close();
            objectSqLiteConexion.close();
        }catch (Exception e){
            Toast.makeText(this, "Error al obtener la lista: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}