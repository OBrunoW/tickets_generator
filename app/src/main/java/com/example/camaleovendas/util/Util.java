package com.example.camaleovendas.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.ByteArrayOutputStream;

public class Util {

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

}
