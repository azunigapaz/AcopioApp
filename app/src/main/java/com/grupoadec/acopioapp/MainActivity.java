package com.grupoadec.acopioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;
import com.grupoadec.acopioapp.models.TablaUsuarios;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // declaracion de variables
    ImageButton btnSubirDatos,btnRegistroAcopio,btnAccesoConfiguracion,btnBajarDatos;

    ProgressDialog progressDialog;

    RequestQueue requestQueue;

    String HttpURI = "http://192.168.68.106/ApiSaeAppAcopio/assets/php/apicrud.php";

    SQLiteConexion objectSqLiteConexion;

    ListView objectListViewListaUsuarios;
    List<TablaUsuarios> objectListTablaUsuarios;
    ArrayList<String> objectArrayListUsuarios;

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

            // inicializamos requestQueue
            requestQueue = Volley.newRequestQueue(this);

            // inicializamos el progress bar
            progressDialog = new ProgressDialog(this);

            // inicializamos la conexion
            objectSqLiteConexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);

            objectListTablaUsuarios =  new ArrayList<>();
            objectArrayListUsuarios = new ArrayList<String>();

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

                    SubirDatos();
                }
            });

            btnBajarDatos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BajarDatos();
                    //Toast.makeText(getApplicationContext(),"Click en bajar datos",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void SubirDatos() {
        try{
            SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getReadableDatabase();
            Cursor objectCursor = objectSqLiteDatabase.rawQuery("SELECT * FROM tblusuarios WHERE UsuarioNuevoRegistro=1", null);

            if(objectCursor.getCount()!=0){
                // Mostramos el progressDialog
                progressDialog.setMessage("Procesando...");
                progressDialog.show();

                while (objectCursor.moveToNext()){
                    // declaramos variables que llenaremos con los datos obtenidos del cursor
                    final String usuarionombre = objectCursor.getString(1);
                    final String usuarioapellido = objectCursor.getString(2);
                    final String usuariotelefono = objectCursor.getString(3);
                    final String usuariocorreo = objectCursor.getString(4);
                    final String usuariocontrasenia = objectCursor.getString(5);
                    final String usuarionuevousuario = objectCursor.getString(6);
                    final String usuarioaccesoconfiguracion = objectCursor.getString(7);
                    final String usuarioaccesobajardatos = objectCursor.getString(8);
                    final String usuarioaccesosubirdatos = objectCursor.getString(9);
                    final String usuarioaccesoregistroproductores = objectCursor.getString(10);
                    final String usuarioaccesoregistroacopio = objectCursor.getString(11);
                    final String usuarioestado = objectCursor.getString(12);
                    final String usuariofechacreacion = objectCursor.getString(13);


                    // creamos la cadena que se enviara al webservice
                    StringRequest stringRequestSubirUsuarios = new StringRequest(Request.Method.POST, HttpURI,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String serverResponse) {
                                    // Recibimos la respuesta del web services

                                    try{

                                        JSONObject jsonObject = new JSONObject(serverResponse);

                                        // obtenemos las variables declaradas en el webservice
                                        String mensajeApi = jsonObject.getString("mensajeactintusuario");
                                        Toast.makeText(getApplicationContext(),mensajeApi,Toast.LENGTH_SHORT).show();

                                    }catch (JSONException ex){
                                        ex.printStackTrace();
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
                objectCursor.close();
                // cerramos la conexion
                objectSqLiteDatabase.close();
                // ocultamos el progress Dialog
                progressDialog.dismiss();
            }else{
                Toast.makeText(this, "No existen registros en la base de datos", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void BajarDatos() {
        try{
            // bajar datos de usuarios
            // Mostramos el progressDialog
            progressDialog.setMessage("Procesando...");
            progressDialog.show();
            // creamos la cadena que se enviara al webservice
            StringRequest stringRequestBajarUsuarios = new StringRequest(Request.Method.POST, HttpURI,
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
                                            Toast.makeText(getApplicationContext(), "Usuario almacenado en la DB", Toast.LENGTH_SHORT).show();
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

            // ejecutamos la cadena
            requestQueue.add(stringRequestBajarUsuarios);


            // Bajar datos de proveedores
            // Mostramos el progressDialog
            progressDialog.setMessage("Procesando...");
            progressDialog.show();
            // creamos la cadena que se enviara al webservice
            StringRequest stringRequestBajarProveedores = new StringRequest(Request.Method.POST, HttpURI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String serverResponse) {
                            // recibimos la respuesta del web services
                            try{

                                JSONObject jsonObject = new JSONObject(serverResponse);
                                JSONArray objectJsonArrayTablaProveedores = jsonObject.getJSONArray("tablaproveedores");

                                SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();

                                Cursor objectCursor;
                                String lsproveedorClave,lsproveedorNombre,lsproveedorRtn,lsproveedorCalle,lsproveedorCruzamiento,lsproveedorLocalidad,lsproveedorMunicipio,lsproveedorTelefon,lsproveedorSaldo;
                                String consultaSql;

                                for(int i = 0; i < objectJsonArrayTablaProveedores.length(); i++){
                                    JSONObject objectJsonProveedores = objectJsonArrayTablaProveedores.getJSONObject(i);
                                    lsproveedorClave = objectJsonProveedores.getString("CLAVE");
                                    lsproveedorNombre = objectJsonProveedores.getString("NOMBRE");


                                    consultaSql = "SELECT * FROM tblproveedores WHERE ProveedorClave = '" + lsproveedorClave + "'";

                                    objectCursor = objectSqLiteDatabase.rawQuery(consultaSql, null);

                                    if(objectCursor.moveToNext()){
                                        // si el registro existe en el movil, lo actualizamos
                                        String [] parametroWhere = { lsproveedorClave };
                                        ContentValues objectContentValuesUpdateProveedores = new ContentValues();
                                        objectContentValuesUpdateProveedores.put(Transacciones.ProveedorNombre, lsproveedorNombre);


                                        objectSqLiteDatabase.update(Transacciones.tablaproveedores,objectContentValuesUpdateProveedores,Transacciones.ProveedorClave + "=?", parametroWhere);

                                    }else{
                                        // si el registro no existe en el movil, lo insertamos
                                        ContentValues objectContentValuesInsertProveedores = new ContentValues();
                                        objectContentValuesInsertProveedores.put(Transacciones.UsuarioNombre, lsproveedorNombre);

                                        long checkIfQueryRuns = objectSqLiteDatabase.insert(Transacciones.tablausuarios, null, objectContentValuesInsertProveedores);

                                        if(checkIfQueryRuns!=-1){
                                            Toast.makeText(getApplicationContext(), "Proveedor almacenado en la DB", Toast.LENGTH_SHORT).show();
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


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

}