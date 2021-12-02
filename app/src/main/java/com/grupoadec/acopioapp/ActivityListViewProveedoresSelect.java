package com.grupoadec.acopioapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Adaptadores.ListaProductoresAdapter;
import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;
import com.grupoadec.acopioapp.Models.TablaProveedores;

import java.util.ArrayList;

public class ActivityListViewProveedoresSelect extends AppCompatActivity {

    // definicion de variables
    SQLiteConexion objectSqLiteConexion;
    ListView objectListViewConsultaProveedores;
    ArrayList<TablaProveedores> objectArrayListTablaProveedoresLista = new ArrayList<>();
    TablaProveedores objectTablaProveedoresListaProveedores = null;
    EditText objectEditTextBuscarproveedores_input;
    ImageView objectImageViewBtnvolveractivitymain;
    ListaProductoresAdapter objectAdapter;

    String [] objectListItem;
    AlertDialog.Builder objectAlertDialogBuilderOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_proveedores_select);
        try {
            objectSqLiteConexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
            objectListViewConsultaProveedores = (ListView) findViewById(R.id.acopio_listview);
            objectEditTextBuscarproveedores_input = (EditText) findViewById(R.id.buscarproveedores_input);
            objectImageViewBtnvolveractivitymain = (ImageView) findViewById(R.id.btnvolveractivitymain);

            objectAlertDialogBuilderOpciones = new AlertDialog.Builder(this);

            // llenamos variables con los datos del putExtra
            String parPeNombres = getIntent().getStringExtra("iPeNombres");
            String parPeApellidos = getIntent().getStringExtra("iPeApellidos");
            String parPeCorreo = getIntent().getStringExtra("iPeCorreo");

            String parPeAlmacenClave = getIntent().getStringExtra("ipeAlmacenClave");
            String parPeAlmacenDescripcion = getIntent().getStringExtra("ipeAlmacenDescripcion");

            String parPeAccesoBajarDatos = getIntent().getStringExtra("iPeAccesoBajarDatos");
            String parPeAccesoSubirDatos = getIntent().getStringExtra("iPeAccesoSubirDatos");
            String parPeAccesoConfiguracion = getIntent().getStringExtra("iPeAccesoConfiguracion");
            String parPeAccesoRegistroAcopio = getIntent().getStringExtra("iPeAccesoRegistroAcopio");
            String parPeAccesoRegistroProductores = getIntent().getStringExtra("iPeAccesoRegistroProductores");

            ObtenerListaProveedores();

            objectAdapter = new ListaProductoresAdapter(this,objectArrayListTablaProveedoresLista);
            objectListViewConsultaProveedores.setAdapter(objectAdapter);

            objectEditTextBuscarproveedores_input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    objectAdapter.filtrarProveedorNombreRtn(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            objectImageViewBtnvolveractivitymain.setOnClickListener(new View.OnClickListener() {
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

            objectListViewConsultaProveedores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    try {
                        objectListItem = new String[]{"Consultar recibos"};
                        objectAlertDialogBuilderOpciones.setTitle("Seleccione un opcion");
                        objectAlertDialogBuilderOpciones.setSingleChoiceItems(objectListItem, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(objectListItem[i] == "Consultar recibos"){
                                    //Toast.makeText(getApplicationContext(), "Ha dado click en Consultar recibo", Toast.LENGTH_SHORT).show();

                                    TablaProveedores tplc = objectArrayListTablaProveedoresLista.get(position);

                                    Intent objectIntent = new Intent(getApplicationContext(),ActivityListViewConsultaAcopio.class);
                                    objectIntent.putExtra("iptProveedorClave", tplc.getProveedorClave());
                                    objectIntent.putExtra("iptProveedorNombre", tplc.getProveedorNombre());
                                    objectIntent.putExtra("iptProveedorRtn", tplc.getProveedorRtn());

                                    objectIntent.putExtra("iPeNombres", parPeNombres);
                                    objectIntent.putExtra("iPeApellidos", parPeApellidos);
                                    objectIntent.putExtra("iPeCorreo", parPeCorreo);
                                    objectIntent.putExtra("iPeAccesoConfiguracion", parPeAccesoConfiguracion);
                                    objectIntent.putExtra("iPeAccesoBajarDatos", parPeAccesoBajarDatos);
                                    objectIntent.putExtra("iPeAccesoSubirDatos", parPeAccesoSubirDatos);
                                    objectIntent.putExtra("iPeAccesoRegistroProductores", parPeAccesoRegistroProductores);
                                    objectIntent.putExtra("iPeAccesoRegistroAcopio", parPeAccesoRegistroAcopio);

                                    objectIntent.putExtra("iPeNuevaFactura", "1");

                                    objectIntent.putExtra("ipeAlmacenClave", parPeAlmacenClave);
                                    objectIntent.putExtra("ipeAlmacenDescripcion", parPeAlmacenDescripcion);

                                    startActivity(objectIntent);
                                    finish();

                                }
                            }
                        });
                        objectAlertDialogBuilderOpciones.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        objectAlertDialogBuilderOpciones.create();
                        objectAlertDialogBuilderOpciones.show();

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });

            objectListViewConsultaProveedores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    TablaProveedores tp = objectArrayListTablaProveedoresLista.get(i);

                    Intent objectIntent = new Intent(getApplicationContext(),ActivityMainAcopio.class);
                    objectIntent.putExtra("iptProveedorClave", tp.getProveedorClave());
                    objectIntent.putExtra("iptProveedorNombre", tp.getProveedorNombre());
                    objectIntent.putExtra("iptProveedorRtn", tp.getProveedorRtn());

                    objectIntent.putExtra("iPeNombres", parPeNombres);
                    objectIntent.putExtra("iPeApellidos", parPeApellidos);
                    objectIntent.putExtra("iPeCorreo", parPeCorreo);
                    objectIntent.putExtra("iPeAccesoConfiguracion", parPeAccesoConfiguracion);
                    objectIntent.putExtra("iPeAccesoBajarDatos", parPeAccesoBajarDatos);
                    objectIntent.putExtra("iPeAccesoSubirDatos", parPeAccesoSubirDatos);
                    objectIntent.putExtra("iPeAccesoRegistroProductores", parPeAccesoRegistroProductores);
                    objectIntent.putExtra("iPeAccesoRegistroAcopio", parPeAccesoRegistroAcopio);

                    objectIntent.putExtra("iPeNuevaFactura", "1");

                    objectIntent.putExtra("ipeAlmacenClave", parPeAlmacenClave);
                    objectIntent.putExtra("ipeAlmacenDescripcion", parPeAlmacenDescripcion);

                    startActivity(objectIntent);
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void ObtenerListaProveedores() {
        try {
            SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getReadableDatabase();

            Cursor objectCursor = objectSqLiteDatabase.rawQuery("SELECT * FROM " + Transacciones.tablaproveedores, null);

            while (objectCursor.moveToNext()){
                objectTablaProveedoresListaProveedores = new TablaProveedores();
                objectTablaProveedoresListaProveedores.setProveedorClave(objectCursor.getString(0));
                objectTablaProveedoresListaProveedores.setProveedorNombre(objectCursor.getString(1));
                objectTablaProveedoresListaProveedores.setProveedorRtn(objectCursor.getString(2));
                objectTablaProveedoresListaProveedores.setProveedorCalle(objectCursor.getString(3));
                objectTablaProveedoresListaProveedores.setProveedorCruzamiento(objectCursor.getString(4));
                objectTablaProveedoresListaProveedores.setProveedorLocalidad(objectCursor.getString(5));
                objectTablaProveedoresListaProveedores.setProveedorMunicipio(objectCursor.getString(6));
                objectTablaProveedoresListaProveedores.setProveedorTelefono(objectCursor.getString(7));
                objectTablaProveedoresListaProveedores.setProveedorSaldo(objectCursor.getDouble(8));
                objectTablaProveedoresListaProveedores.setProveedorCertificacion(objectCursor.getString(9));

                objectArrayListTablaProveedoresLista.add(objectTablaProveedoresListaProveedores);
            }

            objectCursor.close();
            objectSqLiteConexion.close();

        }catch (Exception e){
            Toast.makeText(this, "Error prueba "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}