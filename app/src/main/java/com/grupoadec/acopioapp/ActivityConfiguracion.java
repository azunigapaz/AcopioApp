package com.grupoadec.acopioapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;

import java.util.Calendar;

public class ActivityConfiguracion extends AppCompatActivity {
    EditText idusuarioconfiguracion_input,ultimodocumentoconfiguracion_input,urlconfiguracion_input;
    Spinner tipoimpresoraconfiguracion_spinner;
    ImageView btncfgvolvermain;
    Button btnguardarconfiguracion;

    SQLiteConexion objectSqLiteConexion;
    AlertDialog.Builder objectAlertDialogBuilder;

    String dispositivoId;
    ArrayAdapter<String> objectAdapter;

    TextView fechalimitedeemision_txt;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        try {
            idusuarioconfiguracion_input = (EditText) findViewById(R.id.idusuarioconfiguracion_input);
            ultimodocumentoconfiguracion_input = (EditText) findViewById(R.id.ultimodocumentoconfiguracion_input);
            urlconfiguracion_input = (EditText) findViewById(R.id.urlconfiguracion_input);
            tipoimpresoraconfiguracion_spinner = (Spinner) findViewById(R.id.tipoimpresoraconfiguracion_spinner);
            btnguardarconfiguracion = (Button) findViewById(R.id.btnguardarconfiguracion);
            btncfgvolvermain = (ImageView) findViewById(R.id.btncfgvolvermain);
            fechalimitedeemision_txt = (TextView) findViewById(R.id.fechalimitedeemision_txt);

            objectSqLiteConexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
            objectAlertDialogBuilder = new AlertDialog.Builder(this);

            LlenarSpinnerTipoImpresora();
            dispositivoId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
            idusuarioconfiguracion_input.setText(dispositivoId);

            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);

            // llenamos variables con los datos del putExtra
            String parPeNombres = getIntent().getStringExtra("iPeNombres");
            String parPeApellidos = getIntent().getStringExtra("iPeApellidos");
            String parPeCorreo = getIntent().getStringExtra("iPeCorreo");

            String parPeAccesoBajarDatos = getIntent().getStringExtra("iPeAccesoBajarDatos");
            String parPeAccesoSubirDatos = getIntent().getStringExtra("iPeAccesoSubirDatos");
            String parPeAccesoConfiguracion = getIntent().getStringExtra("iPeAccesoConfiguracion");
            String parPeAccesoRegistroAcopio = getIntent().getStringExtra("iPeAccesoRegistroAcopio");
            String parPeAccesoRegistroProductores = getIntent().getStringExtra("iPeAccesoRegistroProductores");

            LlenarDatosActivityConfiguracion();

            btnguardarconfiguracion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GuardarConfiguracion();
                }
            });

            btncfgvolvermain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent objectIntent = new Intent(getApplicationContext(),MainActivity.class);

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

            fechalimitedeemision_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityConfiguracion.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);

                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            });

            setListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    String date = day+"/"+month+"/"+year;
                    fechalimitedeemision_txt.setText(date);
                }
            };

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void LlenarSpinnerTipoImpresora() {
        try{
            objectAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.tipo_de_impresora));
            objectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tipoimpresoraconfiguracion_spinner.setAdapter(objectAdapter);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void LlenarDatosActivityConfiguracion() {
        try{
            SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();
            String ConsultaSql = "SELECT * FROM " + Transacciones.tablaconfiguraciones + " WHERE ConfiguracionId = '" + dispositivoId + "'";

            Cursor objectCursor = objectSqLiteDatabase.rawQuery(ConsultaSql,null);

            if (objectCursor.getCount()!=0){
                while (objectCursor.moveToNext()){
                    ultimodocumentoconfiguracion_input.setText(String.valueOf(objectCursor.getInt(2)));
                    urlconfiguracion_input.setText(objectCursor.getString(3).toString());
                    int spinnerPosition = objectAdapter.getPosition(objectCursor.getString(4).toString());
                    tipoimpresoraconfiguracion_spinner.setSelection(spinnerPosition);
                }
            }else{
                Toast.makeText(getApplicationContext(),"No existe configuración",Toast.LENGTH_SHORT).show();
            }
            objectCursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    private void GuardarConfiguracion() {
        try {
            String sufijoFolioDocumento = dispositivoId.substring(0,4).toUpperCase();
            SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();
            String ConsultaSql = "SELECT * FROM " + Transacciones.tablaconfiguraciones + " WHERE ConfiguracionId = '" + dispositivoId + "'";

            Cursor objectCursor = objectSqLiteDatabase.rawQuery(ConsultaSql,null);

            if (objectCursor.moveToNext()){
                String [] parametroWhere = { idusuarioconfiguracion_input.getText().toString() };
                ContentValues objectContentValuesUpdateConf = new ContentValues();
                objectContentValuesUpdateConf.put(Transacciones.ConfiguracionUltimoDocumento, ultimodocumentoconfiguracion_input.getText().toString());
                objectContentValuesUpdateConf.put(Transacciones.ConfiguracionUrl, urlconfiguracion_input.getText().toString());
                objectContentValuesUpdateConf.put(Transacciones.ConfiguracionTipoImpresora, tipoimpresoraconfiguracion_spinner.getSelectedItem().toString());

                objectSqLiteDatabase.update(Transacciones.tablaconfiguraciones,objectContentValuesUpdateConf,Transacciones.ConfiguracionId + "=?", parametroWhere);
                Toast.makeText(getApplicationContext(),"Configuración actualizada", Toast.LENGTH_SHORT).show();
            }else{
                ContentValues objectContentValuesInsertConf = new ContentValues();
                objectContentValuesInsertConf.put(Transacciones.ConfiguracionId, idusuarioconfiguracion_input.getText().toString());
                objectContentValuesInsertConf.put(Transacciones.ConfiguracionSufijoDocumento, sufijoFolioDocumento);
                objectContentValuesInsertConf.put(Transacciones.ConfiguracionUltimoDocumento, ultimodocumentoconfiguracion_input.getText().toString());
                objectContentValuesInsertConf.put(Transacciones.ConfiguracionUrl, urlconfiguracion_input.getText().toString());
                objectContentValuesInsertConf.put(Transacciones.ConfiguracionTipoImpresora, tipoimpresoraconfiguracion_spinner.getSelectedItem().toString());

                Long checkIfQueryRun = objectSqLiteDatabase.insert(Transacciones.tablaconfiguraciones,null,objectContentValuesInsertConf);

                if(checkIfQueryRun != -1){
                    Toast.makeText(getApplicationContext(),"Configuración guardada", Toast.LENGTH_SHORT).show();
                    objectSqLiteDatabase.close();
                }else{
                    Toast.makeText(getApplicationContext(),"No se guardó la configuración", Toast.LENGTH_SHORT).show();
                }
            }
            objectCursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}