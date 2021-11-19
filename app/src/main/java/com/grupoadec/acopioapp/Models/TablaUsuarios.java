package com.grupoadec.acopioapp.Models;

public class TablaUsuarios {
    private String UsuarioId;
    private String UsuarioNombre;
    private String UsuarioApellido;
    private String UsuarioTelefono;
    private String UsuarioCorreo;
    private String UsuarioContrasenia;
    private String UsuarioNuevoRegistro;
    private String UsuarioAccesoConfiguracion;
    private String UsuarioAccesoBajarDatos;
    private String UsuarioAccesoSubirDatos;
    private String UsuarioAccesoRegistroProductores;
    private String UsuarioAccesoRegistroAcopio;
    private String UsuarioEstado;
    private String UsuarioFechaCreacion;

    public TablaUsuarios(String usuarioId, String usuarioNombre, String usuarioApellido, String usuarioTelefono, String usuarioCorreo, String usuarioContrasenia, String usuarioNuevoRegistro, String usuarioAccesoConfiguracion, String usuarioAccesoBajarDatos, String usuarioAccesoSubirDatos, String usuarioAccesoRegistroProductores, String usuarioAccesoRegistroAcopio, String usuarioEstado, String usuarioFechaCreacion) {
        UsuarioId = usuarioId;
        UsuarioNombre = usuarioNombre;
        UsuarioApellido = usuarioApellido;
        UsuarioTelefono = usuarioTelefono;
        UsuarioCorreo = usuarioCorreo;
        UsuarioContrasenia = usuarioContrasenia;
        UsuarioNuevoRegistro = usuarioNuevoRegistro;
        UsuarioAccesoConfiguracion = usuarioAccesoConfiguracion;
        UsuarioAccesoBajarDatos = usuarioAccesoBajarDatos;
        UsuarioAccesoSubirDatos = usuarioAccesoSubirDatos;
        UsuarioAccesoRegistroProductores = usuarioAccesoRegistroProductores;
        UsuarioAccesoRegistroAcopio = usuarioAccesoRegistroAcopio;
        UsuarioEstado = usuarioEstado;
        UsuarioFechaCreacion = usuarioFechaCreacion;
    }

    public String getUsuarioId() {return UsuarioId;}

    public void setUsuarioId(String usuarioId) {UsuarioId = usuarioId;}

    public String getUsuarioNombre() {return UsuarioNombre;}

    public void setUsuarioNombre(String usuarioNombre) {UsuarioNombre = usuarioNombre;}

    public String getUsuarioApellido() {return UsuarioApellido;}

    public void setUsuarioApellido(String usuarioApellido) {UsuarioApellido = usuarioApellido;}

    public String getUsuarioTelefono() {return UsuarioTelefono;}

    public void setUsuarioTelefono(String usuarioTelefono) {UsuarioTelefono = usuarioTelefono;}

    public String getUsuarioCorreo() {return UsuarioCorreo;}

    public void setUsuarioCorreo(String usuarioCorreo) {UsuarioCorreo = usuarioCorreo;}

    public String getUsuarioContrasenia() {return UsuarioContrasenia;}

    public void setUsuarioContrasenia(String usuarioContrasenia) {UsuarioContrasenia = usuarioContrasenia;}

    public String getUsuarioNuevoRegistro() {return UsuarioNuevoRegistro;}

    public void setUsuarioNuevoRegistro(String usuarioNuevoRegistro) {UsuarioNuevoRegistro = usuarioNuevoRegistro;}

    public String getUsuarioAccesoConfiguracion() {return UsuarioAccesoConfiguracion;}

    public void setUsuarioAccesoConfiguracion(String usuarioAccesoConfiguracion) {UsuarioAccesoConfiguracion = usuarioAccesoConfiguracion;}

    public String getUsuarioAccesoBajarDatos() {return UsuarioAccesoBajarDatos;}

    public void setUsuarioAccesoBajarDatos(String usuarioAccesoBajarDatos) {UsuarioAccesoBajarDatos = usuarioAccesoBajarDatos;}

    public String getUsuarioAccesoSubirDatos() {return UsuarioAccesoSubirDatos;}

    public void setUsuarioAccesoSubirDatos(String usuarioAccesoSubirDatos) {UsuarioAccesoSubirDatos = usuarioAccesoSubirDatos;}

    public String getUsuarioAccesoRegistroProductores() {return UsuarioAccesoRegistroProductores;}

    public void setUsuarioAccesoRegistroProductores(String usuarioAccesoRegistroProductores) {UsuarioAccesoRegistroProductores = usuarioAccesoRegistroProductores;}

    public String getUsuarioAccesoRegistroAcopio() {return UsuarioAccesoRegistroAcopio;}

    public void setUsuarioAccesoRegistroAcopio(String usuarioAccesoRegistroAcopio) {UsuarioAccesoRegistroAcopio = usuarioAccesoRegistroAcopio;}

    public String getUsuarioEstado() {return UsuarioEstado;}

    public void setUsuarioEstado(String usuarioEstado) {UsuarioEstado = usuarioEstado;}

    public String getUsuarioFechaCreacion() {return UsuarioFechaCreacion;}

    public void setUsuarioFechaCreacion(String usuarioFechaCreacion) {UsuarioFechaCreacion = usuarioFechaCreacion;}
}
