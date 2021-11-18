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
    public static final String ProveedorCertificacion = "ProveedorCertificacion";

    // Transacciones DDL(Data Definition Language)
    public static final String CreateTableProveedores = "CREATE TABLE tblproveedores (ProveedorClave VARCHAR(10) PRIMARY KEY," +
            "ProveedorNombre VARCHAR(120), ProveedorRtn VARCHAR(15), ProveedorCalle VARCHAR(80), ProveedorCruzamiento VARCHAR(40)," +
            "ProveedorLocalidad VARCHAR(50), ProveedorMunicipio VARCHAR(50), ProveedorTelefono VARCHAR(25), ProveedorSaldo DECIMAL(18,6), ProveedorCertificacion VARCHAR(20) )";

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

    // tablas de compras y cuentas por pagar
    public static final String tablacomprasencabezado = "tblcomprasencabezado";
    // campos de la tabla encabezado de compras
    public static final String CompraEncabezadoDocumento = "CompraEncabezadoDocumento";
    public static final String CompraEncabezadoTipoDocumento = "CompraEncabezadoTipoDocumento";
    public static final String CompraEncabezadoProveedorClave = "CompraEncabezadoProveedorClave";
    public static final String CompraEncabezadoEstado = "CompraEncabezadoEstado";
    public static final String CompraEncabezadoFecha = "CompraEncabezadoFecha";
    public static final String CompraEncabezadoSubTotal = "CompraEncabezadoSubTotal";
    public static final String CompraEncabezadoImpuesto = "CompraEncabezadoImpuesto";
    public static final String CompraEncabezadoTotal = "CompraEncabezadoTotal";
    public static final String CompraEncabezadoAlmacen = "CompraEncabezadoAlmacen";
    public static final String CompraEncabezadoFechaHora = "CompraEncabezadoFechaHora";
    public static final String CompraEncabezadoSincronizado = "CompraEncabezadoSincronizado";
    // ddl encabezado de compras
    public static final String CreateTableComprasEncabezado = "CREATE TABLE tblcomprasencabezado (CompraEncabezadoDocumento VARCHAR(20) PRIMARY KEY," +
            "CompraEncabezadoTipoDocumento VARCHAR(1), CompraEncabezadoProveedorClave VARCHAR(10), CompraEncabezadoEstado VARCHAR(1), CompraEncabezadoFecha DATETIME," +
            "CompraEncabezadoSubTotal DECIMAL(18,6),CompraEncabezadoImpuesto DECIMAL(18,6), CompraEncabezadoTotal DECIMAL(18,6), CompraEncabezadoAlmacen INTEGER," +
            "CompraEncabezadoFechaHora DATETIME, CompraEncabezadoSincronizado INTEGER)";

    public static final String DropTableComprasEncabezado = "DROP TABLE IF EXISTS tblcomprasencabezado";

    public static final String tablacompraspartida = "tblcompraspartida";
    // campos de la tabla partida de compras
    public static final String CompraPartidaTipoDocumento = "CompraPartidaTipoDocumento";
    public static final String CompraPartidaDocumento = "CompraPartidaDocumento";
    public static final String CompraPartidaNumeroFila = "CompraPartidaNumeroFila";
    public static final String CompraPartidaProductoClave = "CompraPartidaProductoClave";
    public static final String CompraPartidaCantidad = "CompraPartidaCantidad";
    public static final String CompraPartidaCosto = "CompraPartidaCosto";
    public static final String CompraPartidaImpuesto = "CompraPartidaImpuesto";
    public static final String CompraPartidaTotal = "CompraPartidaTotal";
    public static final String CompraPartidaAlmacen = "CompraPartidaAlmacen";
    public static final String CompraPartidaSincronizado = "CompraPartidaSincronizado";
    // ddl partida de compras
    public static String CreateTableComprasPartida = "CREATE TABLE tblcompraspartida (CompraPartidaTipoDocumento VARCHAR(1),CompraPartidaDocumento VARCHAR(20)," +
            "CompraPartidaNumeroFila INTEGER, CompraPartidaProductoClave VARCHAR(16), CompraPartidaCantidad NUMERIC(18,6),CompraPartidaCosto NUMERIC(18,6)," +
            "CompraPartidaImpuesto NUMERIC(18,6), CompraPartidaTotal NUMERIC(18,6), CompraPartidaAlmacen INTEGER, CompraPartidaSincronizado INTEGER)";
    public static final String DropTableComprasPartida = "DROP TABLE IF EXISTS tblcompraspartida";

    public static final String tablacuentasporpagarconceptos = "tblcuentasporpagarconceptos";
    // campos de la tabla conceptos de cuentas por pagar
    public static final String CuentasPorPagarConceptoNumero = "CuentasPorPagarConceptoNumero";
    public static final String CuentasPorPagarConceptoDescripcion = "CuentasPorPagarConceptoDescripcion";
    public static final String CuentasPorPagarConceptoTipo = "CuentasPorPagarConceptoTipo";
    public static final String CuentasPorPagarConceptoConRefer = "CuentasPorPagarConceptoConRefer";
    public static final String CuentasPorPagarConceptoSigno = "CuentasPorPagarConceptoSigno";
    public static final String CuentasPorPagarConceptoEsFormaPago = "CuentasPorPagarConceptoEsFormaPago";
    // ddl conceptos de compras
    public static final String CreateTableCuentasPorPagarConceptos = "CREATE TABLE tblcuentasporpagarconceptos (CuentasPorPagarConceptoNumero INTEGER PRIMARY KEY," +
            "CuentasPorPagarConceptoDescripcion VARCHAR(20), CuentasPorPagarConceptoTipo VARCHAR(1), CuentasPorPagarConceptoConRefer VARCHAR(1), CuentasPorPagarConceptoSigno INTEGER," +
            "CuentasPorPagarConceptoEsFormaPago VARCHAR(1))";
    public static final String DropTableCuentasPorPagarConceptos = "DROP TABLE IF EXISTS tblcuentasporpagarconceptos";

    public static final String tablacuentasporpagarencabezado = "tblcuentasporpagarencabezado";
    // campos de la tabla cuentas por pagar encabezados
    public static final String CuentasPorPagarEncabezadoProveedorClave = "CuentasPorPagarEncabezadoProveedorClave";
    public static final String CuentasPorPagarEncabezadoConcepto = "CuentasPorPagarEncabezadoConcepto";
    public static final String CuentasPorPagarEncabezadoReferencia = "CuentasPorPagarEncabezadoReferencia";
    public static final String CuentasPorPagarEncabezadoFactura = "CuentasPorPagarEncabezadoFactura";
    public static final String CuentasPorPagarEncabezadoDocumento = "CuentasPorPagarEncabezadoDocumento";
    public static final String CuentasPorPagarEncabezadoImporte = "CuentasPorPagarEncabezadoImporte";
    public static final String CuentasPorPagarEncabezadoFechaAplicacion = "CuentasPorPagarEncabezadoFechaAplicacion";
    public static final String CuentasPorPagarEncabezadoFechaVencimiento = "CuentasPorPagarEncabezadoFechaVencimiento";
    public static final String CuentasPorPagarEncabezadoTipoMovimiento = "CuentasPorPagarEncabezadoTipoMovimiento";
    public static final String CuentasPorPagarEncabezadoSigno = "CuentasPorPagarEncabezadoSigno";
    public static final String CuentasPorPagarEncabezadoFechaHora = "CuentasPorPagarEncabezadoFechaHora";
    public static final String CuentasPorPagarEncabezadoSincronizado = "CuentasPorPagarEncabezadoSincronizado";
    // ddl encabezado de cuentas por pagar
    public static final String CreateTableCuentasPorPagarEncabezado = "CREATE TABLE tblcuentasporpagarencabezado (CuentasPorPagarEncabezadoProveedorClave VARCHAR(10)," +
            "CuentasPorPagarEncabezadoConcepto INTEGER, CuentasPorPagarEncabezadoReferencia VARCHAR(20) PRIMARY KEY, CuentasPorPagarEncabezadoFactura VARCHAR(20)," +
            "CuentasPorPagarEncabezadoDocumento VARCHAR(20), CuentasPorPagarEncabezadoImporte DECIMAL(18,6), CuentasPorPagarEncabezadoFechaAplicacion DATETIME," +
            "CuentasPorPagarEncabezadoFechaVencimiento DATETIME, CuentasPorPagarEncabezadoTipoMovimiento VARCHAR(1), CuentasPorPagarEncabezadoSigno INTEGER," +
            "CuentasPorPagarEncabezadoFechaHora DATETIME, CuentasPorPagarEncabezadoSincronizado INTEGER)";
    public final static String DropTableCuentasPorPagarEncabezado = "DROP TABLE IF EXISTS tblcuentasporpagarencabezado";

    public static final String tablacuentasporpagardetalle = "tblcuentasporpagardetalle";
    // campos de la tabla cuentas por pagar partidas
    public final static String CuentasPorPagarDetalleProveedorClave = "CuentasPorPagarDetalleProveedorClave";
    public final static String CuentasPorPagarDetalleConcepto = "CuentasPorPagarDetalleConcepto";
    public final static String CuentasPorPagarDetalleReferencia = "CuentasPorPagarDetalleReferencia";
    public final static String CuentasPorPagarDetalleFactura = "CuentasPorPagarDetalleFactura";
    public final static String CuentasPorPagarDetalleDocumento = "CuentasPorPagarDetalleDocumento";
    public final static String CuentasPorPagarDetalleImporte = "CuentasPorPagarDetalleImporte";
    public final static String CuentasPorPagarDetalleFechaAplicacion = "CuentasPorPagarDetalleFechaAplicacion";
    public final static String CuentasPorPagarDetalleTipoMovimiento = "CuentasPorPagarDetalleTipoMovimiento";
    public final static String CuentasPorPagarDetalleSigno = "CuentasPorPagarDetalleSigno";
    public final static String CuentasPorPagarDetalleFechaHora = "CuentasPorPagarDetalleFechaHora";
    public final static String CuentasPorPagarDetalleSincronizado = "CuentasPorPagarDetalleSincronizado";
    // ddl detalle cuentas por pagar
    public static final String CreateTableCuentasPorPagarDetalle = "CREATE TABLE tblcuentasporpagardetalle (CuentasPorPagarDetalleProveedorClave VARCHAR(10), CuentasPorPagarDetalleConcepto INTEGER," +
            "CuentasPorPagarDetalleReferencia VARCHAR(20), CuentasPorPagarDetalleFactura VARCHAR(20), CuentasPorPagarDetalleDocumento VARCHAR(20), CuentasPorPagarDetalleImporte DECIMAL(18,6)," +
            "CuentasPorPagarDetalleFechaAplicacion DATETIME, CuentasPorPagarDetalleTipoMovimiento VARCHAR(1),CuentasPorPagarDetalleSigno INTEGER, CuentasPorPagarDetalleFechaHora DATETIME, CuentasPorPagarDetalleSincronizado INTEGER)";
    public final static String DropTableCuentasPorPagarDetalle = "DROP TABLE IF EXISTS tblcuentasporpagardetalle";

}
