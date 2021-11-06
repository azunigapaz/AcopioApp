package com.grupoadec.acopioapp.models;

public class TablaProveedores {
    private String ProveedorClave;
    private String ProveedorNombre;
    private String ProveedorRtn;
    private String ProveedorCalle;
    private String ProveedorCruzamiento;
    private String ProveedorLocalidad;
    private String ProveedorMunicipio;
    private String ProveedorTelefono;
    private Double ProveedorSaldo;

    public TablaProveedores(String proveedorClave, String proveedorNombre, String proveedorRtn, String proveedorCalle, String proveedorCruzamiento, String proveedorLocalidad, String proveedorMunicipio, String proveedorTelefono, Double proveedorSaldo) {
        ProveedorClave = proveedorClave;
        ProveedorNombre = proveedorNombre;
        ProveedorRtn = proveedorRtn;
        ProveedorCalle = proveedorCalle;
        ProveedorCruzamiento = proveedorCruzamiento;
        ProveedorLocalidad = proveedorLocalidad;
        ProveedorMunicipio = proveedorMunicipio;
        ProveedorTelefono = proveedorTelefono;
        ProveedorSaldo = proveedorSaldo;
    }

    public TablaProveedores(){};

    public String getProveedorClave() {return ProveedorClave;}

    public void setProveedorClave(String proveedorClave) {ProveedorClave = proveedorClave;}

    public String getProveedorNombre() {return ProveedorNombre;}

    public void setProveedorNombre(String proveedorNombre) {ProveedorNombre = proveedorNombre;}

    public String getProveedorRtn() {return ProveedorRtn;}

    public void setProveedorRtn(String proveedorRtn) {ProveedorRtn = proveedorRtn;}

    public String getProveedorCalle() {return ProveedorCalle;}

    public void setProveedorCalle(String proveedorCalle) {ProveedorCalle = proveedorCalle;}

    public String getProveedorCruzamiento() {return ProveedorCruzamiento;}

    public void setProveedorCruzamiento(String proveedorCruzamiento) {ProveedorCruzamiento = proveedorCruzamiento;}

    public String getProveedorLocalidad() {return ProveedorLocalidad;}

    public void setProveedorLocalidad(String proveedorLocalidad) {ProveedorLocalidad = proveedorLocalidad;}

    public String getProveedorMunicipio() {return ProveedorMunicipio;}

    public void setProveedorMunicipio(String proveedorMunicipio) {ProveedorMunicipio = proveedorMunicipio;}

    public String getProveedorTelefono() {return ProveedorTelefono;}

    public void setProveedorTelefono(String proveedorTelefono) {ProveedorTelefono = proveedorTelefono;}

    public Double getProveedorSaldo() {return ProveedorSaldo;}

    public void setProveedorSaldo(Double proveedorSaldo) {ProveedorSaldo = proveedorSaldo;}
}
