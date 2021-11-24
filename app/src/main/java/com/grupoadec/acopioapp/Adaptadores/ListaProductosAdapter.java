package com.grupoadec.acopioapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Models.TablaProductos;
import com.grupoadec.acopioapp.R;

import java.util.ArrayList;

public class ListaProductosAdapter extends BaseAdapter {

    private static LayoutInflater objectInflater = null;
    Context objectContext;
    ArrayList<TablaProductos> objectArrayListTablaProductos;
    ArrayList<TablaProductos> objectArrayListTablaProductosOriginal;

    public ListaProductosAdapter(Context objectContext, ArrayList<TablaProductos> objectArrayListTablaProductos) {
        try{
            this.objectContext = objectContext;
            this.objectArrayListTablaProductos = objectArrayListTablaProductos;

            objectArrayListTablaProductosOriginal = new ArrayList<>();
            objectArrayListTablaProductosOriginal.addAll(objectArrayListTablaProductos);

            objectInflater = (LayoutInflater)objectContext.getSystemService(objectContext.LAYOUT_INFLATER_SERVICE);
        }catch (Exception e){
            Toast.makeText(objectContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View objectView = objectInflater.inflate(R.layout.elementos_customview_productos,null);

        try{
            TextView textViewDescripcionProductoCvProd = (TextView) objectView.findViewById(R.id.textViewDocumentoConsultaAcopio);
            TextView textViewClaveProductoCvProd = (TextView) objectView.findViewById(R.id.textViewFechaConsultaAcopio);
            TextView textViewCostoProductoCvProd = (TextView) objectView.findViewById(R.id.textViewImporteConsultaAcopio);

            textViewDescripcionProductoCvProd.setText(objectArrayListTablaProductos.get(i).getProductoDescripcion());
            textViewClaveProductoCvProd.setText(objectArrayListTablaProductos.get(i).getProductoClave());
            textViewCostoProductoCvProd.setText(objectArrayListTablaProductos.get(i).getProductoCosto().toString());


        }catch (Exception e){
            Toast.makeText(objectContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        return objectView;
    }

    @Override
    public int getCount() {

        return objectArrayListTablaProductos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void filtrarProductoDescripcionCodigo(final CharSequence txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud > 0) {
            objectArrayListTablaProductos.clear();
            for(int i = 0; i < objectArrayListTablaProductosOriginal.size(); i++){
                if(objectArrayListTablaProductosOriginal.get(i).getProductoDescripcion().toLowerCase().contains(txtBuscar.toString().toLowerCase()) || objectArrayListTablaProductosOriginal.get(i).getProductoClave().toLowerCase().contains(txtBuscar.toString().toLowerCase())){
                    objectArrayListTablaProductos.add(objectArrayListTablaProductosOriginal.get(i));
                }
            }
            notifyDataSetChanged();
        }else{
            objectArrayListTablaProductos.clear();
            objectArrayListTablaProductos.addAll(objectArrayListTablaProductosOriginal);
            notifyDataSetChanged();
        }
    }

}
