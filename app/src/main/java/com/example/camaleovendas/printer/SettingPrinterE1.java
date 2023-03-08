package com.example.camaleovendas.printer;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.elgin.e1.Impressora.Etiqueta;
import com.elgin.e1.Impressora.Termica;

import java.util.Map;
import java.util.Objects;

public class SettingPrinterE1 {

    public static final String MODEL_PRINTER_I9 = "i9";
    public static final int USB = 1;
    public static final int IP = 3;
    public static final String TYPE_IP = "";
    public static final String TYPE_USB = "usb";


    public Context _context;
    public SettingPrinterE1(Context context) {
        this._context = context;
        Termica.setContext(context);
    }
    public int connectPrinter() {
        try {
            return Termica.AbreConexaoImpressora(USB, MODEL_PRINTER_I9, TYPE_USB, 0);
        } catch (Exception e) {
            return -1;
        }
    }
    public void printerStopElgin() {
        Termica.FechaConexaoImpressora();
    }
    public int AvancaLinhasElgin(Map map) {
        int lines = (Integer) map.get("quant");
        return Termica.AvancaPapel(lines);
    }
    public int cutPaperElgin(Map map) {
        int lines = (Integer) map.get("quant");
        return Termica.Corte(lines);
    }
    public int printTextElgin(Map map) {
        String text = (String) map.get("text");
        String align = (String) map.get("align");
        String font = (String) map.get("font");
        int fontSize = (Integer) map.get("fontSize");

        int result = 0;

        int alignValue = 0;
        int styleValue = 0;

        // ALINHAMENTO VALUE
        if (align.equals("Esquerda")) {
            alignValue = 0;
        } else if (align.equals("Centralizado")) {
            alignValue = 1;
        } else {
            alignValue = 2;
        }
        //STILO VALUE
        if (font.equals("FONT B")) {
            styleValue += 1;
        }
        if ((boolean) map.get("isUnderline")) {
            styleValue += 2;
        }
        if ((boolean) map.get("isBold")) {
            styleValue += 8;
        }

        result = Termica.ImpressaoTexto(text, alignValue, styleValue, fontSize);
        return result;
    }
    private int codeOfBarCode(String barCodeName) {
        if (barCodeName.equals("UPC-A"))
            return 0;
        else if (barCodeName.equals("UPC-E"))
            return 1;
        else if (barCodeName.equals("EAN 13") || barCodeName.equals("JAN 13"))
            return 2;
        else if (barCodeName.equals("EAN 8") || barCodeName.equals("JAN 8"))
            return 3;
        else if (barCodeName.equals("CODE 39"))
            return 4;
        else if (barCodeName.equals("ITF"))
            return 5;
        else if (barCodeName.equals("CODE BAR"))
            return 6;
        else if (barCodeName.equals("CODE 93"))
            return 7;
        else if (barCodeName.equals("CODE 128"))
            return 8;
        else return 0;
    }
    public int printBarCodeElgin(Map map) {
        int barCodeType = codeOfBarCode((String) map.get("barCodeType"));
        String text = (String) map.get("text");
        int height = (Integer) map.get("height");
        int width = (Integer) map.get("width");
        String align = (String) map.get("align");

        int hri = 4; // NO PRINT
        int result;
        int alignValue;

        // ALINHAMENTO VALUE
        if (align.equals("Esquerda")) {
            alignValue = 0;
        } else if (align.equals("Centralizado")) {
            alignValue = 1;
        } else {
            alignValue = 2;
        }

        Termica.DefinePosicao(alignValue);

        result = Termica.ImpressaoCodigoBarras(barCodeType, text, height, width, hri);

        return result;
    }
    public int printQrCodeElgin(Map map) {
        int size = (Integer) map.get("qrSize");
        String text = (String) map.get("text");
        String align = (String) map.get("align");
        int nivelCorrecao = 2;
        int result;
        int alignValue;

        // ALINHAMENTO VALUE
        if (align.equals("Esquerda")) {
            alignValue = 0;
        } else if (align.equals("Centralizado")) {
            alignValue = 1;
        } else {
            alignValue = 2;
        }

        Termica.DefinePosicao(alignValue);

        result = Termica.ImpressaoQRCode(text, size, nivelCorrecao);
        return result;
    }
    public int printImageElgin(Bitmap bitmap) {
        int result;

        //Verifica se o método de impressão atual é por impressora interna
        // ou externa e utiliza a função adequada para cada um result retorna 0 caso sucesso
        result = Termica.ImprimeImagem(bitmap);

        return result;
    }
    public int statusSensorElgin() {
        return Termica.StatusImpressora(3);
    }

}
