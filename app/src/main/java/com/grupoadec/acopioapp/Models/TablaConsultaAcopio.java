package com.grupoadec.acopioapp.Models;

import java.util.Date;

public class TablaConsultaAcopio {
    private String AcopioDocumento;
    private Date AcopioFecha;
    private String AcopioClaveProductor;
    private String AcopioNombreProductor;
    private Double AcopioImporte;

    public TablaConsultaAcopio(String acopioDocumento, Date acopioFecha, String acopioClaveProductor, String acopioNombreProductor, Double acopioImporte) {
        AcopioDocumento = acopioDocumento;
        AcopioFecha = acopioFecha;
        AcopioClaveProductor = acopioClaveProductor;
        AcopioNombreProductor = acopioNombreProductor;
        AcopioImporte = acopioImporte;
    }

    public  TablaConsultaAcopio(){};

    public String getAcopioDocumento() {return AcopioDocumento;}

    public void setAcopioDocumento(String acopioDocumento) {AcopioDocumento = acopioDocumento;}

    public Date getAcopioFecha() {return AcopioFecha;}

    public void setAcopioFecha(Date acopioFecha) {AcopioFecha = acopioFecha;}

    public String getAcopioClaveProductor() {return AcopioClaveProductor;}

    public void setAcopioClaveProductor(String acopioClaveProductor) {AcopioClaveProductor = acopioClaveProductor;}

    public String getAcopioNombreProductor() {return AcopioNombreProductor;}

    public void setAcopioNombreProductor(String acopioNombreProductor) {AcopioNombreProductor = acopioNombreProductor;}

    public Double getAcopioImporte() {return AcopioImporte;}

    public void setAcopioImporte(Double acopioImporte) {AcopioImporte = acopioImporte;}
}
