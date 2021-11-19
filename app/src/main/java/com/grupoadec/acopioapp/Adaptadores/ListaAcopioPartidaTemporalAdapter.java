package com.grupoadec.acopioapp.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.grupoadec.acopioapp.Models.TablaAcopioPartidaTemporal;
import com.grupoadec.acopioapp.R;

import java.util.ArrayList;

public class ListaAcopioPartidaTemporalAdapter extends BaseAdapter {

    private static LayoutInflater objectInflater = null;
    Context objectContext;
    ArrayList<TablaAcopioPartidaTemporal> objectArrayListTablaAcopioPartidaTemporal;
    ArrayList<TablaAcopioPartidaTemporal> objectArrayListTablaAcopioPartidaTemporalOriginal;

    public ListaAcopioPartidaTemporalAdapter(Context objectContext, ArrayList<TablaAcopioPartidaTemporal> objectArrayListTablaAcopioPartidaTemporal) {
        try{
            this.objectContext = objectContext;
            this.objectArrayListTablaAcopioPartidaTemporal = objectArrayListTablaAcopioPartidaTemporal;

            objectArrayListTablaAcopioPartidaTemporalOriginal = new ArrayList<>();
            objectArrayListTablaAcopioPartidaTemporalOriginal.addAll(objectArrayListTablaAcopioPartidaTemporal);

            objectInflater = (LayoutInflater)objectContext.getSystemService(objectContext.LAYOUT_INFLATER_SERVICE);
        }catch (Exception e){
            Toast.makeText(objectContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View objectView = objectInflater.inflate(R.layout.elementos_custom_view_acopiopartidas_temporal,null);

        try{
            TextView textViewAcopioCodigoTemporal = (TextView) objectView.findViewById(R.id.textViewAcopioCodigoTemporal);
            TextView textViewAcopioDescripcionTemporal = (TextView) objectView.findViewById(R.id.textViewAcopioDescripcionTemporal);
            TextView textViewAcopioItemNoTemporal = (TextView) objectView.findViewById(R.id.textViewAcopioItemNoTemporal);
            TextView textViewAcopioCantidadTemporal = (TextView) objectView.findViewById(R.id.textViewAcopioCantidadTemporal);
            TextView textViewAcopioPrecioTemporal = (TextView) objectView.findViewById(R.id.textViewAcopioPrecioTemporal);
            TextView textViewAcopioTotalPartidaTemporal = (TextView) objectView.findViewById(R.id.textViewAcopioTotalPartidaTemporal);

            textViewAcopioCodigoTemporal.setText(objectArrayListTablaAcopioPartidaTemporal.get(i).getAcopioPartidaProductoClave());
            textViewAcopioDescripcionTemporal.setText(objectArrayListTablaAcopioPartidaTemporal.get(i).getAcopioPartidaProductoDescripcion());
            textViewAcopioItemNoTemporal.setText(objectArrayListTablaAcopioPartidaTemporal.get(i).getAcopioPartidaNo().toString());
            textViewAcopioCantidadTemporal.setText(objectArrayListTablaAcopioPartidaTemporal.get(i).getAcopioPartidaProductoCantidad().toString());
            textViewAcopioPrecioTemporal.setText(objectArrayListTablaAcopioPartidaTemporal.get(i).getAcopioPartidaProductoPrecio().toString());
            textViewAcopioTotalPartidaTemporal.setText(objectArrayListTablaAcopioPartidaTemporal.get(i).getAcopioPartidaProductoSubTotal().toString());


        }catch (Exception e){
            Toast.makeText(objectContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return objectView;
    }

    @Override
    public int getCount() {
        return objectArrayListTablaAcopioPartidaTemporal.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}
