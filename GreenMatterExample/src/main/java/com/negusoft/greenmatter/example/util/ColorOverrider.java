package com.negusoft.greenmatter.example.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.activity.MatActivity;
import com.negusoft.greenmatter.example.R;

/**
 * Singleton to store override colors and apply them to the palette.
 */
public class ColorOverrider {

    private static final float DARK_RATIO = 0.85f;

    public static ColorOverrider sInstance;

    public static ColorOverrider getInstance(Context c) {
        if (sInstance == null) {
            Resources res = c.getResources();
            int accent = res.getColor(R.color.accent);
            int primaryDark = res.getColor(R.color.primary);
            int primaryLight = res.getColor(R.color.primary_light);
            sInstance = new ColorOverrider(accent, primaryDark, primaryLight);
        }
        return sInstance;
    }

    private boolean mEnabled = false;
    private int mColorPrimaryDark;
    private int mColorPrimaryLight;
    private int mColorAccent;
    private boolean mLightTheme = true;

    private ColorOverrider(int colorAccent, int colorPrimaryDark, int colorPrimaryLight) {
        mColorAccent = colorAccent;
        mColorPrimaryDark = colorPrimaryDark;
        mColorPrimaryLight = colorPrimaryLight;
    }

    public MatPalette applyOverride(MatPalette palette) {
        if (!mEnabled)
            return palette;

        if (mColorAccent != 0) {
            palette.setColorAccent(mColorAccent);
            palette.setColorControlActivated(mColorAccent);
        }

        int colorPrimary = getColorPrimary();
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

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        mEnabled = enabled;
    }

    public int getColorPrimary() {
        return mLightTheme ? mColorPrimaryLight : mColorPrimaryDark;
    }

    public void setColorPrimary(int colorPrimary) {
        if (mLightTheme) {
            mColorPrimaryLight = colorPrimary;
        } else {
            mColorPrimaryDark = colorPrimary;
        }
    }

    public void setColorPrimaryDark(int colorPrimary) {
        mColorPrimaryDark = colorPrimary;
    }

    public void setColorPrimaryLight(int colorPrimary) {
        mColorPrimaryLight = colorPrimary;
    }

    public int getColorAccent() {
        return mColorAccent;
    }

    public void setColorAccent(int colorAccent) {
        mColorAccent = colorAccent;
    }

    public boolean isLightTheme() {
        return mLightTheme;
    }

    public void setLightTheme(boolean lightTheme) {
        mLightTheme = lightTheme;
    }

    public void setAccentHue(float hue) {
        mColorAccent = ColorUtils.replaceHue(mColorAccent, hue);
    }

    public float getAccentHue() {
        return ColorUtils.getHue(mColorAccent);
    }

    public void setPrimaryHue(float hue) {
        mColorPrimaryDark = ColorUtils.replaceHue(mColorPrimaryDark, hue);
        mColorPrimaryLight = ColorUtils.replaceHue(mColorPrimaryLight, hue);
    }

    public float getPrimaryHue() {
        return ColorUtils.getHue(getColorPrimary());
    }
}
