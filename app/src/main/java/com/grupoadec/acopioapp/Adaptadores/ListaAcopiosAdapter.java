package com.grupoadec.acopioapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Models.TablaConsultaAcopio;
import com.grupoadec.acopioapp.R;

import java.util.ArrayList;

public class ListaAcopiosAdapter extends BaseAdapter {

    private static LayoutInflater objectInflater = null;
    Context objectContext;

    ArrayList<TablaConsultaAcopio> objectArrayListTablaConsultaAcopio;
    ArrayList<TablaConsultaAcopio> objectArrayListTablaConsultaAcopioOriginal;


    public ListaAcopiosAdapter(Context objectContext, ArrayList<TablaConsultaAcopio> objectArrayListTablaConsultaAcopio) {
        try{
            this.objectContext = objectContext;
            this.objectArrayListTablaConsultaAcopio = objectArrayListTablaConsultaAcopio;

            objectArrayListTablaConsultaAcopioOriginal = new ArrayList<>();
            objectArrayListTablaConsultaAcopioOriginal.addAll(objectArrayListTablaConsultaAcopio);

            objectInflater = (LayoutInflater)objectContext.getSystemService(objectContext.LAYOUT_INFLATER_SERVICE);
        }catch (Exception e){
            Toast.makeText(objectContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View objectView = objectInflater.inflate(R.layout.elementos_customview_consultaproductosacopio,null);

        try{

            TextView textViewDocumentoConsultaAcopio = (TextView) objectView.findViewById(R.id.textViewDocumentoConsultaAcopio);
            TextView textViewFechaConsultaAcopio = (TextView) objectView.findViewById(R.id.textViewFechaConsultaAcopio);
            TextView textViewImporteConsultaAcopio = (TextView) objectView.findViewById(R.id.textViewImporteConsultaAcopio);
            TextView textViewNombreProductorConsultaAcopio = (TextView) objectView.findViewById(R.id.textViewNombreProductorConsultaAcopio);

            textViewDocumentoConsultaAcopio.setText(objectArrayListTablaConsultaAcopio.get(position).getAcopioDocumento());
            textViewFechaConsultaAcopio.setText(objectArrayListTablaConsultaAcopio.get(position).getAcopioFecha().toString());
            textViewImporteConsultaAcopio.setText(objectArrayListTablaConsultaAcopio.get(position).getAcopioImporte().toString());
            textViewNombreProductorConsultaAcopio.setText(objectArrayListTablaConsultaAcopio.get(position).getAcopioNombreProductor());

        }catch (Exception e){
            Toast.makeText(objectContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return objectView;
    }

    @Override
    public int getCount() {
        return objectArrayListTablaConsultaAcopio.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
