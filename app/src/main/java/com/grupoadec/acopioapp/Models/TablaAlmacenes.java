package com.grupoadec.acopioapp.Models;

public class TablaAlmacenes {
    private Integer AlmacenClave;
    private String AlmacenDescripcion;

    public TablaAlmacenes(Integer almacenClave, String almacenDescripcion) {
        AlmacenClave = almacenClave;
        AlmacenDescripcion = almacenDescripcion;
    }

    public TablaAlmacenes(){};

    public Integer getAlmacenClave() {return AlmacenClave;}

    public void setAlmacenClave(Integer almacenClave) {AlmacenClave = almacenClave;}

    public String getAlmacenDescripcion() {return AlmacenDescripcion;}

    public void setAlmacenDescripcion(String almacenDescripcion) {AlmacenDescripcion = almacenDescripcion;}
}
