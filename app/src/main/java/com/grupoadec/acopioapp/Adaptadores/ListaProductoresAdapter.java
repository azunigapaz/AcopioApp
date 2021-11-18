package com.grupoadec.acopioapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Models.TablaProveedores;
import com.grupoadec.acopioapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaProductoresAdapter extends BaseAdapter {

    private static LayoutInflater objectInflater = null;
    Context objectContext;
    ArrayList<TablaProveedores> objectArrayListTablaProveedores;
    ArrayList<TablaProveedores> objectArrayListTablaProveedoresOriginal;

    public ListaProductoresAdapter(Context objectContext, ArrayList<TablaProveedores> objectArrayListTablaProveedores) {
        try{
            this.objectContext = objectContext;
            this.objectArrayListTablaProveedores = objectArrayListTablaProveedores;

            objectArrayListTablaProveedoresOriginal = new ArrayList<>();
            objectArrayListTablaProveedoresOriginal.addAll(objectArrayListTablaProveedores);

            objectInflater = (LayoutInflater)objectContext.getSystemService(objectContext.LAYOUT_INFLATER_SERVICE);
        }catch (Exception e){
            Toast.makeText(objectContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View objectView = objectInflater.inflate(R.layout.elementos_custom_view_proveedores,null);

        try{
            TextView textViewProveedorNombreCvp = (TextView) objectView.findViewById(R.id.textViewProveedorNombreCvp);
            TextView textViewProveedorCodigoCvp = (TextView) objectView.findViewById(R.id.textViewProveedorCodigoCvp);
            TextView textViewProveedorRtnCvp = (TextView) objectView.findViewById(R.id.textViewProveedorRtnCvp);
            TextView textViewProveedorDireccionCvp = (TextView) objectView.findViewById(R.id.textViewProveedorDireccionCvp);
            TextView textViewProveedorCertificacionCvp = (TextView) objectView.findViewById(R.id.textViewProveedorCertificacionCvp);

            textViewProveedorNombreCvp.setText(objectArrayListTablaProveedores.get(i).getProveedorNombre());
            textViewProveedorCodigoCvp.setText(objectArrayListTablaProveedores.get(i).getProveedorClave());
            textViewProveedorRtnCvp.setText(objectArrayListTablaProveedores.get(i).getProveedorRtn());
            textViewProveedorDireccionCvp.setText(objectArrayListTablaProveedores.get(i).getProveedorCalle());
            textViewProveedorCertificacionCvp.setText(objectArrayListTablaProveedores.get(i).getProveedorCertificacion());

        }catch (Exception e){
            Toast.makeText(objectContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return objectView;
    }

    @Override
    public int getCount() {

        return objectArrayListTablaProveedores.size();
    }

    @Override
    public Object getItem(int i) {

        return null;
    }

    @Override
    public long getItemId(int i) {

        return 0;
    }

    public void filtrarNombre(final CharSequence txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            objectArrayListTablaProveedores.clear();
            objectArrayListTablaProveedores.addAll(objectArrayListTablaProveedoresOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<TablaProveedores> collecion = objectArrayListTablaProveedores.stream()
                        .filter(i -> i.getProveedorNombre().toLowerCase().contains(txtBuscar.toString().toLowerCase()))
                        .collect(Collectors.toList());
                objectArrayListTablaProveedores.clear();
                objectArrayListTablaProveedores.addAll(collecion);
            } else {
                for (int i = 0; i < objectArrayListTablaProveedoresOriginal.size(); i++) {
                    if (objectArrayListTablaProveedoresOriginal.get(i).getProveedorNombre().toLowerCase().contains(txtBuscar.toString().toLowerCase()) || objectArrayListTablaProveedoresOriginal.get(i).getProveedorNombre().toLowerCase().contains(txtBuscar.toString().toLowerCase())) {
                        objectArrayListTablaProveedores.add(objectArrayListTablaProveedoresOriginal.get(i));
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filtrarProveedorNombreRtn(final CharSequence txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud > 0) {
            objectArrayListTablaProveedores.clear();
            for(int i = 0; i < objectArrayListTablaProveedoresOriginal.size(); i++){
                if(objectArrayListTablaProveedoresOriginal.get(i).getProveedorNombre().toLowerCase().contains(txtBuscar.toString().toLowerCase()) || objectArrayListTablaProveedoresOriginal.get(i).getProveedorRtn().toLowerCase().contains(txtBuscar.toString().toLowerCase())){
                    objectArrayListTablaProveedores.add(objectArrayListTablaProveedoresOriginal.get(i));
                }
            }
            notifyDataSetChanged();
        }else{
            objectArrayListTablaProveedores.clear();
            objectArrayListTablaProveedores.addAll(objectArrayListTablaProveedoresOriginal);
            notifyDataSetChanged();
        }
    }

}
