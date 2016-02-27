package com.example.sushil.androidmaterialtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Su syl on 7/7/2015.
 */
public class ImageColour {
    String colour;
    Context c;


    public ImageColour(Context context, Bitmap image) throws Exception {
        this.c = context;

        int height = image.getHeight();
        int width = image.getWidth();

        Map m = new HashMap();

        for (int i = 0; i < width; i++) {

            for (int j = 0; j < height; j++) {

                int argb = image.getPixel(i, j);
                int[] rgbArr = getRGBArr(argb);

                if (!isGray(rgbArr)) {

                    Integer counter = (Integer) m.get(argb);
                    if (counter == null)
                        counter = 0;
                    counter++;
                    m.put(argb, counter);

                }
            }
        }

        String colourHex = getMostCommonColour(m);
        this.colour = colourHex;
        //Toast.makeText(context,colourHex,Toast.LENGTH_LONG).show();


        m.clear();
    }


    public static String getMostCommonColour(Map map) {

        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {

                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());

            }

        });

        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
        int[] argb = getRGBArr((Integer) me.getKey());
        return Integer.toHexString(argb[0]) + Integer.toHexString(argb[1]) + Integer.toHexString(argb[2]);
    }


    public static int[] getRGBArr(int pixel) {

        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        return new int[]{red, green, blue, alpha};

    }

    public static boolean isGray(int[] rgbArr) {

        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];

        int tolerance = 10;

        if (rgDiff > tolerance || rgDiff < -tolerance)
            if (rbDiff > tolerance || rbDiff < -tolerance) {

                return false;

            }

        return true;
    }


    public String returnColour() {


        return colour;

    }
}
