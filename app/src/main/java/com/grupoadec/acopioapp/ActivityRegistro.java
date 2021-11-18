package com.grupoadec.acopioapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class ActivityRegistro extends AppCompatActivity {
    // declaramos las variables
    SQLiteConexion conexion;

    EditText nombreusuarioregistro_input, apellidousuarioregistro_input,telefonoregistro_input,correoregistro_input,passwordregistro_input,confirmarpasswordregistro_input;
    TextView btnregistrarusuario,txtactivitylogin;

    AlertDialog.Builder builder;

    // variables para obtener fecha y hora actual
    Calendar c;
    SimpleDateFormat df;
    String formattedDate;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        try{
            // inicializamos y enlazamos las variables con el activity xml
            conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null, 1);

            c = Calendar.getInstance();
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedDate = df.format(c.getTime());

            nombreusuarioregistro_input = (EditText) findViewById(R.id.buscarproveedores_input);
            apellidousuarioregistro_input = (EditText) findViewById(R.id.apellidousuarioregistro_input);
            telefonoregistro_input = (EditText) findViewById(R.id.telefonoregistro_input);
            correoregistro_input = (EditText) findViewById(R.id.correoregistro_input);
            passwordregistro_input = (EditText) findViewById(R.id.passwordregistro_input);
            confirmarpasswordregistro_input = (EditText) findViewById(R.id.confirmarpasswordregistro_input);

            btnregistrarusuario = (TextView) findViewById(R.id.btnregistrarusuario);
            txtactivitylogin = (TextView) findViewById(R.id.txtactivitylogin);

            builder = new AlertDialog.Builder(this);

            // guardar usuario
            btnregistrarusuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(nombreusuarioregistro_input.length()>0 && apellidousuarioregistro_input.length()>0 && telefonoregistro_input.length()>0 && correoregistro_input.length()>0 && passwordregistro_input.length()>0 && confirmarpasswordregistro_input.length()>0){
                        if(passwordregistro_input.getText().toString().equals(confirmarpasswordregistro_input.getText().toString())) {

                            // despues de hacer las validaciones realizamos la funcion de insertar datos
                            //Mensaje de dialogo
                            builder.setMessage("Desea registrar el usuario " + nombreusuarioregistro_input.getText() + " " + apellidousuarioregistro_input.getText() + " ,con el correo: " + correoregistro_input.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            try {
                                                AgregarUsuario();
                                            } catch (NoSuchAlgorithmException e) {
                                                e.printStackTrace();
                                            }
                                            //Toast.makeText(getApplicationContext(),"Usuario registrado",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();
                                            Toast.makeText(getApplicationContext(),"No se registro el Usuario",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("Alerta");
                            alert.show();

                        } else{
                            Toast.makeText(getApplicationContext(), "Las contrasenias no coinciden, por favor verificar", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Todos los datos son requeridos, por favor no deje campos vacios", Toast.LENGTH_LONG).show();
                    }
                }
            });

            // retornar al activity de inicio de sesion
            txtactivitylogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),ActivityLogin.class);
                    startActivity(intent);
                    finish();
                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void AgregarUsuario() throws NoSuchAlgorithmException {
        try{
            SQLiteDatabase db = conexion.getWritableDatabase();

            String consultaSql = "SELECT * FROM tblusuarios WHERE UsuarioCorreo = '" + correoregistro_input.getText().toString().trim() + "'";
            //String consultaSql = "SELECT * FROM tblusuarios";
            Cursor cursor = db.rawQuery(consultaSql, null);


            if(cursor.moveToNext()){
                Toast.makeText(getApplicationContext(),"la direccion de correo: " + correoregistro_input.getText().toString() + " ya existe en la base de datos", Toast.LENGTH_LONG).show();
            }else{
                String password = passwordregistro_input.getText().toString();


                MessageDigest md = MessageDigest.getInstance("SHA1");
                md.update(password.getBytes(), 0 , password.length());
                String encriptedPass = new BigInteger(1, md.digest()).toString(16);

                ContentValues valores = new ContentValues();
                valores.put(Transacciones.UsuarioNombre, nombreusuarioregistro_input.getText().toString());
                valores.put(Transacciones.UsuarioApellido, apellidousuarioregistro_input.getText().toString());
                valores.put(Transacciones.UsuarioTelefono, telefonoregistro_input.getText().toString());
                valores.put(Transacciones.UsuarioCorreo, correoregistro_input.getText().toString());
                valores.put(Transacciones.UsuarioContrasenia, encriptedPass);
                valores.put(Transacciones.UsuarioNuevoRegistro, 1);
                valores.put(Transacciones.UsuarioAccesoConfiguracion, 0);
                valores.put(Transacciones.UsuarioAccesoBajarDatos, 1);
                valores.put(Transacciones.UsuarioAccesoSubirDatos, 1);
                valores.put(Transacciones.UsuarioAccesoRegistroProductores, 0);
                valores.put(Transacciones.UsuarioAccesoRegistroAcopio, 0);
                valores.put(Transacciones.UsuarioEstado, 1);
                valores.put(Transacciones.UsuarioFechaCreacio,formattedDate);

                Long resultado = db.insert(Transacciones.tablausuarios, Transacciones.UsuarioId, valores);

                if(resultado != -1){
                    Toast.makeText(getApplicationContext(),"Usuario registrado con el id: " + resultado.toString(),Toast.LENGTH_SHORT).show();
                    db.close();
                    LimpiarPantalla();
                }else{
                    Toast.makeText(getApplicationContext(),"El usuario no pudo ser registrado en la DB ",Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    private void LimpiarPantalla() {
        try {
            nombreusuarioregistro_input.setText("");
            apellidousuarioregistro_input.setText("");
            telefonoregistro_input.setText("");
            correoregistro_input.setText("");
            passwordregistro_input.setText("");
            confirmarpasswordregistro_input.setText("");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}