package com.grupoadec.acopioapp.Models;

public class TablaAcopioPartidaTemporal {
    private Integer AcopioPartidaNo;
    private String AcopioPartidaProductoClave;
    private String AcopioPartidaProductoDescripcion;
    private Double AcopioPartidaProductoCantidad;
    private Double AcopioPartidaProductoPrecio;
    private Double AcopioPartidaProductoSubTotal;

    public TablaAcopioPartidaTemporal(Integer acopioPartidaNo, String acopioPartidaProductoClave, String acopioPartidaProductoDescripcion, Double acopioPartidaProductoCantidad, Double acopioPartidaProductoPrecio, Double acopioPartidaProductoSubTotal) {
        AcopioPartidaNo = acopioPartidaNo;
        AcopioPartidaProductoClave = acopioPartidaProductoClave;
        AcopioPartidaProductoDescripcion = acopioPartidaProductoDescripcion;
        AcopioPartidaProductoCantidad = acopioPartidaProductoCantidad;
        AcopioPartidaProductoPrecio = acopioPartidaProductoPrecio;
        AcopioPartidaProductoSubTotal = acopioPartidaProductoSubTotal;
    }

    public TablaAcopioPartidaTemporal(){};

    public Integer getAcopioPartidaNo() {return AcopioPartidaNo;}

    public void setAcopioPartidaNo(Integer acopioPartidaNo) {AcopioPartidaNo = acopioPartidaNo;}

    public String getAcopioPartidaProductoClave() {return AcopioPartidaProductoClave;}

    public void setAcopioPartidaProductoClave(String acopioPartidaProductoClave) {AcopioPartidaProductoClave = acopioPartidaProductoClave;}

    public String getAcopioPartidaProductoDescripcion() {return AcopioPartidaProductoDescripcion;}

    public void setAcopioPartidaProductoDescripcion(String acopioPartidaProductoDescripcion) {AcopioPartidaProductoDescripcion = acopioPartidaProductoDescripcion;}

    public Double getAcopioPartidaProductoCantidad() {return AcopioPartidaProductoCantidad;}

    public void setAcopioPartidaProductoCantidad(Double acopioPartidaProductoCantidad) {AcopioPartidaProductoCantidad = acopioPartidaProductoCantidad;}

    public Double getAcopioPartidaProductoPrecio() {return AcopioPartidaProductoPrecio;}

    public void setAcopioPartidaProductoPrecio(Double acopioPartidaProductoPrecio) {AcopioPartidaProductoPrecio = acopioPartidaProductoPrecio;}

    public Double getAcopioPartidaProductoSubTotal() {return AcopioPartidaProductoSubTotal;}

    public void setAcopioPartidaProductoSubTotal(Double acopioPartidaProductoSubTotal) {AcopioPartidaProductoSubTotal = acopioPartidaProductoSubTotal;}
}
