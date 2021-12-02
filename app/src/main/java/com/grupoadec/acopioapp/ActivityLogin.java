package com.grupoadec.acopioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {

    // declaracion de variables
    SQLiteConexion conexion;

    EditText correologin_input, passwordlogin_input;
    TextView btnlogin;
    TextView txtactivityregistro;
    ImageView btncerrarsesionaisl;

    ProgressDialog progressDialog;

    RequestQueue requestQueue;

    String HttpURI = "http://192.168.68.106/ApiSaeAppAcopio/assets/php/apiusuarios.php";

    String ls_correologin,ls_passwordlogin,dispositivoId,correoValidacion, passwordValidacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try{
            // Se inicializan las variables
            conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);

            correologin_input = (EditText) findViewById(R.id.correologin_input);
            passwordlogin_input = (EditText) findViewById(R.id.passwordlogin_input);
            btnlogin = (TextView) findViewById(R.id.btnlogin);

            txtactivityregistro = (TextView) findViewById(R.id.txtactivityregistro);
            btncerrarsesionaisl = (ImageView) findViewById(R.id.btncerrarsesionaisl);

            // inicializamos requestQueue
            requestQueue = Volley.newRequestQueue(this);

            // inicializamos el progress bar
            progressDialog = new ProgressDialog(this);

            dispositivoId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

            ValidarSesion();
            
            // evento click del TextView registro
            txtactivityregistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),ActivityRegistro.class);
                    startActivity(intent);
                    finish();
                }
            });

            // evento click del boton iniciar sesion
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Login();
                    try {
                        LoginSqlite();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            });

            btncerrarsesionaisl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishAndRemoveTask();
                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void Login() {
        ls_correologin = correologin_input.getText().toString();
        ls_passwordlogin = passwordlogin_input.getText().toString();

        if(ls_correologin.isEmpty() || ls_passwordlogin.isEmpty()){
            Toast.makeText(getApplicationContext(),"El correo y password, no pueden estar vacios",Toast.LENGTH_LONG).show();
        }else{
            // Mostramos el progressDialog
            progressDialog.setMessage("Procesando...");
            progressDialog.show();

            // creamos la cadena que se enviara al webservice
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURI,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String serverResponse) {
                            // una vez que recibimos la respuesta del web services, desarrollamos la logiaca de lo que haremos con la info retornada
                            progressDialog.dismiss();

                            try{
                                JSONObject jsonObject = new JSONObject(serverResponse);
                                // obtenemos las variables declaradas en el webservice
                                Boolean error = jsonObject.getBoolean("error");
                                String mensaje = jsonObject.getString("mensaje");

                                if(error == true){
                                    Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
                                }else{
                                    // aqui podriamos llamar al activity main, en caso de que las contrasenias sean correcto, conciderar que este escenarios es si deseamos hacer login con credenciales directas al server
                                    Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

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
                // el primer paso es enviar los datos al web services, con sus respectivos parametros
                protected Map<String,String> getParams(){
                    Map<String,String> parametros = new HashMap<>();
                    // parametros que enviaremos al web service
                    parametros.put("opcion", "iniciarsesion");

                    parametros.put("email", ls_correologin);
                    parametros.put("contrasenia", ls_passwordlogin);
                    return parametros;
                }
            };

            // ejecutamos la cadena
            requestQueue.add(stringRequest);
        }
    }

    private void ValidarSesion() {

        try{

            SQLiteDatabase db = conexion.getReadableDatabase();
            Cursor objectCursorConfiguracion = db.rawQuery("SELECT * FROM tblconfiguraciones WHERE ConfiguracionId = '" + dispositivoId + "'" , null);

            String configuracionCorreo="", configuracionContrasenia="";
            Integer configuracionInicioSesion=0;

            if(objectCursorConfiguracion.getCount()!=0){
                while (objectCursorConfiguracion.moveToNext()){
                    configuracionInicioSesion = objectCursorConfiguracion.getInt(5);
                    configuracionCorreo = objectCursorConfiguracion.getString(6);
                    configuracionContrasenia = objectCursorConfiguracion.getString(7);
                }

                if(configuracionInicioSesion.equals(1)){
                    Cursor objectCursor = db.rawQuery("SELECT * FROM tblusuarios WHERE UsuarioCorreo = '"+ configuracionCorreo + "'" , null);
                    Intent objectIntent=new Intent(getApplicationContext(),MainActivity.class);

                    if(objectCursor.getCount()!=0){
                        while (objectCursor.moveToNext()){

                            Toast.makeText(getApplicationContext(),"Bienvenido " + objectCursor.getString(1) + " " + objectCursor.getString(2),Toast.LENGTH_LONG).show();
                            objectIntent.putExtra("iPeNombres", objectCursor.getString(1));
                            objectIntent.putExtra("iPeApellidos", objectCursor.getString(2));
                            objectIntent.putExtra("iPeTelefono", objectCursor.getString(3));
                            objectIntent.putExtra("iPeCorreo", objectCursor.getString(4));
                            objectIntent.putExtra("iPeNuevoRegistro", objectCursor.getString(6));
                            objectIntent.putExtra("iPeAccesoConfiguracion", objectCursor.getString(7));
                            objectIntent.putExtra("iPeAccesoBajarDatos", objectCursor.getString(8));
                            objectIntent.putExtra("iPeAccesoSubirDatos", objectCursor.getString(9));
                            objectIntent.putExtra("iPeAccesoRegistroProductores", objectCursor.getString(10));
                            objectIntent.putExtra("iPeAccesoRegistroAcopio", objectCursor.getString(11));
                        }

                        startActivity(objectIntent);

                        finish();

                    }else{
                        Toast.makeText(getApplicationContext(),"El usuario no existe o esta inactivo",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                    objectCursor.close();
                }
            }
            objectCursorConfiguracion.close();


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    private void LoginSqlite() throws NoSuchAlgorithmException {
        try{
            ls_correologin = correologin_input.getText().toString();
            ls_passwordlogin = passwordlogin_input.getText().toString();

            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(ls_passwordlogin.getBytes(), 0 , ls_passwordlogin.length());
            String encriptedPass = new BigInteger(1, md.digest()).toString(16);

            if(ls_correologin.isEmpty() || ls_passwordlogin.isEmpty()){
                Toast.makeText(getApplicationContext(),"El correo y password, no pueden estar vacios",Toast.LENGTH_LONG).show();
            }else{
                progressDialog.setMessage("Procesando...");
                progressDialog.show();

                SQLiteDatabase db = conexion.getReadableDatabase();

                Cursor objectCursor = db.rawQuery("SELECT * FROM tblusuarios WHERE UsuarioCorreo = '"+ ls_correologin + "' AND UsuarioContrasenia = '" + encriptedPass + "' AND UsuarioEstado = 1" , null);
                Intent objectIntent=new Intent(getApplicationContext(),MainActivity.class);

                if(objectCursor.getCount()!=0){
                    while (objectCursor.moveToNext()){
                        correoValidacion = objectCursor.getString(4);
                        passwordValidacion = objectCursor.getString(5);

                        Toast.makeText(getApplicationContext(),"Bienvenido " + objectCursor.getString(1) + " " + objectCursor.getString(2),Toast.LENGTH_LONG).show();
                        objectIntent.putExtra("iPeNombres", objectCursor.getString(1));
                        objectIntent.putExtra("iPeApellidos", objectCursor.getString(2));
                        objectIntent.putExtra("iPeTelefono", objectCursor.getString(3));
                        objectIntent.putExtra("iPeCorreo", objectCursor.getString(4));
                        objectIntent.putExtra("iPeNuevoRegistro", objectCursor.getString(6));
                        objectIntent.putExtra("iPeAccesoConfiguracion", objectCursor.getString(7));
                        objectIntent.putExtra("iPeAccesoBajarDatos", objectCursor.getString(8));
                        objectIntent.putExtra("iPeAccesoSubirDatos", objectCursor.getString(9));
                        objectIntent.putExtra("iPeAccesoRegistroProductores", objectCursor.getString(10));
                        objectIntent.putExtra("iPeAccesoRegistroAcopio", objectCursor.getString(11));
                    }

                    ActualizarSesionConfiguracion();

                    startActivity(objectIntent);

                    progressDialog.dismiss();
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),"El usuario no existe o esta inactivo",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void ActualizarSesionConfiguracion() {
        try{
            SQLiteDatabase db = conexion.getWritableDatabase();

            String [] params = { dispositivoId };

            ContentValues valores = new ContentValues();
            valores.put(Transacciones.ConfiguracionInicioSesion, 1);
            valores.put(Transacciones.ConfiguracionCorreoInicioSesion, correoValidacion);
            valores.put(Transacciones.ConfiguracionContraseniaInicioSesion, passwordValidacion);
            db.update(Transacciones.tablaconfiguraciones, valores, Transacciones.ConfiguracionId + "=?", params);

            db.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

}