package com.example.camaleovendas.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Util {

    public static final String NAME_PIX = "Pix";
    public static final String NAME_CREDIT = "Crédito";
    public static final String NAME_DEBIT = "Débito";
    public static final String NAME_MONEY = "Dinheiro";

    public static Locale ptBr() {
        return new Locale("pt", "BR");
    }
    public static Bitmap setImageSizeColor(Bitmap bitmap, int width, int height) {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Bitmap b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Bitmap resize = Bitmap.createScaledBitmap(b, width, height, false);

            int A, R, G, B;
            int pixel;
            for (int x = 0; x < resize.getWidth(); ++x) {
                for (int y = 0; y < resize.getHeight(); ++y) {

                    pixel = resize.getPixel(x, y);
                    A = Color.alpha(pixel);
                    R = Color.red(pixel);
                    G = Color.green(pixel);
                    B = Color.blue(pixel);
                    int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);

                    if (gray > 128) {
                        gray = 255;
                    } else {
                        gray = 0;
                    }

                    resize.setPixel(x, y, Color.argb(A, gray, gray, gray));
                }
            }

            return resize;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap replaceColor(Bitmap src) {
        if (src == null)
            return null;
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, 1 * width, 0, 0, width, height);
        for (int x = 0; x < pixels.length; ++x) {
            //    pixels[x] = ~(pixels[x] << 8 & 0xFF000000) & Color.BLACK;
            if (pixels[x] == Color.WHITE) pixels[x] = 0;
        }
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
    }

    public static SimpleDateFormat setDateTimeFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    public static String getDateTimeNow() {
        String patternDate = "dd/MM/yyyy HH:mm:ss";
        try {
            Calendar calendar = Calendar.getInstance();
            return setDateTimeFormat(patternDate).format(calendar.getTime());
        } catch (Exception ignored) {
        }
        return "00/00/00000";
    }

}
