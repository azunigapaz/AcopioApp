package com.grupoadec.acopioapp.models;

public class Usuario {
    private Integer id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String contrasenia;
    private Integer nuevoregistro;
    private Integer accesoconfiguracion;
    private Integer accesobajardatos;
    private Integer accesosubirdatos;
    private Integer accesoregistroprodcutores;
    private Integer accesoregistroacopio;
    private Integer estado;
    private String fechacreacion;

    public Usuario(Integer id, String nombre, String apellido, String telefono, String correo, String contrasenia, Integer nuevoregistro, Integer accesoconfiguracion, Integer accesobajardatos, Integer accesosubirdatos, Integer accesoregistroprodcutores, Integer accesoregistroacopio, Integer estado, String fechacreacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.nuevoregistro = nuevoregistro;
        this.accesoconfiguracion = accesoconfiguracion;
        this.accesobajardatos = accesobajardatos;
        this.accesosubirdatos = accesosubirdatos;
        this.accesoregistroprodcutores = accesoregistroprodcutores;
        this.accesoregistroacopio = accesoregistroacopio;
        this.estado = estado;
        this.fechacreacion = fechacreacion;
    }

    public Usuario(){};

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {return apellido;}

    public void setApellido(String apellido) {this.apellido = apellido;}

    public String getTelefono() {return telefono;}

    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getCorreo() {return correo;}

    public void setCorreo(String correo) {this.correo = correo;}

    public String getContrasenia() {return contrasenia;}

    public void setContrasenia(String contrasenia) {this.contrasenia = contrasenia;}

    public Integer getNuevoregistro() {return nuevoregistro;}

    public void setNuevoregistro(Integer nuevoregistro) {this.nuevoregistro = nuevoregistro;}

    public Integer getAccesoconfiguracion() {return accesoconfiguracion;}

    public void setAccesoconfiguracion(Integer accesoconfiguracion) {this.accesoconfiguracion = accesoconfiguracion;}

    public Integer getAccesobajardatos() {return accesobajardatos;}

    public void setAccesobajardatos(Integer accesobajardatos) {this.accesobajardatos = accesobajardatos;}

    public Integer getAccesosubirdatos() {return accesosubirdatos;}

    public void setAccesosubirdatos(Integer accesosubirdatos) {this.accesosubirdatos = accesosubirdatos;}

    public Integer getAccesoregistroprodcutores() {return accesoregistroprodcutores;}

    public void setAccesoregistroprodcutores(Integer accesoregistroprodcutores) {this.accesoregistroprodcutores = accesoregistroprodcutores;}

    public Integer getAccesoregistroacopio() {return accesoregistroacopio;}

    public void setAccesoregistroacopio(Integer accesoregistroacopio) {this.accesoregistroacopio = accesoregistroacopio;}

    public Integer getEstado() {return estado;}

    public void setEstado(Integer estado) {this.estado = estado;}

    public String getFechacreacion() {return fechacreacion;}

    public void setFechacreacion(String fechacreacion) {this.fechacreacion = fechacreacion;}
}
