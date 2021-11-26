package com.grupoadec.acopioapp.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grupoadec.acopioapp.Models.TablaFotosAcopio;
import com.grupoadec.acopioapp.R;

import java.util.ArrayList;

public class ListaFotosAcopioDocumentoRVAdapter extends RecyclerView.Adapter<ListaFotosAcopioDocumentoRVAdapter.RVViewHolderClass> {

    ArrayList<TablaFotosAcopio> objectTablaFotosAcopioList;

    public ListaFotosAcopioDocumentoRVAdapter(ArrayList<TablaFotosAcopio> objectTablaFotosAcopioList) {
        this.objectTablaFotosAcopioList = objectTablaFotosAcopioList;
    }

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVViewHolderClass(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elementos_customview_imagenesacopio,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolderClass holder, int position) {
        TablaFotosAcopio objectTablaFotosAcopio=objectTablaFotosAcopioList.get(position);
        holder.objectImageView.setImageBitmap(objectTablaFotosAcopio.getFotoacopioimagen());
    }

    @Override
    public int getItemCount() {
        return objectTablaFotosAcopioList.size();
    }

    public class RVViewHolderClass extends RecyclerView.ViewHolder {
        ImageView objectImageView;

        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            objectImageView=itemView.findViewById(R.id.sr_imageIV);
        }
    }
}
