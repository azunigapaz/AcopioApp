package com.grupoadec.acopioapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Adaptadores.ListaAcopioPartidaTemporalAdapter;
import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;
import com.grupoadec.acopioapp.Models.TablaAcopioPartidaTemporal;

import java.util.ArrayList;

public class ActivityMainAcopio extends AppCompatActivity {
    // declaracion de variables
    ImageView btnvolveractivityproductoreslistview,btnactivityproductosparaacopio,btnguardaracopio;
    TextView textViewNoReciboAcopio, textViewProveedorAcopio;
    ListView acopio_listview;
    EditText subtotalacopio_input,impuestosacopio_input,totalacopio_input;
    SQLiteConexion objectSqLiteConexion;
    String dispositivoId;

    ArrayList<TablaAcopioPartidaTemporal> objectArrayListTablaAcopioPartidaTemporalLista = new ArrayList<>();
    TablaAcopioPartidaTemporal objectTablaAcopioPartidaTemporalListaAcopioPartidaTemporal = null;
    ListaAcopioPartidaTemporalAdapter objectAdapter;

    Double subTotal = 0.00, impuesto = 0.00, total = 0.00;
    String [] objectListItem;
    AlertDialog.Builder objectAlertDialogBuilderOpciones;
    AlertDialog.Builder objectAlertDialogBuilderOpcionesCantidadPrecio;

    String validarAccionTipo="";

    Integer tpPartidaNumero;
    String tpPartidaProductoClave;
    String tpPartidaProductoDescripcion;
    Double tpPartidaProductoCantidad;
    Double tpPartidaProductoPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_acopio);

        try{
            btnvolveractivityproductoreslistview = (ImageView) findViewById(R.id.btnvolveractivityproductoreslistview);
            btnactivityproductosparaacopio = (ImageView) findViewById(R.id.btnactivityproductosparaacopio);
            btnguardaracopio = (ImageView) findViewById(R.id.btnguardaracopio);

            textViewNoReciboAcopio = (TextView) findViewById(R.id.textViewNoReciboAcopio);
            textViewProveedorAcopio = (TextView) findViewById(R.id.textViewProveedorAcopio);

            acopio_listview = (ListView) findViewById(R.id.acopio_listview);

            subtotalacopio_input = (EditText) findViewById(R.id.subtotalacopio_input);
            impuestosacopio_input = (EditText) findViewById(R.id.impuestosacopio_input);
            totalacopio_input = (EditText) findViewById(R.id.totalacopio_input);

            objectAlertDialogBuilderOpciones = new AlertDialog.Builder(this);
            objectAlertDialogBuilderOpcionesCantidadPrecio = new AlertDialog.Builder(this);

            objectSqLiteConexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
            dispositivoId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

            ObtenerNumeroDeRecibo();

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

            String parPeValidacionNuevaFactura = getIntent().getStringExtra("iPeNuevaFactura");

            textViewProveedorAcopio.setText(parPeProveedorClave.trim() + "-" + parPeProveedorNombre);

            if(parPeValidacionNuevaFactura.equals("1")){
                ElminarProductoAcopioPartidaTemporal();
            }

            ObtenerListaAcopioPartidasTemporal();
            objectAdapter = new ListaAcopioPartidaTemporalAdapter(this,objectArrayListTablaAcopioPartidaTemporalLista);
            acopio_listview.setAdapter(objectAdapter);

            acopio_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    try{
                        TablaAcopioPartidaTemporal tp = objectArrayListTablaAcopioPartidaTemporalLista.get(i);
                        tpPartidaNumero = tp.getAcopioPartidaNo();
                        tpPartidaProductoClave = tp.getAcopioPartidaProductoClave();
                        tpPartidaProductoDescripcion = tp.getAcopioPartidaProductoDescripcion();
                        tpPartidaProductoCantidad = tp.getAcopioPartidaProductoCantidad();
                        tpPartidaProductoPrecio = tp.getAcopioPartidaProductoPrecio();

                        objectListItem = new String[]{"Editar cantidad","Editar precio","Eliminar producto del recibo"};
                        objectAlertDialogBuilderOpciones.setTitle("Seleccione un opcion");
                        objectAlertDialogBuilderOpciones.setSingleChoiceItems(objectListItem, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(objectListItem[i] == "Editar cantidad"){
                                    // 1 = Agregar, 2 = Modificar
                                    validarAccionTipo = "2";

                                    objectAlertDialogBuilderOpcionesCantidadPrecio.setTitle("Ingrese la cantidad");

                                    final EditText cantidadInput = new EditText(getApplicationContext());
                                    cantidadInput.setInputType(InputType.TYPE_CLASS_PHONE);

                                    objectAlertDialogBuilderOpcionesCantidadPrecio.setView(cantidadInput);

                                    objectAlertDialogBuilderOpcionesCantidadPrecio.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            tpPartidaProductoCantidad = Double.parseDouble(cantidadInput.getText().toString());

                                            ActualizarCantidadFilaListViewAcopio(tpPartidaNumero);
                                            objectArrayListTablaAcopioPartidaTemporalLista.clear();

                                            ObtenerListaAcopioPartidasTemporal();

                                            objectAdapter = new ListaAcopioPartidaTemporalAdapter(getApplicationContext(),objectArrayListTablaAcopioPartidaTemporalLista);
                                            acopio_listview.setAdapter(objectAdapter);

                                        }
                                    });
                                    objectAlertDialogBuilderOpcionesCantidadPrecio.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    objectAlertDialogBuilderOpcionesCantidadPrecio.show();

                                    dialogInterface.dismiss();

                                }else if(objectListItem[i] == "Eliminar producto del recibo"){
                                    //Si selecciona la opcion eliminar
                                    EliminarFilaListViewAcopio(tpPartidaNumero);
                                    objectArrayListTablaAcopioPartidaTemporalLista.clear();

                                    ObtenerListaAcopioPartidasTemporal();

                                    objectAdapter = new ListaAcopioPartidaTemporalAdapter(getApplicationContext(),objectArrayListTablaAcopioPartidaTemporalLista);
                                    acopio_listview.setAdapter(objectAdapter);

                                    dialogInterface.dismiss();
                                }else if(objectListItem[i] == "Editar precio"){
                                    objectAlertDialogBuilderOpcionesCantidadPrecio.setTitle("Ingrese el precio");

                                    final EditText cantidadInput = new EditText(getApplicationContext());
                                    cantidadInput.setInputType(InputType.TYPE_CLASS_PHONE);

                                    objectAlertDialogBuilderOpcionesCantidadPrecio.setView(cantidadInput);

                                    objectAlertDialogBuilderOpcionesCantidadPrecio.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            tpPartidaProductoPrecio = Double.parseDouble(cantidadInput.getText().toString());

                                            ActualizarPrecioFilaListViewAcopio(tpPartidaNumero);
                                            objectArrayListTablaAcopioPartidaTemporalLista.clear();

                                            ObtenerListaAcopioPartidasTemporal();

                                            objectAdapter = new ListaAcopioPartidaTemporalAdapter(getApplicationContext(),objectArrayListTablaAcopioPartidaTemporalLista);
                                            acopio_listview.setAdapter(objectAdapter);

                                        }
                                    });
                                    objectAlertDialogBuilderOpcionesCantidadPrecio.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    objectAlertDialogBuilderOpcionesCantidadPrecio.show();

                                    dialogInterface.dismiss();
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

                    return false;
                }
            });

            btnvolveractivityproductoreslistview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent objectIntent = new Intent(getApplicationContext(),ActivityListViewProveedoresSelect.class);

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

            btnactivityproductosparaacopio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 1 = Agregar, 2 = Modificar
                    validarAccionTipo = "1";

                    Intent objectIntent = new Intent(getApplicationContext(),ActivityListViewProductosSelectParaAcopio.class);

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

                    // 1 = Agregar, 2 = Modificar
                    objectIntent.putExtra("iptAccionTipo", validarAccionTipo);

                    startActivity(objectIntent);
                    finish();

                }
            });

            btnguardaracopio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        Intent objectIntent = new Intent(getApplicationContext(),ActivityListViewProveedoresSelect.class);

                        objectIntent.putExtra("iPeNombres", parPeNombres);
                        objectIntent.putExtra("iPeApellidos", parPeApellidos);
                        objectIntent.putExtra("iPeCorreo", parPeCorreo);
                        objectIntent.putExtra("iPeAccesoConfiguracion", parPeAccesoConfiguracion);
                        objectIntent.putExtra("iPeAccesoBajarDatos", parPeAccesoBajarDatos);
                        objectIntent.putExtra("iPeAccesoSubirDatos", parPeAccesoSubirDatos);
                        objectIntent.putExtra("iPeAccesoRegistroProductores", parPeAccesoRegistroProductores);
                        objectIntent.putExtra("iPeAccesoRegistroAcopio", parPeAccesoRegistroAcopio);

                        ElminarProductoAcopioPartidaTemporal();

                        startActivity(objectIntent);
                        finish();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void ObtenerNumeroDeRecibo() {
        try{
            SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();
            String ConsultaSql = "SELECT * FROM " + Transacciones.tablaconfiguraciones + " WHERE ConfiguracionId = '" + dispositivoId + "'";

            Cursor objectCursor = objectSqLiteDatabase.rawQuery(ConsultaSql,null);

            String sufijoDocumento = "",nuevoDocumento = "", reciboNo = "";

            if (objectCursor.getCount()!=0){
                while (objectCursor.moveToNext()){
                    sufijoDocumento = objectCursor.getString(1).toString();
                    // definimos el nuevo numero de recibo (ultimoDucumento + 1)
                    nuevoDocumento = String.valueOf(objectCursor.getInt(2) + 1);
                }
                reciboNo = sufijoDocumento + "-" + Replicar("0", 8 - nuevoDocumento.length()) + nuevoDocumento;
                textViewNoReciboAcopio.setText(reciboNo);
            }else{
                Toast.makeText(getApplicationContext(),"No existe configuración",Toast.LENGTH_SHORT).show();
            }
            objectCursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //Replicar caracter
    private String Replicar(String ls_ParametroValor, int li_ParametroCantidadRepetir) {
        String ls_Resultado = "";
        if (li_ParametroCantidadRepetir > 0) {
            for (int li_Contadorfila = 1; li_Contadorfila <= li_ParametroCantidadRepetir; li_Contadorfila++) {
                ls_Resultado = ls_Resultado + ls_ParametroValor;
            }
        }
        return ls_Resultado;
    }

    private void ElminarProductoAcopioPartidaTemporal() {
        SQLiteDatabase db = objectSqLiteConexion.getWritableDatabase();

        Cursor objectCursor = db.rawQuery("SELECT * FROM " + Transacciones.tablaacopiopartidatmp, null);

        if(objectCursor.moveToNext()){
            db.delete(Transacciones.tablaacopiopartidatmp, null, null);
            db.close();
        }
        objectCursor.close();
    }

    private void ObtenerListaAcopioPartidasTemporal() {
        SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getReadableDatabase();

        Cursor objectCursor = objectSqLiteDatabase.rawQuery("SELECT * FROM " + Transacciones.tablaacopiopartidatmp, null);

        while (objectCursor.moveToNext()){
            objectTablaAcopioPartidaTemporalListaAcopioPartidaTemporal = new TablaAcopioPartidaTemporal();
            objectTablaAcopioPartidaTemporalListaAcopioPartidaTemporal.setAcopioPartidaNo(objectCursor.getInt(0));
            objectTablaAcopioPartidaTemporalListaAcopioPartidaTemporal.setAcopioPartidaProductoClave(objectCursor.getString(1));
            objectTablaAcopioPartidaTemporalListaAcopioPartidaTemporal.setAcopioPartidaProductoDescripcion(objectCursor.getString(2));
            objectTablaAcopioPartidaTemporalListaAcopioPartidaTemporal.setAcopioPartidaProductoCantidad(objectCursor.getDouble(3));
            objectTablaAcopioPartidaTemporalListaAcopioPartidaTemporal.setAcopioPartidaProductoPrecio(objectCursor.getDouble(4));
            objectTablaAcopioPartidaTemporalListaAcopioPartidaTemporal.setAcopioPartidaProductoSubTotal(objectCursor.getDouble(5));

            objectArrayListTablaAcopioPartidaTemporalLista.add(objectTablaAcopioPartidaTemporalListaAcopioPartidaTemporal);

            subTotal = subTotal + objectCursor.getDouble(5);
        }

        objectCursor.close();
        objectSqLiteConexion.close();

        total = subTotal + impuesto;

        subtotalacopio_input.setText(String.format(subTotal.toString(), "%.2f"));
        impuestosacopio_input.setText(String.format(impuesto.toString(),"%.2f"));
        totalacopio_input.setText(String.format(total.toString(), "%.2f"));
    }

    private void EliminarFilaListViewAcopio(Integer filaNumero) {
        SQLiteDatabase db = objectSqLiteConexion.getWritableDatabase();
        String [] params = { filaNumero.toString() };
        String wherecond = Transacciones.AcopioPartidaNo + "=?";
        db.delete(Transacciones.tablaacopiopartidatmp, wherecond, params);

        db.close();

        subTotal = 0.00;
        impuesto = 0.00;
        total = 0.00;
    }

    private void ActualizarCantidadFilaListViewAcopio(Integer filaNumero){
        SQLiteDatabase db = objectSqLiteConexion.getWritableDatabase();

        String [] params = { filaNumero.toString() };

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.AcopioPartidaProductoCantidad, tpPartidaProductoCantidad);
        valores.put(Transacciones.AcopioPartidaProductoSubTotal, tpPartidaProductoCantidad * tpPartidaProductoPrecio);
        db.update(Transacciones.tablaacopiopartidatmp, valores, Transacciones.AcopioPartidaNo + "=?", params);

        db.close();

        subTotal = 0.00;
        impuesto = 0.00;
        total = 0.00;
    }

    private void ActualizarPrecioFilaListViewAcopio(Integer filaNumero){
        SQLiteDatabase db = objectSqLiteConexion.getWritableDatabase();

        String [] params = { filaNumero.toString() };

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.AcopioPartidaProductoPrecio, tpPartidaProductoPrecio);
        valores.put(Transacciones.AcopioPartidaProductoSubTotal, tpPartidaProductoCantidad * tpPartidaProductoPrecio);
        db.update(Transacciones.tablaacopiopartidatmp, valores, Transacciones.AcopioPartidaNo + "=?", params);

        db.close();

        subTotal = 0.00;
        impuesto = 0.00;
        total = 0.00;
    }

}