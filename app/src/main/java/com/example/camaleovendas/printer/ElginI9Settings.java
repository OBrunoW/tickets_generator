package com.example.camaleovendas.printer;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;


public class ElginI9Settings {
    private final SettingPrinterE1 elgin;


    public  ElginI9Settings(Context mContext) {

        this.elgin = new SettingPrinterE1(mContext);
    }
    /**
     * cutPaperElgin() função para acionar a guilhotina das impressoras
     */
    public void cutPaper() {

        Map<String, Object> mapValues = new HashMap<>();

        mapValues.put("quant", 1);

        elgin.cutPaperElgin(mapValues);
    }

    /**
     * jumpLine() funcao responsavel por pular linhas no modo impressao de texto
     */
    public void jumpLine() {
        Map<String, Object> mapValues = new HashMap<>();

        mapValues.put("quant", 5);

        elgin.AvancaLinhasElgin(mapValues);
    }

    /**
     * printText() controla os parametros das impressoes de texto
     *
     * @param concat   recebe a string concatenada dos produtos
     * @param bold     adiciona negrito
     * @param sizeFont tamanho da fonte do texto
     * @param local    localizacao do texto
     */
    public void printText(String concat, boolean bold, int sizeFont, String local) {

        Map<String, Object> mapValues = new HashMap<>();
        mapValues.put("text", concat);
        mapValues.put("align", local);
        mapValues.put("font", "Font A");
        mapValues.put("fontSize", sizeFont);
        mapValues.put("isBold", bold);
        mapValues.put("isUnderline", false);

        elgin.printTextElgin(mapValues);
    }



}
