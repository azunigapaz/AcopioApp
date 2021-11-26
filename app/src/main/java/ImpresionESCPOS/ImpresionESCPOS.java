package ImpresionESCPOS;
import android.database.Cursor;
import android.util.Log;

import com.dantsu.escposprinter.EscPosCharsetEncoding;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;

import java.time.chrono.ThaiBuddhistEra;

//"[C]<font size='tall'>"+dataItems.getString(dataItems.getColumnIndex("CompraEncabezadoDocumento"))+"</font>\n";
public class ImpresionESCPOS {
    public void ImprimirDocumento(Cursor dataItems,String OriginalCopia,Integer Copias) throws EscPosConnectionException, EscPosParserException, EscPosEncodingException, EscPosBarcodeException, InterruptedException {
        //Log.i("Imprimir","Si llego aqui");
        float LF_TotalSubtotal=0,LF_TotalImpuesto=0,LF_TotalTotal=0;
        String ComandoImprimir="";

        EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 72F, 48);
        dataItems.moveToFirst();
        ComandoImprimir="[C]<font size='big'>APROSACAO</font>\n"+
                "[C]<font size='tall'>RECIBO DE COMPRA</font>\n"+
                "[C]<font size='tall'>Nº "+dataItems.getString(dataItems.getColumnIndex("CompraEncabezadoDocumento"))+"</font>\n"+
                //"[C]<font size='tall'>FAIR TRADE</font>\n"+
                //"[C]<font size='tall'>FIO ID:35276</font>\n"+
                "[L]\n"+
                "[L]<b>Fecha:"+dataItems.getString(dataItems.getColumnIndex("CompraEncabezadoFecha"))+"</b>\n"+
                "[L]<b>Productor:("+dataItems.getString(dataItems.getColumnIndex("CompraEncabezadoProveedorClave")).trim()+") - "+dataItems.getString(dataItems.getColumnIndex("ProveedorNombre")).trim()+"</b>\n"+
                "[L]<b>RTN Productor:"+dataItems.getString(dataItems.getColumnIndex("ProveedorRtn")).trim()+"</b>\n"+
                "[L]<b>Certificado:"+dataItems.getString(dataItems.getColumnIndex("ProveedorCertificacion")).trim()+"</b>\n"+
                "[L]<b>Direccion Productor:"+dataItems.getString(dataItems.getColumnIndex("ProveedorCalle")).trim()+"</b>\n"+
                "[L]<b>------------------------------------------------<b>\n"+
                "[C]<b>Descripcion<b>\n"+
                "[L]<b>Cantidad Lbs[C]Precio[R]Total Lps<b>\n"+
                "[L]<b>------------------------------------------------<b>\n";

        for(dataItems.moveToFirst(); !dataItems.isAfterLast(); dataItems.moveToNext())
        {

            ComandoImprimir+=""+
                    "[L]<font size='small'>"+dataItems.getString(dataItems.getColumnIndex("ProductoDescripcion"))+"</font>\n"+
                    "[L]<font size='medium'>"+String.valueOf(dataItems.getString(dataItems.getColumnIndex("CompraPartidaCantidad")))+
                    "[C]"+String.valueOf(dataItems.getString(dataItems.getColumnIndex("CompraPartidaCosto")))+
                    "[R]"+String.valueOf(dataItems.getString(dataItems.getColumnIndex("CompraPartidaTotal")))+"</font>\n";
            LF_TotalSubtotal=LF_TotalSubtotal+(Float.valueOf(dataItems.getString(dataItems.getColumnIndex("CompraPartidaCantidad")))*Float.valueOf(dataItems.getString(dataItems.getColumnIndex("CompraPartidaCosto"))));
            LF_TotalImpuesto=LF_TotalImpuesto+Float.valueOf(dataItems.getString(dataItems.getColumnIndex("CompraPartidaImpuesto")));
            LF_TotalTotal=LF_TotalTotal+Float.valueOf(dataItems.getString(dataItems.getColumnIndex("CompraPartidaTotal")));
        }
      ComandoImprimir+=""+
              "[L]<b>------------------------------------------------<b>\n"+
                "[L]<b>SUBTOTAL: [R]"+LF_TotalSubtotal+"</b>\n"+
                "[L]<b>IMPUESTO: [R]"+LF_TotalImpuesto+"</b>\n"+
                "[L]<b>TOTAL: [R]"+LF_TotalTotal+"</b>\n"+
                "[L]\n"+
                "[L]\n"+
              "[L]<b>______________________________</b>\n"+
                "[L]ENTREGADO POR\n"+
              "[L]\n"+
              "[L]\n"+
              "[L]<b>______________________________</b>\n"+
              "[L]\n"+
              "[L]ENCARGADO CENTRO DE ACOPIO POR\n"+
              "[L]\n"+
              "[L]\n"+
                "[C]<font size='tall'>PRODUCTO FISICAMENTE RASTREABLE</font>\n";


                for(int vueltas=1;vueltas<=Copias;vueltas++)
                {
                    String footer=(vueltas==1?"[C]<font size='tall'>ORIGINAL</font>\n":"[C]<font size='tall'>COPIA</font>\n");
                        printer.printFormattedTextAndCut(ComandoImprimir+footer,200);
                    Thread.sleep(3000);
                }

                printer.disconnectPrinter();
    }
}
