package com.grupoadec.acopioapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.grupoadec.acopioapp.Adaptadores.ListaAcopiosAdapter;
import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;
import com.grupoadec.acopioapp.Models.TablaConsultaAcopio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ImpresionESCPOS.ImpresionESCPOS;

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

    AlertDialog.Builder objectAlertDialogBuilderConfirmarImprimirAcopio;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_consulta_acopio);

        try{
            objectSqLiteConexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
            btnvolveractivitymainacopio = (ImageView) findViewById(R.id.btnvolveractivityphotomainacopio);
            acopio_listview = (ListView) findViewById(R.id.acopio_listview);
            consultaracopios_input = (EditText) findViewById(R.id.consultaracopios_input);

            objectAlertDialogBuilderOpciones = new AlertDialog.Builder(this);
            objectAlertDialogBuilderConfirmarImprimirAcopio = new AlertDialog.Builder(this);

            // llenamos variables con los datos del putExtra
            String parPeNombres = getIntent().getStringExtra("peNombre");
            String parPeApellidos = getIntent().getStringExtra("peApellidos");
            String parPeCorreo = getIntent().getStringExtra("peCorreo");

            String parPeAccesoBajarDatos = getIntent().getStringExtra("iPeAccesoBajarDatos");
            String parPeAccesoSubirDatos = getIntent().getStringExtra("iPeAccesoSubirDatos");
            String parPeAccesoConfiguracion = getIntent().getStringExtra("iPeAccesoConfiguracion");
            String parPeAccesoRegistroAcopio = getIntent().getStringExtra("iPeAccesoRegistroAcopio");
            String parPeAccesoRegistroProductores = getIntent().getStringExtra("iPeAccesoRegistroProductores");

            String parPeAlmacenClave = getIntent().getStringExtra("ipeAlmacenClave");
            String parPeAlmacenDescripcion = getIntent().getStringExtra("ipeAlmacenDescripcion");

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

            consultaracopios_input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    objectAdapter.filtrarDocumento(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            acopio_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TablaConsultaAcopio tca = objectArrayListTablaConsultaAcopioLista.get(position);
                    String documentoImprimir = tca.getAcopioDocumento();
                    String documentoProductorNombre = tca.getAcopioNombreProductor();

                    final EditText cantidadInput = new EditText(getApplicationContext());
                    cantidadInput.setInputType(InputType.TYPE_CLASS_PHONE);
                    cantidadInput.setText("2");

                    objectAlertDialogBuilderConfirmarImprimirAcopio.setMessage("Desea imprimir el Recibo No. " + documentoImprimir + " del productor, " + documentoProductorNombre+"\n\n"+"Ingrese las copias a imprimir")
                            .setView(cantidadInput)
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try {
                                        ImprimirReciboAcopio(documentoImprimir,cantidadInput.getText().toString());
                                    } catch (EscPosConnectionException e) {
                                        e.printStackTrace();
                                    } catch (EscPosEncodingException e) {
                                        e.printStackTrace();
                                    } catch (EscPosBarcodeException e) {
                                        e.printStackTrace();
                                    } catch (EscPosParserException e) {
                                        e.printStackTrace();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    //dialog.cancel();
                                    Toast.makeText(getApplicationContext(),"No se imprimio el Recibo No. " + documentoImprimir,
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alertConfirmarImprimirRecibo = objectAlertDialogBuilderConfirmarImprimirAcopio.create();
                    //Setting the title manually
                    alertConfirmarImprimirRecibo.setTitle("Alerta");
                    alertConfirmarImprimirRecibo.show();
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


    private void ImprimirReciboAcopio(String Documento,String CopiasImprimir) throws EscPosConnectionException, EscPosEncodingException, EscPosBarcodeException, EscPosParserException, InterruptedException {
        try{
            ImpresionESCPOS LI_Imprimir= new ImpresionESCPOS();
            SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();
            String ConsultaSql;
            Cursor objectCursor;
            Integer Copias=Integer.valueOf(CopiasImprimir);

            ConsultaSql = "SELECT A.CompraEncabezadoDocumento,A.CompraEncabezadoFecha,A.CompraEncabezadoProveedorClave,A.CompraEncabezadoEstado,A.CompraEncabezadoSubTotal,A.CompraEncabezadoImpuesto,A.CompraEncabezadoTotal," +
                    "B.CompraPartidaProductoClave,D.ProductoDescripcion,B.CompraPartidaCantidad,B.CompraPartidaCosto,B.CompraPartidaImpuesto,B.CompraPartidaTotal,"+
                    "C.ProveedorNombre,C.ProveedorRtn,C.ProveedorCalle, CASE WHEN trim(C.ProveedorCertificacion) = 'ECOLOGICO' THEN 'ECOLOGICO/FAIRTRADE' WHEN trim(C.ProveedorCertificacion) = 'CONVENCIONAL' THEN 'CONVENCIONAL/FAIRTRADE' WHEN trim(C.ProveedorCertificacion) = 'CONVERSIÓN' THEN 'CONVERSIÓN/FAIRTRADE' ELSE '' END AS  ProveedorCertificacion "+
                    " FROM " + Transacciones.tablacomprasencabezado + " A " +
                    " INNER JOIN "+Transacciones.tablacompraspartida+" B ON B."+Transacciones.CompraPartidaDocumento+"=A."+Transacciones.CompraEncabezadoDocumento+
                    " INNER JOIN "+Transacciones.tablaproveedores+" C ON C."+Transacciones.ProveedorClave+"=A."+Transacciones.CompraEncabezadoProveedorClave+
                    " INNER JOIN "+Transacciones.tablaproductos+" D ON D."+Transacciones.ProductoClave+"=B."+Transacciones.CompraPartidaProductoClave+
                    " WHERE A.CompraEncabezadoDocumento = '" + Documento + "'";

            objectCursor = objectSqLiteDatabase.rawQuery(ConsultaSql,null);
            if (objectCursor.getCount()!=0){
                LI_Imprimir.ImprimirDocumento(objectCursor,"ORIGINAL",Copias);

            }
            else{
                Toast.makeText(getApplicationContext(),"No se obtuvo informacion",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


}