package com.grupoadec.acopioapp.Configuracion;

public class Transacciones {
    // database Name
    public static final String NameDatabase = "AcopioAppDB";

    // definimos las tablas de la base de datos
    public static final String tablausuarios = "tblusuarios";
    // campos de la tabla usuarios
    public static final String UsuarioId = "UsuarioId";
    public static final String UsuarioNombre = "UsuarioNombre";
    public static final String UsuarioApellido = "UsuarioApellido";
    public static final String UsuarioTelefono = "UsuarioTelefono";
    public static final String UsuarioCorreo = "UsuarioCorreo";
    public static final String UsuarioContrasenia = "UsuarioContrasenia";
    public static final String UsuarioNuevoRegistro = "UsuarioNuevoRegistro";
    public static final String UsuarioAccesoConfiguracion = "UsuarioAccesoConfiguracion";
    public static final String UsuarioAccesoBajarDatos = "UsuarioAccesoBajarDatos";
    public static final String UsuarioAccesoSubirDatos = "UsuarioAccesoSubirDatos";
    public static final String UsuarioAccesoRegistroProductores = "UsuarioAccesoRegistroProductores";
    public static final String UsuarioAccesoRegistroAcopio = "UsuarioAccesoRegistroAcopio";
    public static final String UsuarioEstado = "UsuarioEstado";
    public static final String UsuarioFechaCreacio = "UsuarioFechaCreacion";

    // Transacciones DDL(Data Definition Language)
    public static final String CreateTableUsuarios = "CREATE TABLE tblusuarios (UsuarioId INTEGER PRIMARY KEY AUTOINCREMENT," +
            "UsuarioNombre TEXT, UsuarioApellido TEXT, UsuarioTelefono TEXT, UsuarioCorreo TEXT, UsuarioContrasenia TEXT,"+
            "UsuarioNuevoRegistro INTEGER, UsuarioAccesoConfiguracion INTEGER, UsuarioAccesoBajarDatos INTEGER, UsuarioAccesoSubirDatos INTEGER," +
            "UsuarioAccesoRegistroProductores INTEGER, UsuarioAccesoRegistroAcopio INTEGER, UsuarioEstado INTEGER, UsuarioFechaCreacion DATETIME)";

    public static final String DropTableUsuario = "DROP TABLE IF EXISTS tblusuarios";

}
