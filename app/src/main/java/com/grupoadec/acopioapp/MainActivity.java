package com.grupoadec.acopioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // declaracion de variables
    ImageButton btnSincronizar,btnRegistroAcopio,btnAccesoConfiguracion,btnPerfilUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            // inicializamos variables
            btnSincronizar = (ImageButton) findViewById(R.id.btnSubirDatos);
            btnRegistroAcopio = (ImageButton) findViewById(R.id.btnRegistroAcopio);
            btnAccesoConfiguracion = (ImageButton) findViewById(R.id.btnBajarDatos);
            btnPerfilUsuario = (ImageButton) findViewById(R.id.btnConfiguracion);

            // llenamos variables con los datos del putExtra
            String parPeNombres = getIntent().getStringExtra("peNombre");
            String parPeApellidos = getIntent().getStringExtra("peApellidos");
            String parPeCorreo = getIntent().getStringExtra("peCorreo");
            String parPeAccesoRegistroProductores = getIntent().getStringExtra("iPeAccesoRegistroProductores");
            String parPeAccesoBajarDatos = getIntent().getStringExtra("iPeAccesoBajarDatos");
            String parPeAccesoConfiguracion = getIntent().getStringExtra("iPeAccesoConfiguracion");
            String parPeAccesoRegistroAcopio = getIntent().getStringExtra("iPeAccesoRegistroAcopio");

            // establecemos permisos al usuario
            if(parPeAccesoBajarDatos.equals("1")){
                btnSincronizar.setClickable(true);
            }else if(parPeAccesoConfiguracion.equals("1")){
                btnAccesoConfiguracion.setClickable(true);
            }else if(parPeAccesoRegistroAcopio.equals("1")){
                btnRegistroAcopio.setClickable(true);
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}