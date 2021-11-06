package com.grupoadec.acopioapp.models;

public class TablaProductos {
    private String ProductoClave;
    private String ProductoDescripcion;
    private Double ProductoCosto;
    private String ProductoLinea;

    public TablaProductos(String productoClave, String productoDescripcion, Double productoCosto, String productoLinea) {
        ProductoClave = productoClave;
        ProductoDescripcion = productoDescripcion;
        ProductoCosto = productoCosto;
        ProductoLinea = productoLinea;
    }

    public TablaProductos(){};

    public String getProductoClave() {return ProductoClave;}

    public void setProductoClave(String productoClave) {ProductoClave = productoClave;}

    public String getProductoDescripcion() {return ProductoDescripcion;}

    public void setProductoDescripcion(String productoDescripcion) {ProductoDescripcion = productoDescripcion;}

    public Double getProductoCosto() {return ProductoCosto;}

    public void setProductoCosto(Double productoCosto) {ProductoCosto = productoCosto;}

    public String getProductoLinea() {return ProductoLinea;}

    public void setProductoLinea(String productoLinea) {ProductoLinea = productoLinea;}
}
