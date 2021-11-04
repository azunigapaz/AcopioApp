package com.grupoadec.acopioapp.Configuracion;

public class Transacciones {
    // database Name
    public static final String NameDatabase = "AcopioAppDB";

    // tabla de usuarios
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
            "UsuarioNombre VARCHAR(60), UsuarioApellido VARCHAR(60), UsuarioTelefono VARCHAR(60), UsuarioCorreo VARCHAR(120), UsuarioContrasenia VARCHAR(120),"+
            "UsuarioNuevoRegistro INTEGER, UsuarioAccesoConfiguracion INTEGER, UsuarioAccesoBajarDatos INTEGER, UsuarioAccesoSubirDatos INTEGER," +
            "UsuarioAccesoRegistroProductores INTEGER, UsuarioAccesoRegistroAcopio INTEGER, UsuarioEstado INTEGER, UsuarioFechaCreacion DATETIME)";

    public static final String DropTableUsuario = "DROP TABLE IF EXISTS tblusuarios";

    // tabla de proveedores
    public static final String tablaproveedores = "tblproveedores";
    // campos de la tabla proveedores
    public static final String ProveedorClave = "ProveedorClave";
    public static final String ProveedorNombre = "ProveedorNombre";
    public static final String ProveedorRtn = "ProveedorRtn";
    public static final String ProveedorCalle = "ProveedorCalle";
    public static final String ProveedorCruzamiento = "ProveedorCruzamiento";
    public static final String ProveedorLocalidad = "ProveedorLocalidad";
    public static final String ProveedorMunicipio = "ProveedorMunicipio";
    public static final String ProveedorTelefono = "ProveedorTelefono";
    public static final String ProveedorSaldo = "ProveedorSaldo";

    // Transacciones DDL(Data Definition Language)
    public static final String CreateTableProveedores = "CREATE TABLE tblproveedores (ProveedorClave VARCHAR(10) PRIMARY KEY," +
            "ProveedorNombre VARCHAR(120), ProveedorRtn VARCHAR(15), ProveedorCalle VARCHAR(80), ProveedorCruzamiento VARCHAR(40)," +
            "ProveedorLocalidad VARCHAR(50), ProveedorMunicipio VARCHAR(50), ProveedorTelefono VARCHAR(25), ProveedorSaldo DECIMAL(18,6))";

    public static final String DropTableProveedores = "DROP TABLE IF EXISTS tblusuarios";

}
