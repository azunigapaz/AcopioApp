package com.grupoadec.acopioapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Adaptadores.ListaFotosAcopioDocumentoRVAdapter;
import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;
import com.grupoadec.acopioapp.Models.TablaFotosAcopio;

import java.util.ArrayList;

public class ActivityListViewPhotosAcopio extends AppCompatActivity {
    SQLiteConexion objectSqLiteConexion;
    private RecyclerView objectRecyclerView;

    private ListaFotosAcopioDocumentoRVAdapter objectRvAdapter;

    ImageView btnvolveractivityphotoacopio;

    String parPeProveedorClave;
    String parPeProveedorNombre;
    String parPeProveedorRtn;
    String parPeAlmacenClave;
    String parPeAlmacenDescripcion;
    String parPeNumeroDocumento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_photos_acopio);

        try{
            objectRecyclerView =findViewById(R.id.rvListaFotosAcopio);
            objectSqLiteConexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);

            btnvolveractivityphotoacopio=(ImageView) findViewById(R.id.btnvolveractivityphotoacopio);

            parPeNumeroDocumento = getIntent().getStringExtra("ipeNumeroDocumento");

            // llenamos variables con los datos del putExtra
            String parPeNombres = getIntent().getStringExtra("iPeNombres");
            String parPeApellidos = getIntent().getStringExtra("iPeApellidos");
            String parPeCorreo = getIntent().getStringExtra("iPeCorreo");

            String parPeAccesoBajarDatos = getIntent().getStringExtra("iPeAccesoBajarDatos");
            String parPeAccesoSubirDatos = getIntent().getStringExtra("iPeAccesoSubirDatos");
            String parPeAccesoConfiguracion = getIntent().getStringExtra("iPeAccesoConfiguracion");
            String parPeAccesoRegistroAcopio = getIntent().getStringExtra("iPeAccesoRegistroAcopio");
            String parPeAccesoRegistroProductores = getIntent().getStringExtra("iPeAccesoRegistroProductores");

            parPeProveedorClave = getIntent().getStringExtra("iptProveedorClave");
            parPeProveedorNombre = getIntent().getStringExtra("iptProveedorNombre");
            parPeProveedorRtn = getIntent().getStringExtra("iptProveedorRtn");
            String parPeValidacionNuevaFactura = getIntent().getStringExtra("iPeNuevaFactura");
            parPeAlmacenClave = getIntent().getStringExtra("ipeAlmacenClave");
            parPeAlmacenDescripcion = getIntent().getStringExtra("ipeAlmacenDescripcion");


            ObtenerImagenesAcopio();


            btnvolveractivityphotoacopio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent objectIntent = new Intent(getApplicationContext(),ActivityPhotoAcopio.class);

                    objectIntent.putExtra("ipeAlmacenClave", parPeAlmacenClave);
                    objectIntent.putExtra("ipeAlmacenDescripcion", parPeAlmacenDescripcion);

                    objectIntent.putExtra("iptProveedorClave", parPeProveedorClave);
                    objectIntent.putExtra("iptProveedorNombre", parPeProveedorNombre);
                    objectIntent.putExtra("iptProveedorRtn", parPeProveedorRtn);

                    objectIntent.putExtra("iPeNombres", parPeNombres);
                    objectIntent.putExtra("iPeApellidos", parPeApellidos);
                    objectIntent.putExtra("iPeCorreo", parPeCorreo);
                    objectIntent.putExtra("iPeAccesoConfiguracion", parPeAccesoConfiguracion);
                    objectIntent.putExtra("iPeAccesoBajarDatos", parPeAccesoBajarDatos);
                    objectIntent.putExtra("iPeAccesoSubirDatos", parPeAccesoSubirDatos);
                    objectIntent.putExtra("iPeAccesoRegistroProductores", parPeAccesoRegistroProductores);
                    objectIntent.putExtra("iPeAccesoRegistroAcopio", parPeAccesoRegistroAcopio);

                    objectIntent.putExtra("iPeNuevaFactura", "1");

                    objectIntent.putExtra("ipeNumeroDocumento", parPeNumeroDocumento);

                    startActivity(objectIntent);
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void ObtenerImagenesAcopio(){
        try {
            String documentoAcopio = parPeNumeroDocumento;
            SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getReadableDatabase();

            ArrayList<TablaFotosAcopio> objectTablaFotosAcopioList=new ArrayList<>();
            Cursor objectCursor = objectSqLiteDatabase.rawQuery("SELECT * FROM " + Transacciones.tablafotosacopio + " WHERE FotosAcopioDocumento ='"+ documentoAcopio + "'", null);

            if(objectCursor.getCount()!=0){
                while (objectCursor.moveToNext()){
                    Integer fotosAcopioId=objectCursor.getInt(0);
                    String fotoAcopioDocumento=objectCursor.getString(1);
                    byte [] imageBytes=objectCursor.getBlob(2);

                    Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes, 0 , imageBytes.length);
                    objectTablaFotosAcopioList.add(new TablaFotosAcopio(fotosAcopioId,fotoAcopioDocumento,objectBitmap));
                }
            }

            objectRvAdapter=new ListaFotosAcopioDocumentoRVAdapter(objectTablaFotosAcopioList);
            objectRecyclerView.setHasFixedSize(true);

            objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            objectRecyclerView.setAdapter(objectRvAdapter);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}