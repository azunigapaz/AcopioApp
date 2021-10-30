package com.grupoadec.acopioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {

    // declaracion de variables
    EditText correologin_input, passwordlogin_input;
    TextView btnlogin;
    TextView txtactivityregistro;

    ProgressDialog progressDialog;
    //ProgressBar progressBar;

    RequestQueue requestQueue;

    String HttpURI = "http://192.168.68.105/ApiSaeAppAcopio/assets/php/apiusuarios.php";

    String ls_correologin;
    String ls_passwordlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Se inicializan las variables
        correologin_input = (EditText) findViewById(R.id.correologin_input);
        passwordlogin_input = (EditText) findViewById(R.id.passwordlogin_input);
        btnlogin = (TextView) findViewById(R.id.btnlogin);

        txtactivityregistro = (TextView) findViewById(R.id.txtactivityregistro);

        // inicializamos requestQueue
        requestQueue = Volley.newRequestQueue(this);

        // inicializamos el progress bar
        progressDialog = new ProgressDialog(this);


        // evento click del TextView registro
        txtactivityregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityRegistro.class);
                startActivity(intent);
            }
        });

        // evento click del boton iniciar sesion
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

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
}