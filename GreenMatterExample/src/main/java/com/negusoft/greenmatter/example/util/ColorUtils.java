package com.negusoft.greenmatter.example.util;

import android.graphics.Color;

public class ColorUtils {

    /** Get the hue component of the color [0..360]. */
    public static float getHue(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv[0];
    }

    /** Replace the hue in the given color */
    public static int replaceHue(int color, float hue) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[0] = hue;
        return Color.HSVToColor(hsv);
    }

}
