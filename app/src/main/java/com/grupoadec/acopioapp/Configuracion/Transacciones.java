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

    public static final String DropTableProveedores = "DROP TABLE IF EXISTS tblproveedores";

    // tabla productos
    public static final String tablaproductos = "tblproductos";
    // campos de la tabla productos
    public static final String ProductoClave = "ProductoClave";
    public static final String ProductoDescripcion = "ProductoDescripcion";
    public static final String ProductoCosto = "ProductoCosto";
    public static final String ProductoLinea = "ProductoLinea";

    // Transacciones DDL(Data Definition Language)
    public static final String CreateTableProductos = "CREATE TABLE tblproductos (ProductoClave VARCHAR(16) PRIMARY KEY," +
            "ProductoDescripcion VARCHAR(40), ProductoCosto DECIMAL(18,6), ProductoLinea VARCHAR(5))";

    public static final String DropTableProductos = "DROP TABLE IF EXISTS tblproductos";

    // tabla almacenes
    public static final String tablaalmacenes = "tblalmacenes";
    // campos de la tabla almacenes
    public static final String AlmacenClave = "AlmacenClave";
    public static final String AlmacenDescripcion = "AlmacenDescripcion";

    // Transacciones DDL(Data Definition Language)
    public static final String CreateTableAlmacenes = "CREATE TABLE tblalmacenes (AlmacenClave INT PRIMARY KEY, AlmacenDescripcion VARCHAR(40))";

    public static final String DropTableAlmacenes = "DROP TABLE IF EXISTS tblalmacenes";

    // tabla configuracion
    public static final String tablaconfiguraciones = "tblconfiguraciones";
    // campos de la tabla configuraciones
    public static final String ConfiguracionId = "ConfiguracionId";
    public static final String ConfiguracionSufijoDocumento = "ConfiguracionSufijoDocumento";
    public static final String ConfiguracionUltimoDocumento = "ConfiguracionUltimoDocumento";
    public static final String ConfiguracionUrl = "ConfiguracionUrl";
    public static final String ConfiguracionTipoImpresora = "ConfiguracionTipoImpresora";

    // DDL
    public static final String CreateTableConfiguraciones = "CREATE TABLE tblconfiguraciones (ConfiguracionId VARCHAR(120) PRIMARY KEY, ConfiguracionSufijoDocumento VARCHAR(60),"+
            "ConfiguracionUltimoDocumento INT, ConfiguracionUrl VARCHAR(200), ConfiguracionTipoImpresora VARCHAR(60))";

    public static final String DropTableConfiguraciones = "DROP TABLE IF EXISTS tblconfiguraciones";

    // tabla acopio partida temporal
    public static final String tablaacopiopartidatmp = "tblacopiopartidatmp";
    // campos de la tabla acopio partida temporal
    public static final String AcopioPartidaNo = "AcopioPartidaNo";
    public static final String AcopioPartidaProductoClave = "AcopioPartidaProductoClave";
    public static final String AcopioPartidaProductoDescripcion = "AcopioPartidaProductoDescripcion";
    public static final String AcopioPartidaProductoCantidad = "AcopioPartidaProductoCantidad";
    public static final String AcopioPartidaProductoPrecio = "AcopioPartidaProductoPrecio";
    public static final String AcopioPartidaProductoSubTotal = "AcopioPartidaProductoSubTotal";

    // DDL
    public static final String CreateTableAcopioPartidaTmp = "CREATE TABLE tblacopiopartidatmp (AcopioPartidaNo INT," +
            "AcopioPartidaProductoClave VARCHAR(16), AcopioPartidaProductoDescripcion VARCHAR(40), AcopioPartidaProductoCantidad DECIMAL(18,6),"+
            "AcopioPartidaProductoPrecio DECIMAL(18,6), AcopioPartidaProductoSubTotal DECIMAL(18,6))";

    public static final String DropTableAcopioPartidaTmp = "DROP TABLE IF EXISTS tblacopiopartidatmp";

}
