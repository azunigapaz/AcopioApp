package com.grupoadec.acopioapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Configuracion.SQLiteConexion;
import com.grupoadec.acopioapp.Configuracion.Transacciones;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ActivityPhotoAcopio extends AppCompatActivity {

    ImageView btnvolveractivityphotomainacopio,btntomarphotoacopio, photoacopio_imageview, btngaleriaphotosacopio;

    String parPeProveedorClave;
    String parPeProveedorNombre;
    String parPeProveedorRtn;

    String parPeAlmacenClave;
    String parPeAlmacenDescripcion;

    String parPeNumeroDocumento;

    private static final int PICK_IMAGE_REQUEST=100;
    static final int REQUEST_IMAGE_CAPTURE = 2;
    static final int PETICION_ACCESO_CAM = 101;

    private Uri imageFilePath;
    private Bitmap imageToStore;

    SQLiteConexion objectSqLiteConexion;

    String currentPhotoPath;

    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_acopio);

        try{
            objectSqLiteConexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);

            btnvolveractivityphotomainacopio = (ImageView) findViewById(R.id.btnvolveractivityphotomainacopio);
            btntomarphotoacopio = (ImageView) findViewById(R.id.btntomarphotoacopio);
            btngaleriaphotosacopio = (ImageView) findViewById(R.id.btngaleriaphotosacopio);
            // objetos para almacenar la foto
            photoacopio_imageview = (ImageView) findViewById(R.id.photoacopio_imageview);
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

            btnvolveractivityphotomainacopio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent objectIntent = new Intent(getApplicationContext(),ActivityMainAcopio.class);

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

                    objectIntent.putExtra("ipeAlmacenClave", parPeAlmacenClave);
                    objectIntent.putExtra("ipeAlmacenDescripcion", parPeAlmacenDescripcion);

                    objectIntent.putExtra("iPeNuevaFactura", "0");

                    startActivity(objectIntent);
                    finish();
                }
            });

            btngaleriaphotosacopio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent objectIntent = new Intent(getApplicationContext(),ActivityListViewPhotosAcopio.class);

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

                    objectIntent.putExtra("iPeNuevaFactura", "0");

                    objectIntent.putExtra("ipeNumeroDocumento", parPeNumeroDocumento);

                    startActivity(objectIntent);
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void chooseImage(View objectView){
        try {
            Intent objectIntent=new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent,PICK_IMAGE_REQUEST);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int RequestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(RequestCode, permissions, grantResults);

        if (RequestCode == PETICION_ACCESO_CAM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tomarPhoto();
            } else {
                Toast.makeText(getApplicationContext(), "Se necesita el permiso de camara", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
                imageFilePath=data.getData();
                imageToStore= MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);

                photoacopio_imageview.setImageBitmap(imageToStore);
            }

            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                // Bundle extras = data.getExtras();
                imageToStore = BitmapFactory.decodeFile(currentPhotoPath);
                photoacopio_imageview.setImageBitmap(imageToStore);
            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void storeImage(View view){
        try {
            if(photoacopio_imageview.getDrawable() !=null && imageToStore!=null){
                // aqui guardamos la imagen en la db
                //objectDatabaseHandler.storeImage(new ModelClass(imageDetailsET.getText().toString(),imageToStore));

                SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();
                Bitmap imageToStoreBitmap=imageToStore;

                objectByteArrayOutputStream=new ByteArrayOutputStream();
                imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,30,objectByteArrayOutputStream);

                imageInBytes=objectByteArrayOutputStream.toByteArray();

                ContentValues objectContentValuesStoreImage=new ContentValues();

                objectContentValuesStoreImage.put(Transacciones.FotosAcopioDocumento,parPeNumeroDocumento);
                objectContentValuesStoreImage.put(Transacciones.FotosAcopioImagen,imageInBytes);

                long checkIfQueryRuns = objectSqLiteDatabase.insert(Transacciones.tablafotosacopio, Transacciones.FotosAcopioId, objectContentValuesStoreImage);

                if(checkIfQueryRuns!=-1){
                    Toast.makeText(this, "Foto almacenada", Toast.LENGTH_SHORT).show();
                    objectSqLiteDatabase.close();
                }else {
                    Toast.makeText(this, "No se almaceno la foto", Toast.LENGTH_SHORT).show();
                }

                cleanObjects();
            }else{
                Toast.makeText(this, "Por favor escriba un texto y seleccione una imagen", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Para tomar foto
    public void permisos(View view) {
        // Valido si el permiso esta otorgado
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Otorgo el permiso si no lo tengo
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CAMERA }, PETICION_ACCESO_CAM);
        } else {
            tomarPhoto();
        }
    }

    private void tomarPhoto() {
        Intent TomarPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (TomarPhoto.resolveActivity(getPackageManager()) != null) {
            File photo = null;
            try {
                photo = createImageFile();
            } catch(IOException ex) {
                Log.e("Error", ex.toString());
            }
            if(photo != null) {
                imageFilePath = FileProvider.getUriForFile(this, "com.grupoadec.acopioapp.fileprovider", photo);
                TomarPhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageFilePath);
                startActivityForResult(TomarPhoto, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String imageFileName = "JPEG_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void cleanObjects(){

        String uri = "@drawable/coffee_logo_opacity";

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        photoacopio_imageview.setImageDrawable(res);
    }

}