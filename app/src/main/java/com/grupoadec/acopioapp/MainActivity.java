package com.grupoadec.acopioapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;
import com.grupoadec.acopioapp.Models.TablaAlmacenes;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import android.provider.Settings.Secure;


public class MainActivity extends AppCompatActivity {
    // declaracion de variables
    ImageButton btnSubirDatos,btnRegistroAcopio,btnAccesoConfiguracion,btnBajarDatos;
    ImageView btncerrarsesion;
    //Spinner spinnerSelectAlmacen;

    ArrayList<String> objectArrayListStringAlmacenes;
    ArrayList<TablaAlmacenes> objectArrayListAlmacenesLista;

    ProgressDialog progressDialog;

    RequestQueue requestQueue;

    String dispositivoId,httpUriCfg,apiBajarDatos,apiSubirDatos,apiSubirCompras;
    String HttpURI = "http://192.168.68.105/ApiSaeAppAcopio/assets/php/apicrud.php";
    String HttpURI2 = "http://192.168.68.105/ApiSaeAppAcopio/assets/php/insertarcomprasapi.php";
    Boolean validarConfiguracion = false;

    SQLiteConexion objectSqLiteConexion;

    String AlmacenClave,AlmacenDescripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            // inicializamos variables
            btnSubirDatos = (ImageButton) findViewById(R.id.btnSubirDatos);
            btnRegistroAcopio = (ImageButton) findViewById(R.id.btnRegistroAcopio);
            btnBajarDatos = (ImageButton) findViewById(R.id.btnBajarDatos);
            btnAccesoConfiguracion = (ImageButton) findViewById(R.id.btnConfiguracion);
            btncerrarsesion = (ImageView) findViewById(R.id.btncerrarsesion);

            // inicializamos la conexion
            objectSqLiteConexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);

            // Llenamos el Spinner Almacenes
            ObtenerListaAlmacenes();

            // generar un codigo unico para el app
            String uniqueID = UUID.randomUUID().toString();
            Log.d("Codigo Aleatorio: ",uniqueID);

            dispositivoId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
            Log.d("Codigo Unico: ",dispositivoId);

            // obtener la url
            ObtenerUriConfiguracion();
            // establecemos la uri para cada proceso
            apiBajarDatos = httpUriCfg+"apicrud.php";
            apiSubirDatos = httpUriCfg+"apicrud.php";
            apiSubirCompras = httpUriCfg+"insertarcomprasapi.php";

            // inicializamos requestQueue
            requestQueue = Volley.newRequestQueue(this);

            // inicializamos el progress bar
            progressDialog = new ProgressDialog(this);

            // llenamos variables con los datos del putExtra
            String parPeNombres = getIntent().getStringExtra("peNombre");
            String parPeApellidos = getIntent().getStringExtra("peApellidos");
            String parPeCorreo = getIntent().getStringExtra("peCorreo");

            String parPeAccesoBajarDatos = getIntent().getStringExtra("iPeAccesoBajarDatos");
            String parPeAccesoSubirDatos = getIntent().getStringExtra("iPeAccesoSubirDatos");
            String parPeAccesoConfiguracion = getIntent().getStringExtra("iPeAccesoConfiguracion");
            String parPeAccesoRegistroAcopio = getIntent().getStringExtra("iPeAccesoRegistroAcopio");
            String parPeAccesoRegistroProductores = getIntent().getStringExtra("iPeAccesoRegistroProductores");

            // establecemos permisos al usuario
            if(parPeAccesoBajarDatos.equals("1")){
                btnBajarDatos.setClickable(true);
            }
            if(parPeAccesoSubirDatos.equals("1")){
                btnSubirDatos.setClickable(true);
            }
            if(parPeAccesoRegistroAcopio.equals("1")){
                btnRegistroAcopio.setClickable(true);
            }
            if(parPeAccesoConfiguracion.equals("1")){
                btnAccesoConfiguracion.setClickable(true);
            }

            btnSubirDatos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validarConfiguracion.equals(true)){
                        SubirDatos();
                    }else{
                        Toast.makeText(MainActivity.this,"Debe realizar la configuración del sistema", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            btnBajarDatos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validarConfiguracion.equals(true)){
                        BajarDatos();
                    }else{
                        Toast.makeText(MainActivity.this,"Debe realizar la configuración del sistema", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnAccesoConfiguracion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent objectIntent = new Intent(getApplicationContext(),ActivityConfiguracion.class);

                    objectIntent.putExtra("iPeNombres", parPeNombres);
                    objectIntent.putExtra("iPeApellidos", parPeApellidos);
                    objectIntent.putExtra("iPeCorreo", parPeCorreo);
                    objectIntent.putExtra("iPeAccesoConfiguracion", parPeAccesoConfiguracion);
                    objectIntent.putExtra("iPeAccesoBajarDatos", parPeAccesoBajarDatos);
                    objectIntent.putExtra("iPeAccesoSubirDatos", parPeAccesoSubirDatos);
                    objectIntent.putExtra("iPeAccesoRegistroProductores", parPeAccesoRegistroProductores);
                    objectIntent.putExtra("iPeAccesoRegistroAcopio", parPeAccesoRegistroAcopio);

                    startActivity(objectIntent);
                    finish();
                }
            });

            btnRegistroAcopio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(validarConfiguracion.equals(true)){

                        Spinner selector = new Spinner(MainActivity.this);
                        selector.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(final AdapterView<?> adapterView, View view, final int i, long l) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlmacenClave = objectArrayListAlmacenesLista.get(i).getAlmacenClave().toString();
                                        AlmacenDescripcion = objectArrayListAlmacenesLista.get(i).getAlmacenDescripcion();
                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_spinner_item,
                                objectArrayListStringAlmacenes);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        selector.setAdapter(dataAdapter);
                        dataAdapter.notifyDataSetChanged();

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        dialogBuilder.setView(selector);
                        dialogBuilder.setTitle("Selecciones un Almacen");
                        dialogBuilder.setCancelable(false)
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if(selector.getSelectedItem().toString().length()>0){
                                            // Si el usuario confirma
                                            Intent objectIntent = new Intent(getApplicationContext(),ActivityListViewProveedoresSelect.class);

                                            objectIntent.putExtra("iPeNombres", parPeNombres);
                                            objectIntent.putExtra("iPeApellidos", parPeApellidos);
                                            objectIntent.putExtra("iPeCorreo", parPeCorreo);
                                            objectIntent.putExtra("iPeAccesoConfiguracion", parPeAccesoConfiguracion);
                                            objectIntent.putExtra("iPeAccesoBajarDatos", parPeAccesoBajarDatos);
                                            objectIntent.putExtra("iPeAccesoSubirDatos", parPeAccesoSubirDatos);
                                            objectIntent.putExtra("iPeAccesoRegistroProductores", parPeAccesoRegistroProductores);
                                            objectIntent.putExtra("iPeAccesoRegistroAcopio", parPeAccesoRegistroAcopio);

                                            objectIntent.putExtra("ipeAlmacenClave", AlmacenClave);
                                            objectIntent.putExtra("ipeAlmacenDescripcion", AlmacenDescripcion);

                                            //Toast.makeText(MainActivity.this,"Ha seleccionado el almacen: " + AlmacenClave + " " + AlmacenDescripcion, Toast.LENGTH_SHORT).show();

                                            startActivity(objectIntent);
                                            finish();
                                        }else{
                                            Toast.makeText(MainActivity.this,"Debe seleccionar un almacen", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();

                                    }
                                });
                        //dialogBuilder.setMessage("message");
                        AlertDialog b = dialogBuilder.create();
                        b.show();

                    }else{
                        Toast.makeText(MainActivity.this,"Debe realizar la configuración del sistema", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            btncerrarsesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent objectIntent = new Intent(getApplicationContext(), ActivityLogin.class);
                    startActivity(objectIntent);
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void ObtenerListaAlmacenes() {
        SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getReadableDatabase();
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

    private void ObtenerUriConfiguracion() {
        try{
            SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();
            String ConsultaSql = "SELECT * FROM " + Transacciones.tablaconfiguraciones + " WHERE ConfiguracionId = '" + dispositivoId + "'";

            Cursor objectCursor = objectSqLiteDatabase.rawQuery(ConsultaSql,null);

            if (objectCursor.getCount()!=0){
                while (objectCursor.moveToNext()){
                    httpUriCfg  = objectCursor.getString(3).toString();
                }
                validarConfiguracion = true;
            }else{
                Toast.makeText(getApplicationContext(),"No existe configuración",Toast.LENGTH_SHORT).show();
                validarConfiguracion = false;
            }
            objectCursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    private void SubirDatos() {
        try{

            SQLiteDatabase objectSqLiteDatabase;

            // subir usuarios
            objectSqLiteDatabase = objectSqLiteConexion.getReadableDatabase();
            Cursor objectCursorUsuarios = objectSqLiteDatabase.rawQuery("SELECT * FROM tblusuarios WHERE UsuarioNuevoRegistro=1", null);

            progressDialog.setMessage("Procesando usuarios...");
            progressDialog.show();

            if(objectCursorUsuarios.getCount()!=0){
                // Mostramos el progressDialog
                while (objectCursorUsuarios.moveToNext()){
                    // declaramos variables que llenaremos con los datos obtenidos del cursor
                    final String usuarionombre = objectCursorUsuarios.getString(1);
                    final String usuarioapellido = objectCursorUsuarios.getString(2);
                    final String usuariotelefono = objectCursorUsuarios.getString(3);
                    final String usuariocorreo = objectCursorUsuarios.getString(4);
                    final String usuariocontrasenia = objectCursorUsuarios.getString(5);
                    final String usuarionuevousuario = objectCursorUsuarios.getString(6);
                    final String usuarioaccesoconfiguracion = objectCursorUsuarios.getString(7);
                    final String usuarioaccesobajardatos = objectCursorUsuarios.getString(8);
                    final String usuarioaccesosubirdatos = objectCursorUsuarios.getString(9);
                    final String usuarioaccesoregistroproductores = objectCursorUsuarios.getString(10);
                    final String usuarioaccesoregistroacopio = objectCursorUsuarios.getString(11);
                    final String usuarioestado = objectCursorUsuarios.getString(12);
                    final String usuariofechacreacion = objectCursorUsuarios.getString(13);


                    // creamos la cadena que se enviara al webservice
                    StringRequest stringRequestSubirUsuarios = new StringRequest(Request.Method.POST, apiSubirDatos,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String serverResponse) {
                                    // Recibimos la respuesta del web services
                                    try{
                                        JSONObject jsonObject = new JSONObject(serverResponse);

                                        // obtenemos las variables declaradas en el webservice
                                        String mensajeApi = jsonObject.getString("mensajeactintusuario");
                                        Toast.makeText(getApplicationContext(),mensajeApi,Toast.LENGTH_SHORT).show();

                                        // ocultamos el progress Dialog
                                        progressDialog.dismiss();

                                    }catch (JSONException ex){
                                        ex.printStackTrace();
                                        progressDialog.dismiss();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // si hay algun error por parte de la libreria Voley
                            progressDialog.dismiss();
                            // mostramos el error de la libreria
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                        // Enviamos los datos al web services, con sus respectivos parametros, hacemos un mapeo de un arreglo de 2 dimesiones
                        protected Map<String,String> getParams(){
                            Map<String,String> parametros = new HashMap<>();
                            // parametros que enviaremos al web service
                            parametros.put("opcion", "insertarusuario");

                            parametros.put("usuarionombre", usuarionombre);
                            parametros.put("usuarioapellido", usuarioapellido);
                            parametros.put("usuariotelefono", usuariotelefono);
                            parametros.put("usuariocorreo", usuariocorreo);
                            parametros.put("usuariocontrasenia", usuariocontrasenia);
                            parametros.put("usuarionuevousuario", usuarionuevousuario);
                            parametros.put("usuarioaccesoconfiguracion", usuarioaccesoconfiguracion);
                            parametros.put("usuarioaccesobajardatos", usuarioaccesobajardatos);
                            parametros.put("usuarioaccesosubirdatos", usuarioaccesosubirdatos);
                            parametros.put("usuarioaccesoregistroproductores", usuarioaccesoregistroproductores);
                            parametros.put("usuarioaccesoregistroacopio", usuarioaccesoregistroacopio);
                            parametros.put("usuarioestado", usuarioestado);
                            parametros.put("usuariofechacreacion", usuariofechacreacion);

                            return parametros;
                        }
                    };

                    // ejecutamos la cadena
                    requestQueue.add(stringRequestSubirUsuarios);
                }

                // cerramos el cursor
                objectCursorUsuarios.close();
                // cerramos la conexion
                objectSqLiteDatabase.close();

            }else{
                Toast.makeText(this, "No existen registros de usuarios en la base de datos", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            // subir configuraciones
            objectSqLiteDatabase = objectSqLiteConexion.getReadableDatabase();
            Cursor objectCursorConfiguraciones = objectSqLiteDatabase.rawQuery("SELECT * FROM " + Transacciones.tablaconfiguraciones, null);

            progressDialog.setMessage("Procesando configuraciones...");
            progressDialog.show();

            if(objectCursorConfiguraciones.getCount()!=0){
                // Mostramos el progressDialog
                while (objectCursorConfiguraciones.moveToNext()){
                    // declaramos variables que llenaremos con los datos obtenidos del cursor
                    final String configuracionId = objectCursorConfiguraciones.getString(0);
                    final String configuracionSufijoDocumento = objectCursorConfiguraciones.getString(1);
                    final String configuracionUltimoDocumento = String.valueOf(objectCursorConfiguraciones.getInt(2));
                    final String configuracionUrl = objectCursorConfiguraciones.getString(3);
                    final String configuracionTipoImpresora = objectCursorConfiguraciones.getString(4);

                    // creamos la cadena que se enviara al webservice
                    StringRequest stringRequestSubirConfiguraciones = new StringRequest(Request.Method.POST, apiSubirDatos,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String serverResponse) {
                                    // Recibimos la respuesta del web services
                                    try{
                                        JSONObject jsonObject = new JSONObject(serverResponse);

                                        // obtenemos las variables declaradas en el webservice
                                        String mensajeApi = jsonObject.getString("mensajeactintconfiguraciones");
                                        Toast.makeText(getApplicationContext(),mensajeApi,Toast.LENGTH_SHORT).show();
                                    }catch (JSONException ex){
                                        ex.printStackTrace();
                                        Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // si hay algun error por parte de la libreria Voley
                            progressDialog.dismiss();
                            // mostramos el error de la libreria
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                        // Enviamos los datos al web services, con sus respectivos parametros, hacemos un mapeo de un arreglo de 2 dimesiones
                        protected Map<String,String> getParams(){
                            Map<String,String> parametros = new HashMap<>();
                            // parametros que enviaremos al web service
                            parametros.put("opcion", "insertarconfiguraciones");

                            parametros.put("ConfiguracionId", configuracionId);
                            parametros.put("ConfiguracionSufijoDocumento", configuracionSufijoDocumento);
                            parametros.put("ConfiguracionUltimoDocumento", configuracionUltimoDocumento);
                            parametros.put("ConfiguracionUrl", configuracionUrl);
                            parametros.put("ConfiguracionTipoImpresora", configuracionTipoImpresora);

                            return parametros;
                        }
                    };

                    // ejecutamos la cadena
                    requestQueue.add(stringRequestSubirConfiguraciones);
                }

                // cerramos el cursor
                objectCursorConfiguraciones.close();
                // cerramos la conexion
                objectSqLiteDatabase.close();
                // ocultamos el progress Dialog
                progressDialog.dismiss();

            }else{
                Toast.makeText(this, "No existen registros de configuración en la base de datos", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }


            progressDialog.setMessage("Procesando compras...");
            progressDialog.show();

            // subir JSONObject de compras
            RequestQueue queue = Volley.newRequestQueue(this);
            // creo los objetos Json
            JSONObject jsonObjectEncabezadoCompras;
            JSONObject jsonObjectDocumentoCompras = new JSONObject();
            JSONArray jsonArrayTransaccionCompras = new JSONArray();

            objectSqLiteDatabase = objectSqLiteConexion.getReadableDatabase();
            Cursor objectCursorEncabezadoCompras = objectSqLiteDatabase.rawQuery("SELECT * FROM tblcomprasencabezado WHERE CompraEncabezadoSincronizado = 0" , null);

            if(objectCursorEncabezadoCompras.getCount()!=0){
                int i = 0;
                while (objectCursorEncabezadoCompras.moveToNext()){
                    String documentoCompra = objectCursorEncabezadoCompras.getString(0).toString();
                    jsonObjectEncabezadoCompras = new JSONObject();
                    try {
                        jsonObjectEncabezadoCompras.put("Documento",objectCursorEncabezadoCompras.getString(0));
                        jsonObjectEncabezadoCompras.put("TipoDocumento",objectCursorEncabezadoCompras.getString(1));
                        jsonObjectEncabezadoCompras.put("Proveedor",objectCursorEncabezadoCompras.getString(2));
                        jsonObjectEncabezadoCompras.put("Estado",objectCursorEncabezadoCompras.getString(3));
                        jsonObjectEncabezadoCompras.put("Fecha",objectCursorEncabezadoCompras.getString(4));
                        jsonObjectEncabezadoCompras.put("SubTotal",objectCursorEncabezadoCompras.getDouble(5));
                        jsonObjectEncabezadoCompras.put("Impuesto",objectCursorEncabezadoCompras.getDouble(6));
                        jsonObjectEncabezadoCompras.put("Total",objectCursorEncabezadoCompras.getDouble(7));
                        jsonObjectEncabezadoCompras.put("Almacen",objectCursorEncabezadoCompras.getInt(8));
                        jsonObjectEncabezadoCompras.put("FechaHora",objectCursorEncabezadoCompras.getString(9));

                        // creamos la partida
                        JSONArray jsonArrayPartidaCompras = new JSONArray();

                        Cursor objectCursorPartidaCompras = objectSqLiteDatabase.rawQuery("SELECT * FROM tblcompraspartida WHERE CompraPartidaDocumento = '" + documentoCompra + "' AND CompraPartidaSincronizado = 0", null);
                        if(objectCursorPartidaCompras.getCount()!=0){
                            int j = 0;
                            while (objectCursorPartidaCompras.moveToNext()){
                                JSONObject jsonObjectPartidaCompras = new JSONObject();
                                try{
                                    jsonObjectPartidaCompras.put("TipoDocumento",objectCursorPartidaCompras.getString(0));
                                    jsonObjectPartidaCompras.put("Documento",objectCursorPartidaCompras.getString(1));
                                    jsonObjectPartidaCompras.put("FilaNumero",objectCursorPartidaCompras.getInt(2));
                                    jsonObjectPartidaCompras.put("Producto",objectCursorPartidaCompras.getString(3));
                                    jsonObjectPartidaCompras.put("Cantidad",objectCursorPartidaCompras.getDouble(4));
                                    jsonObjectPartidaCompras.put("Precio",objectCursorPartidaCompras.getDouble(5));
                                    jsonObjectPartidaCompras.put("Impuesto",objectCursorPartidaCompras.getDouble(6));
                                    jsonObjectPartidaCompras.put("TotalPartida",objectCursorPartidaCompras.getDouble(7));
                                    jsonObjectPartidaCompras.put("Almacen",objectCursorPartidaCompras.getInt(8));

                                    jsonArrayPartidaCompras.put(j, jsonObjectPartidaCompras);
                                    j++;
                                }catch (JSONException e){
                                    e.printStackTrace();
                                    progressDialog.dismiss();
                                }
                            }
                        }

                        jsonObjectEncabezadoCompras.put("DetalleCompra",jsonArrayPartidaCompras);
                        objectCursorPartidaCompras.close();

                        // Llenamos el Array con los documentos de compras
                        jsonArrayTransaccionCompras.put(i,jsonObjectEncabezadoCompras);
                        i++;

                    }catch (JSONException e){
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }

            // cerramos el cursor
            objectCursorEncabezadoCompras.close();
            // cerramos la conexion
            objectSqLiteConexion.close();

            // creamos el tag mas la informacion del encabezado
            try{
                // llenamos el objeto json para enviar la peticion
                jsonObjectDocumentoCompras.put("Compra",jsonArrayTransaccionCompras);
            }catch (JSONException e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            // validamos que existan registros por enviar
            if(jsonArrayTransaccionCompras.length()>0){
                JsonObjectRequest jsonObjectRequestCompras = new JsonObjectRequest
                        (Request.Method.POST, apiSubirCompras, jsonObjectDocumentoCompras, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONArray jsonArray = response.getJSONArray("mensaje");

                                    String respuesta = "";
                                    for(int i = 0; i < jsonArray.length(); i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        respuesta = jsonObject.getString("respuestacompras");
                                    }

                                    // actualizamo bandera documento sincronizado
                                    SQLiteDatabase objectSqliteDatabase = objectSqLiteConexion.getWritableDatabase();
                                    String [] params = { "0" };

                                    ContentValues contentValuesUpdateEncabezadoCompras = new ContentValues();
                                    contentValuesUpdateEncabezadoCompras.put(Transacciones.CompraEncabezadoSincronizado, 1);
                                    objectSqliteDatabase.update(Transacciones.tablacomprasencabezado, contentValuesUpdateEncabezadoCompras, Transacciones.CompraEncabezadoSincronizado + "=?", params);

                                    ContentValues contentValuesUpdatePartidaCompras = new ContentValues();
                                    contentValuesUpdatePartidaCompras.put(Transacciones.CompraPartidaSincronizado, 1);
                                    objectSqliteDatabase.update(Transacciones.tablacompraspartida, contentValuesUpdatePartidaCompras, Transacciones.CompraPartidaSincronizado + "=?", params);

                                    objectSqliteDatabase.close();

                                    Toast.makeText(getApplicationContext(),respuesta,Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        });
                requestQueue.add(jsonObjectRequestCompras);
            }else{
                Toast.makeText(getApplicationContext(),"No existen documentos por enviar",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void BajarDatos() {
        try{
            // bajar datos de usuarios

            // Mostramos el progressDialog
            progressDialog.setMessage("Procesando usuarios...");
            progressDialog.show();
            StringRequest stringRequestBajarUsuarios = new StringRequest(Request.Method.POST, apiBajarDatos,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String serverResponse) {
                            // recibimos la respuesta del web services
                            try{

                                JSONObject jsonObject = new JSONObject(serverResponse);
                                JSONArray objectJsonArrayTablaUsuarios = jsonObject.getJSONArray("tablausuarios");

                                SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();

                                Cursor objectCursor;
                                String lsusuarioNombre,lsusuarioApellido,lsusuarioTelefono,lsusuarioCorreo,lsusuarioContrasenia,lsusuarioNuevoRegistro,lsusuarioAccesoConfiguracion,lsusuarioAccesoBajarDatos,lsusuarioAccesoSubirDatos,lsusuarioAccesoRegistroProductores,lsusuarioAccesoRegistroAcopio,lsusuarioEstado,lsusuarioFechaCreacion;
                                String consultaSql;

                                for(int i = 0; i < objectJsonArrayTablaUsuarios.length(); i++){
                                    JSONObject objectJsonUsuarios = objectJsonArrayTablaUsuarios.getJSONObject(i);
                                    lsusuarioNombre = objectJsonUsuarios.getString("UsuarioNombre");
                                    lsusuarioApellido = objectJsonUsuarios.getString("UsuarioApellido");
                                    lsusuarioTelefono = objectJsonUsuarios.getString("UsuarioTelefono");
                                    lsusuarioCorreo = objectJsonUsuarios.getString("UsuarioCorreo");
                                    lsusuarioContrasenia = objectJsonUsuarios.getString("UsuarioContrasenia");
                                    lsusuarioNuevoRegistro = objectJsonUsuarios.getString("UsuarioNuevoRegistro");
                                    lsusuarioAccesoConfiguracion = objectJsonUsuarios.getString("UsuarioAccesoConfiguracion");
                                    lsusuarioAccesoBajarDatos = objectJsonUsuarios.getString("UsuarioAccesoBajarDatos");
                                    lsusuarioAccesoSubirDatos = objectJsonUsuarios.getString("UsuarioAccesoSubirDatos");
                                    lsusuarioAccesoRegistroProductores = objectJsonUsuarios.getString("UsuarioAccesoRegistroProductores");
                                    lsusuarioAccesoRegistroAcopio = objectJsonUsuarios.getString("UsuarioAccesoRegistroAcopio");
                                    lsusuarioEstado = objectJsonUsuarios.getString("UsuarioEstado");
                                    lsusuarioFechaCreacion = objectJsonUsuarios.getString("UsuarioFechaCreacion");

                                    consultaSql = "SELECT * FROM tblusuarios WHERE UsuarioCorreo = '" + lsusuarioCorreo + "'";

                                    objectCursor = objectSqLiteDatabase.rawQuery(consultaSql, null);

                                    if(objectCursor.moveToNext()){
                                        // si el registro existe en el movil, lo actualizamos
                                        String [] parametroWhere = { lsusuarioCorreo };
                                        ContentValues objectContentValuesUpdateUsuarios = new ContentValues();
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioNombre, lsusuarioNombre);
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioApellido, lsusuarioApellido);
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioTelefono, lsusuarioTelefono);
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioContrasenia, lsusuarioContrasenia);
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioNuevoRegistro, lsusuarioNuevoRegistro);
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioAccesoConfiguracion, lsusuarioAccesoConfiguracion);
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioAccesoBajarDatos, lsusuarioAccesoBajarDatos);
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioAccesoSubirDatos, lsusuarioAccesoSubirDatos);
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioAccesoRegistroProductores, lsusuarioAccesoRegistroProductores);
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioAccesoRegistroAcopio, lsusuarioAccesoRegistroAcopio);
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioEstado, lsusuarioEstado);
                                        objectContentValuesUpdateUsuarios.put(Transacciones.UsuarioFechaCreacio, lsusuarioFechaCreacion);

                                        objectSqLiteDatabase.update(Transacciones.tablausuarios,objectContentValuesUpdateUsuarios,Transacciones.UsuarioCorreo + "=?", parametroWhere);

                                    }else{
                                        // si el registro no existe en el movil, lo insertamos
                                        ContentValues objectContentValuesInsertUsuarios = new ContentValues();
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioNombre, lsusuarioNombre);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioApellido, lsusuarioApellido);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioTelefono, lsusuarioTelefono);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioCorreo, lsusuarioCorreo);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioContrasenia, lsusuarioContrasenia);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioNuevoRegistro, lsusuarioNuevoRegistro);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioAccesoConfiguracion, lsusuarioAccesoConfiguracion);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioAccesoBajarDatos, lsusuarioAccesoBajarDatos);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioAccesoSubirDatos, lsusuarioAccesoSubirDatos);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioAccesoRegistroProductores, lsusuarioAccesoRegistroProductores);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioAccesoRegistroAcopio, lsusuarioAccesoRegistroAcopio);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioEstado, lsusuarioEstado);
                                        objectContentValuesInsertUsuarios.put(Transacciones.UsuarioFechaCreacio, lsusuarioFechaCreacion);

                                        long checkIfQueryRuns = objectSqLiteDatabase.insert(Transacciones.tablausuarios, Transacciones.UsuarioId, objectContentValuesInsertUsuarios);

                                        if(checkIfQueryRuns!=-1){
                                            //Toast.makeText(getApplicationContext(), "Usuario almacenado en la DB", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "No se almaceno el usuario en la DB", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    // cerramos el cursor
                                    objectCursor.close();
                                }

                                // cerramos la conexion
                                objectSqLiteDatabase.close();

                                // obtenemos las variables declaradas en el webservice
                                String mensajeApi = jsonObject.getString("mensajeobtenerusuario");
                                Toast.makeText(getApplicationContext(),mensajeApi,Toast.LENGTH_SHORT).show();

                                //Toast.makeText(getApplicationContext(), "Usuarios actualizados", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }catch (JSONException ex){
                                ex.printStackTrace();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // si hay algun error por parte de la libreria Voley
                    progressDialog.dismiss();
                    // mostramos el error de la libreria
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                // el primer paso es enviar los datos al web services, con sus respectivos parametros
                // se hace un mapeo de un arreglo de 2 dimesiones
                protected Map<String,String> getParams(){
                    Map<String,String> parametros = new HashMap<>();
                    // parametros que enviaremos al web service
                    parametros.put("opcion", "obtenerusuarios");

                    return parametros;
                }
            };

            // ejecutamos la cadena(enviamos la peticion)
            requestQueue.add(stringRequestBajarUsuarios);

            // Bajar datos de proveedores

            // Mostramos el progressDialog
            progressDialog.setMessage("Procesando proveedores...");
            progressDialog.show();

            StringRequest stringRequestBajarProveedores = new StringRequest(Request.Method.POST, apiBajarDatos,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String serverResponse) {
                            // recibimos la respuesta del web services
                            try{

                                JSONObject jsonObject = new JSONObject(serverResponse);
                                JSONArray objectJsonArrayTablaProveedores = jsonObject.getJSONArray("tablaproveedores");

                                SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();

                                Cursor objectCursor;
                                String lsproveedorClave,lsproveedorNombre,lsproveedorRtn,lsproveedorCalle,lsproveedorCruzamiento,lsproveedorLocalidad,lsproveedorMunicipio,lsproveedorTelefono,lsproveedorSaldo,lsproveedorCertificacion;
                                String consultaSql;

                                for(int i = 0; i < objectJsonArrayTablaProveedores.length(); i++){
                                    JSONObject objectJsonProveedores = objectJsonArrayTablaProveedores.getJSONObject(i);
                                    lsproveedorClave = objectJsonProveedores.getString("CLAVE");
                                    lsproveedorNombre = objectJsonProveedores.getString("NOMBRE");
                                    lsproveedorRtn = objectJsonProveedores.getString("RFC");
                                    lsproveedorCalle = objectJsonProveedores.getString("CALLE");
                                    lsproveedorCruzamiento = objectJsonProveedores.getString("CRUZAMIENTOS");
                                    lsproveedorLocalidad = objectJsonProveedores.getString("LOCALIDAD");
                                    lsproveedorMunicipio = objectJsonProveedores.getString("MUNICIPIO");
                                    lsproveedorTelefono = objectJsonProveedores.getString("TELEFONO");
                                    lsproveedorSaldo = objectJsonProveedores.getString("SALDO");
                                    lsproveedorCertificacion = objectJsonProveedores.getString("CERTIFICACION");


                                    consultaSql = "SELECT * FROM tblproveedores WHERE ProveedorClave = '" + lsproveedorClave + "'";

                                    objectCursor = objectSqLiteDatabase.rawQuery(consultaSql, null);

                                    if(objectCursor.moveToNext()){
                                        // si el registro existe en el movil, lo actualizamos
                                        String [] parametroWhere = { lsproveedorClave };
                                        ContentValues objectContentValuesUpdateProveedores = new ContentValues();
                                        objectContentValuesUpdateProveedores.put(Transacciones.ProveedorNombre, lsproveedorNombre);
                                        objectContentValuesUpdateProveedores.put(Transacciones.ProveedorRtn, lsproveedorRtn);
                                        objectContentValuesUpdateProveedores.put(Transacciones.ProveedorCalle, lsproveedorCalle);
                                        objectContentValuesUpdateProveedores.put(Transacciones.ProveedorCruzamiento, lsproveedorCruzamiento);
                                        objectContentValuesUpdateProveedores.put(Transacciones.ProveedorLocalidad, lsproveedorLocalidad);
                                        objectContentValuesUpdateProveedores.put(Transacciones.ProveedorMunicipio, lsproveedorMunicipio);
                                        objectContentValuesUpdateProveedores.put(Transacciones.ProveedorTelefono, lsproveedorTelefono);
                                        objectContentValuesUpdateProveedores.put(Transacciones.ProveedorSaldo, lsproveedorSaldo);
                                        objectContentValuesUpdateProveedores.put(Transacciones.ProveedorCertificacion, lsproveedorCertificacion);

                                        objectSqLiteDatabase.update(Transacciones.tablaproveedores,objectContentValuesUpdateProveedores,Transacciones.ProveedorClave + "=?", parametroWhere);

                                    }else{
                                        // si el registro no existe en el movil, lo insertamos
                                        ContentValues objectContentValuesInsertProveedores = new ContentValues();
                                        objectContentValuesInsertProveedores.put(Transacciones.ProveedorClave, lsproveedorClave);
                                        objectContentValuesInsertProveedores.put(Transacciones.ProveedorNombre, lsproveedorNombre);
                                        objectContentValuesInsertProveedores.put(Transacciones.ProveedorRtn, lsproveedorRtn);
                                        objectContentValuesInsertProveedores.put(Transacciones.ProveedorCalle, lsproveedorCalle);
                                        objectContentValuesInsertProveedores.put(Transacciones.ProveedorCruzamiento, lsproveedorCruzamiento);
                                        objectContentValuesInsertProveedores.put(Transacciones.ProveedorLocalidad, lsproveedorLocalidad);
                                        objectContentValuesInsertProveedores.put(Transacciones.ProveedorMunicipio, lsproveedorMunicipio);
                                        objectContentValuesInsertProveedores.put(Transacciones.ProveedorTelefono, lsproveedorTelefono);
                                        objectContentValuesInsertProveedores.put(Transacciones.ProveedorSaldo, lsproveedorSaldo);
                                        objectContentValuesInsertProveedores.put(Transacciones.ProveedorCertificacion, lsproveedorCertificacion);

                                        long checkIfQueryRuns = objectSqLiteDatabase.insert(Transacciones.tablaproveedores, null, objectContentValuesInsertProveedores);

                                        if(checkIfQueryRuns!=-1){
                                            //Toast.makeText(getApplicationContext(), "Proveedor almacenado en la DB", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "No se almaceno el Proveedor en la DB", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    // cerramos el cursor
                                    objectCursor.close();
                                }

                                // cerramos la conexion
                                objectSqLiteDatabase.close();

                                // obtenemos las variables declaradas en el webservice
                                String mensajeApi = jsonObject.getString("mensajeobtenerproductores");
                                Toast.makeText(getApplicationContext(),mensajeApi,Toast.LENGTH_SHORT).show();

                                //Toast.makeText(getApplicationContext(), "Productores actualizados", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }catch (JSONException ex){
                                ex.printStackTrace();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // si hay algun error por parte de la libreria Voley
                    progressDialog.dismiss();
                    // mostramos el error de la libreria
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                // el primer paso es enviar los datos al web services, con sus respectivos parametros
                // se hace un mapeo de un arreglo de 2 dimesiones
                protected Map<String,String> getParams(){
                    Map<String,String> parametros = new HashMap<>();
                    // parametros que enviaremos al web service
                    parametros.put("opcion", "obtenerproductores");

                    return parametros;
                }
            };

            // ejecutamos la cadena
            requestQueue.add(stringRequestBajarProveedores);


            // Bajar datos de productos

            // Mostramos el progressDialog
            progressDialog.setMessage("Procesando productos...");
            progressDialog.show();

            StringRequest stringRequestBajarProductos = new StringRequest(Request.Method.POST, apiBajarDatos,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String serverResponse) {
                            // recibimos la respuesta del web services
                            try{

                                JSONObject jsonObject = new JSONObject(serverResponse);
                                JSONArray objectJsonArrayTablaProductos = jsonObject.getJSONArray("tablaproductos");

                                SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();

                                Cursor objectCursor;
                                String lsproductoClave,lsproductoDescripcion,lsproductoCosto,lsproductoLinea;
                                String consultaSql;

                                for(int i = 0; i < objectJsonArrayTablaProductos.length(); i++){
                                    JSONObject objectJsonProductos = objectJsonArrayTablaProductos.getJSONObject(i);
                                    lsproductoClave = objectJsonProductos.getString("CVE_ART");
                                    lsproductoDescripcion = objectJsonProductos.getString("DESCR");
                                    lsproductoCosto = objectJsonProductos.getString("ULT_COSTO");
                                    lsproductoLinea = objectJsonProductos.getString("LIN_PROD");

                                    consultaSql = "SELECT * FROM tblproductos WHERE ProductoClave = '" + lsproductoClave + "'";

                                    objectCursor = objectSqLiteDatabase.rawQuery(consultaSql, null);

                                    if(objectCursor.moveToNext()){
                                        // si el registro existe en el movil, lo actualizamos
                                        String [] parametroWhere = { lsproductoClave };
                                        ContentValues objectContentValuesUpdateProductos = new ContentValues();
                                        objectContentValuesUpdateProductos.put(Transacciones.ProductoDescripcion, lsproductoDescripcion);
                                        objectContentValuesUpdateProductos.put(Transacciones.ProductoCosto, lsproductoCosto);
                                        objectContentValuesUpdateProductos.put(Transacciones.ProductoLinea, lsproductoLinea);

                                        objectSqLiteDatabase.update(Transacciones.tablaproductos,objectContentValuesUpdateProductos,Transacciones.ProductoClave + "=?", parametroWhere);

                                    }else{
                                        // si el registro no existe en el movil, lo insertamos
                                        ContentValues objectContentValuesInsertProductores = new ContentValues();
                                        objectContentValuesInsertProductores.put(Transacciones.ProductoClave, lsproductoClave);
                                        objectContentValuesInsertProductores.put(Transacciones.ProductoDescripcion, lsproductoDescripcion);
                                        objectContentValuesInsertProductores.put(Transacciones.ProductoCosto, lsproductoCosto);
                                        objectContentValuesInsertProductores.put(Transacciones.ProductoLinea, lsproductoLinea);

                                        long checkIfQueryRuns = objectSqLiteDatabase.insert(Transacciones.tablaproductos, null, objectContentValuesInsertProductores);

                                        if(checkIfQueryRuns!=-1){
                                            //Toast.makeText(getApplicationContext(), "Producto almacenado en la DB", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "No se almaceno el Producto en la DB", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    // cerramos el cursor
                                    objectCursor.close();
                                }

                                // cerramos la conexion
                                objectSqLiteDatabase.close();

                                // obtenemos las variables declaradas en el webservice
                                String mensajeApi = jsonObject.getString("mensajeobtenerproductos");
                                Toast.makeText(getApplicationContext(),mensajeApi,Toast.LENGTH_SHORT).show();

                                //Toast.makeText(getApplicationContext(), "Productos actualizados", Toast.LENGTH_SHORT).show();

                                progressDialog.dismiss();
                            }catch (JSONException ex){
                                ex.printStackTrace();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // si hay algun error por parte de la libreria Voley
                    progressDialog.dismiss();
                    // mostramos el error de la libreria
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                // el primer paso es enviar los datos al web services, con sus respectivos parametros
                // se hace un mapeo de un arreglo de 2 dimesiones
                protected Map<String,String> getParams(){
                    Map<String,String> parametros = new HashMap<>();
                    // parametros que enviaremos al web service
                    parametros.put("opcion", "obtenerproductos");

                    return parametros;
                }
            };

            // ejecutamos la cadena
            requestQueue.add(stringRequestBajarProductos);


            // Bajar datos de almacenes

            // Mostramos el progressDialog
            progressDialog.setMessage("Procesando almacenes...");
            progressDialog.show();

            StringRequest stringRequestBajarAlmacenes = new StringRequest(Request.Method.POST, apiBajarDatos,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String serverResponse) {
                            // recibimos la respuesta del web services
                            try{

                                JSONObject jsonObject = new JSONObject(serverResponse);
                                JSONArray objectJsonArrayTablaAlmacenes = jsonObject.getJSONArray("tablaalmacenes");

                                SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();

                                Cursor objectCursor;
                                String lsalmacenClave,lsalmacenDescripcion;
                                String consultaSql;

                                for(int i = 0; i < objectJsonArrayTablaAlmacenes.length(); i++){
                                    JSONObject objectJsonAlmacenes = objectJsonArrayTablaAlmacenes.getJSONObject(i);
                                    lsalmacenClave = objectJsonAlmacenes.getString("CVE_ALM");
                                    lsalmacenDescripcion = objectJsonAlmacenes.getString("DESCR");

                                    consultaSql = "SELECT * FROM tblalmacenes WHERE AlmacenClave = " + lsalmacenClave;

                                    objectCursor = objectSqLiteDatabase.rawQuery(consultaSql, null);

                                    if(objectCursor.moveToNext()){
                                        // si el registro existe en el movil, lo actualizamos
                                        String [] parametroWhere = { lsalmacenClave };
                                        ContentValues objectContentValuesUpdateAlmacenes = new ContentValues();
                                        objectContentValuesUpdateAlmacenes.put(Transacciones.AlmacenDescripcion, lsalmacenDescripcion);

                                        objectSqLiteDatabase.update(Transacciones.tablaalmacenes,objectContentValuesUpdateAlmacenes,Transacciones.AlmacenClave + "=?", parametroWhere);

                                    }else{
                                        // si el registro no existe en el movil, lo insertamos
                                        ContentValues objectContentValuesInsertAlmacenes = new ContentValues();
                                        objectContentValuesInsertAlmacenes.put(Transacciones.AlmacenClave, lsalmacenClave);
                                        objectContentValuesInsertAlmacenes.put(Transacciones.AlmacenDescripcion, lsalmacenDescripcion);

                                        long checkIfQueryRuns = objectSqLiteDatabase.insert(Transacciones.tablaalmacenes, null, objectContentValuesInsertAlmacenes);

                                        if(checkIfQueryRuns!=-1){
                                            //Toast.makeText(getApplicationContext(), "Almacen almacenado en la DB", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "No se almaceno el almacen en la DB", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    // cerramos el cursor
                                    objectCursor.close();
                                }

                                // cerramos la conexion
                                objectSqLiteDatabase.close();

                                // obtenemos las variables declaradas en el webservice
                                String mensajeApi = jsonObject.getString("mensajeobteneralmacenes");
                                Toast.makeText(getApplicationContext(),mensajeApi,Toast.LENGTH_SHORT).show();

                                //Toast.makeText(getApplicationContext(), "Almacenes actualizados", Toast.LENGTH_SHORT).show();

                                // ocultamos el progressDialog
                                progressDialog.dismiss();

                            }catch (JSONException ex){
                                ex.printStackTrace();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // si hay algun error por parte de la libreria Voley
                    progressDialog.dismiss();
                    // mostramos el error de la libreria
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                // el primer paso es enviar los datos al web services, con sus respectivos parametros
                // se hace un mapeo de un arreglo de 2 dimesiones
                protected Map<String,String> getParams(){
                    Map<String,String> parametros = new HashMap<>();
                    // parametros que enviaremos al web service
                    parametros.put("opcion", "obteneralmacenes");

                    return parametros;
                }
            };

            // ejecutamos la cadena
            requestQueue.add(stringRequestBajarAlmacenes);

            // Bajar datos de configuraciones

            // Mostramos el progressDialog

            progressDialog.setMessage("Procesando configuraciones...");
            progressDialog.show();

            StringRequest stringRequestBajarConfiguraciones = new StringRequest(Request.Method.POST, apiBajarDatos,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String serverResponse) {
                            // recibimos la respuesta del web services
                            try{
                                JSONObject jsonObject = new JSONObject(serverResponse);
                                JSONArray objectJsonArrayTablaConfiguraciones = jsonObject.getJSONArray("tablaconfiguraciones");

                                SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();

                                Cursor objectCursor;
                                String lsConfiguracionId,lsConfiguracionSufijoDocumento,lsConfiguracionUltimoDocumento,lsConfiguracionUrl,lsConfiguracionTipoImpresora;
                                String consultaSql;

                                for(int i = 0; i < objectJsonArrayTablaConfiguraciones.length(); i++){
                                    JSONObject objectJsonConfiguraciones = objectJsonArrayTablaConfiguraciones.getJSONObject(i);
                                    lsConfiguracionId = objectJsonConfiguraciones.getString("ConfiguracionId");
                                    lsConfiguracionSufijoDocumento = objectJsonConfiguraciones.getString("ConfiguracionSufijoDocumento");
                                    lsConfiguracionUltimoDocumento = objectJsonConfiguraciones.getString("ConfiguracionUltimoDocumento");
                                    lsConfiguracionUrl = objectJsonConfiguraciones.getString("ConfiguracionUrl");
                                    lsConfiguracionTipoImpresora = objectJsonConfiguraciones.getString("ConfiguracionTipoImpresora");

                                    consultaSql = "SELECT * FROM tblconfiguraciones WHERE ConfiguracionId = '" + lsConfiguracionId + "'";

                                    objectCursor = objectSqLiteDatabase.rawQuery(consultaSql, null);

                                    if(objectCursor.moveToNext()){
                                        // si el registro existe en el movil, lo actualizamos
                                        String [] parametroWhere = { lsConfiguracionId };
                                        ContentValues objectContentValuesUpdateConfiguraciones = new ContentValues();
                                        objectContentValuesUpdateConfiguraciones.put(Transacciones.ConfiguracionSufijoDocumento, lsConfiguracionSufijoDocumento);
                                        objectContentValuesUpdateConfiguraciones.put(Transacciones.ConfiguracionUltimoDocumento, Integer.parseInt(lsConfiguracionUltimoDocumento));
                                        objectContentValuesUpdateConfiguraciones.put(Transacciones.ConfiguracionUrl, lsConfiguracionUrl);
                                        objectContentValuesUpdateConfiguraciones.put(Transacciones.ConfiguracionTipoImpresora, lsConfiguracionTipoImpresora);

                                        objectSqLiteDatabase.update(Transacciones.tablaconfiguraciones,objectContentValuesUpdateConfiguraciones,Transacciones.ConfiguracionId + "=?", parametroWhere);

                                    }else{
                                        // si el registro no existe en el movil, lo insertamos
                                        ContentValues objectContentValuesInsertConfiguraciones = new ContentValues();
                                        objectContentValuesInsertConfiguraciones.put(Transacciones.ConfiguracionId, lsConfiguracionId);
                                        objectContentValuesInsertConfiguraciones.put(Transacciones.ConfiguracionSufijoDocumento, lsConfiguracionSufijoDocumento);
                                        objectContentValuesInsertConfiguraciones.put(Transacciones.ConfiguracionUltimoDocumento, Integer.parseInt(lsConfiguracionUltimoDocumento));
                                        objectContentValuesInsertConfiguraciones.put(Transacciones.ConfiguracionUrl, lsConfiguracionUrl);
                                        objectContentValuesInsertConfiguraciones.put(Transacciones.ConfiguracionTipoImpresora, lsConfiguracionTipoImpresora);

                                        long checkIfQueryRuns = objectSqLiteDatabase.insert(Transacciones.tablaconfiguraciones, null, objectContentValuesInsertConfiguraciones);

                                        if(checkIfQueryRuns!=-1){
                                            //Toast.makeText(getApplicationContext(), "Configuración almacenada", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "No se almaceno la configuración", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    // cerramos el cursor
                                    objectCursor.close();
                                }

                                // cerramos la conexion
                                objectSqLiteDatabase.close();

                                // obtenemos las variables declaradas en el webservice
                                String mensajeApi = jsonObject.getString("mensajeobtenerconfiguraciones");
                                Toast.makeText(getApplicationContext(),mensajeApi,Toast.LENGTH_SHORT).show();

                                //Toast.makeText(getApplicationContext(), "Configuración actualizada", Toast.LENGTH_SHORT).show();

                                // ocultamos el progressDialog
                                progressDialog.dismiss();

                            }catch (JSONException ex){
                                ex.printStackTrace();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // si hay algun error por parte de la libreria Voley
                    progressDialog.dismiss();
                    // mostramos el error de la libreria
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                // el primer paso es enviar los datos al web services, con sus respectivos parametros
                // se hace un mapeo de un arreglo de 2 dimesiones
                protected Map<String,String> getParams(){
                    Map<String,String> parametros = new HashMap<>();
                    // parametros que enviaremos al web service
                    parametros.put("opcion", "obtenerconfiguraciones");

                    return parametros;
                }
            };

            // ejecutamos la cadena
            requestQueue.add(stringRequestBajarConfiguraciones);

            // Bajar datos de conceptos cuentas por pagar

            // Mostramos el progressDialog
            progressDialog.setMessage("Procesando conceptos...");
            progressDialog.show();

            StringRequest stringRequestBajarConceptosCxp = new StringRequest(Request.Method.POST, apiBajarDatos,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String serverResponse) {
                            // recibimos la respuesta del web services
                            try{
                                JSONObject jsonObject = new JSONObject(serverResponse);
                                JSONArray objectJsonArrayTablaConceptosCxp = jsonObject.getJSONArray("tablaconceptoscxp");

                                SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();

                                Cursor objectCursor;
                                String lsConceptoNo,lsConceptoDescripcion,lsConceptoTipo,lsConceptoConRefer,lsConceptoSigno,lsConceptoEsFormaPago;
                                String consultaSql;

                                for(int i = 0; i < objectJsonArrayTablaConceptosCxp.length(); i++){
                                    JSONObject objectJsonConceptosCxp = objectJsonArrayTablaConceptosCxp.getJSONObject(i);
                                    lsConceptoNo = objectJsonConceptosCxp.getString("NUM_CPTO");
                                    lsConceptoDescripcion = objectJsonConceptosCxp.getString("DESCR");
                                    lsConceptoTipo = objectJsonConceptosCxp.getString("TIPO");
                                    lsConceptoConRefer = objectJsonConceptosCxp.getString("CON_REFER");
                                    lsConceptoSigno = objectJsonConceptosCxp.getString("SIGNO");
                                    lsConceptoEsFormaPago = objectJsonConceptosCxp.getString("ES_FMA_PAG");

                                    consultaSql = "SELECT * FROM tblcuentasporpagarconceptos WHERE CuentasPorPagarConceptoNumero = " + lsConceptoNo;

                                    objectCursor = objectSqLiteDatabase.rawQuery(consultaSql, null);

                                    if(objectCursor.moveToNext()){
                                        // si el registro existe en el movil, lo actualizamos
                                        String [] parametroWhere = { lsConceptoNo };
                                        ContentValues objectContentValuesUpdateConceptosCxp = new ContentValues();
                                        objectContentValuesUpdateConceptosCxp.put(Transacciones.CuentasPorPagarConceptoDescripcion, lsConceptoDescripcion);
                                        objectContentValuesUpdateConceptosCxp.put(Transacciones.CuentasPorPagarConceptoTipo, lsConceptoTipo);
                                        objectContentValuesUpdateConceptosCxp.put(Transacciones.CuentasPorPagarConceptoConRefer, lsConceptoConRefer);
                                        objectContentValuesUpdateConceptosCxp.put(Transacciones.CuentasPorPagarConceptoSigno, Integer.parseInt(lsConceptoSigno));
                                        objectContentValuesUpdateConceptosCxp.put(Transacciones.CuentasPorPagarConceptoEsFormaPago, lsConceptoEsFormaPago);

                                        objectSqLiteDatabase.update(Transacciones.tablacuentasporpagarconceptos,objectContentValuesUpdateConceptosCxp,Transacciones.CuentasPorPagarConceptoNumero + "=?", parametroWhere);

                                    }else{
                                        // si el registro no existe en el movil, lo insertamos
                                        ContentValues objectContentValuesInsertConceptosCxp = new ContentValues();
                                        objectContentValuesInsertConceptosCxp.put(Transacciones.CuentasPorPagarConceptoNumero, Integer.parseInt(lsConceptoNo));
                                        objectContentValuesInsertConceptosCxp.put(Transacciones.CuentasPorPagarConceptoDescripcion, lsConceptoDescripcion);
                                        objectContentValuesInsertConceptosCxp.put(Transacciones.CuentasPorPagarConceptoTipo, lsConceptoTipo);
                                        objectContentValuesInsertConceptosCxp.put(Transacciones.CuentasPorPagarConceptoConRefer, lsConceptoConRefer);
                                        objectContentValuesInsertConceptosCxp.put(Transacciones.CuentasPorPagarConceptoSigno, Integer.parseInt(lsConceptoSigno));
                                        objectContentValuesInsertConceptosCxp.put(Transacciones.CuentasPorPagarConceptoEsFormaPago, lsConceptoEsFormaPago);

                                        long checkIfQueryRuns = objectSqLiteDatabase.insert(Transacciones.tablacuentasporpagarconceptos, null, objectContentValuesInsertConceptosCxp);

                                        if(checkIfQueryRuns!=-1){
                                            //Toast.makeText(getApplicationContext(), "Configuración almacenada", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "No se almaceno el concepto", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    // cerramos el cursor
                                    objectCursor.close();
                                }

                                // cerramos la conexion
                                objectSqLiteDatabase.close();

                                // obtenemos las variables declaradas en el webservice
                                String mensajeApi = jsonObject.getString("mensajeobtenerconceptoscxp");
                                Toast.makeText(getApplicationContext(),mensajeApi,Toast.LENGTH_SHORT).show();

                                //Toast.makeText(getApplicationContext(), "Conceptos de Cuentas por pagar actualizados", Toast.LENGTH_SHORT).show();

                                // ocultamos el progressDialog
                                progressDialog.dismiss();

                            }catch (JSONException ex){
                                ex.printStackTrace();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // si hay algun error por parte de la libreria Voley
                    progressDialog.dismiss();
                    // mostramos el error de la libreria
                    Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
            }){
                // el primer paso es enviar los datos al web services, con sus respectivos parametros
                // se hace un mapeo de un arreglo de 2 dimesiones
                protected Map<String,String> getParams(){
                    Map<String,String> parametros = new HashMap<>();
                    // parametros que enviaremos al web service
                    parametros.put("opcion", "obtenerconceptoscxp");

                    return parametros;
                }
            };

            // ejecutamos la cadena
            requestQueue.add(stringRequestBajarConceptosCxp);


        } catch (WindowManager.BadTokenException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

    }

}