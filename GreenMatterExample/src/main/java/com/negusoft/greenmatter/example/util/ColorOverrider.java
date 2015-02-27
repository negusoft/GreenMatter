package com.negusoft.greenmatter.example.util;

import android.graphics.Color;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.activity.MatActivity;

/**
 * Singleton to store override colors and apply them to the palette.
 */
public class ColorOverrider {

    private static final float DARK_RATIO = 0.85f;

    public static ColorOverrider sInstance;

    public static ColorOverrider getInstance(MatPalette palette) {
        if (sInstance == null) {
            sInstance = new ColorOverrider(palette);
        }
        return sInstance;
    }

    public boolean enabled = false;
    public int colorPrimary;
    public int colorAccent;

    private ColorOverrider(MatPalette palette) {
        colorPrimary = palette.getColorPrimary();
        colorAccent = palette.getColorAccent();
    }

    public MatPalette applyOverride(MatPalette palette) {
        if (!enabled)
            return palette;

        if (colorAccent != 0) {
            palette.setColorAccent(colorAccent);
            palette.setColorControlActivated(colorAccent);
        }
        if (colorPrimary != 0) {
            palette.setColorPrimary(colorPrimary);
            int darkRed = (int)(Color.red(colorPrimary) * DARK_RATIO);
            int darkGreen = (int)(Color.green(colorPrimary) * DARK_RATIO);
            int darkBlue = (int)(Color.blue(colorPrimary) * DARK_RATIO);
            int darkPrimary = Color.argb(255, darkRed, darkGreen, darkBlue);
            palette.setColorPrimaryDark(darkPrimary);
        }

        return palette;
    }
}
