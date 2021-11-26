package com.grupoadec.acopioapp.Models;

import android.graphics.Bitmap;

public class TablaFotosAcopio {
    private Integer fotoacopioid;
    private String fotoacopiodocumento;
    private Bitmap fotoacopioimagen;

    public TablaFotosAcopio(Integer fotoacopioid, String fotoacopiodocumento, Bitmap fotoacopioimagen) {
        this.fotoacopioid = fotoacopioid;
        this.fotoacopiodocumento = fotoacopiodocumento;
        this.fotoacopioimagen = fotoacopioimagen;
    }

    public TablaFotosAcopio(){};

    public Integer getFotoacopioid() {return fotoacopioid;}

    public void setFotoacopioid(Integer fotoacopioid) {this.fotoacopioid = fotoacopioid;}

    public String getFotoacopiodocumento() {return fotoacopiodocumento;}

    public void setFotoacopiodocumento(String fotoacopiodocumento) {this.fotoacopiodocumento = fotoacopiodocumento;}

    public Bitmap getFotoacopioimagen() {return fotoacopioimagen;}

    public void setFotoacopioimagen(Bitmap fotoacopioimagen) {this.fotoacopioimagen = fotoacopioimagen;}
}
