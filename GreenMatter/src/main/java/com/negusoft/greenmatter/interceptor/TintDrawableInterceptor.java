package com.negusoft.greenmatter.interceptor;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;
import com.negusoft.greenmatter.drawable.CompoundDrawableWrapper;
import com.negusoft.greenmatter.drawable.TintDrawableWrapper;

/** Intercepts drawables that need to be wrapped in TintDrawableWrapper. */
public class TintDrawableInterceptor implements MatResources.Interceptor {

    @Override
    public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
        if (resId == R.drawable.gm__btn_check_reference) {
            Drawable primary = getTintendDrawable(res, palette, R.drawable.gm__btn_check_main);
            Drawable secondary = res.getDrawable(R.drawable.gm__circle_indicator);
            return new CompoundDrawableWrapper(primary, secondary);
        }
        if (resId == R.drawable.gm__btn_radio_reference) {
            Drawable primary = getTintendDrawable(res, palette, R.drawable.gm__btn_radio_main);
            Drawable secondary = res.getDrawable(R.drawable.gm__circle_indicator);
            return new CompoundDrawableWrapper(primary, secondary);
        }
        return null;
    }

    private Drawable getTintendDrawable(Resources res, MatPalette palette, int id) {
        Drawable baseDrawable = res.getDrawable(id);
        return new TintDrawableWrapper(baseDrawable, getControlColorStateList(palette));
    }



    /** Get a color state list for widgets */
    private ColorStateList getControlColorStateList(MatPalette palette) {
        int colorNormal = palette.getColorControlNormal();
        int colorActivated = palette.getColorControlActivated();
        float disabledAlpha = palette.getDisabledAlpha();

        final int[][] states = new int[7][];
        final int[] colors = new int[7];
        int i = 0;

        // Disabled state
        states[i] = new int[] { -android.R.attr.state_enabled };
        colors[i] = applyColorAlpha(colorNormal, disabledAlpha);
        i++;

        states[i] = new int[] { android.R.attr.state_focused };
        colors[i] = colorActivated;
        i++;

        states[i] = new int[] { android.R.attr.state_activated };
        colors[i] = colorActivated;
        i++;

        states[i] = new int[] { android.R.attr.state_pressed };
        colors[i] = colorActivated;
        i++;

        states[i] = new int[] { android.R.attr.state_checked };
        colors[i] = colorActivated;
        i++;

        states[i] = new int[] { android.R.attr.state_selected };
        colors[i] = colorActivated;
        i++;

        // Default enabled state
        states[i] = new int[0];
        colors[i] = colorNormal;
        i++;

        return new ColorStateList(states, colors);
    }

    /** Modify the colors translucency by alpha [0..1] with respect to the original color alpha. */
    private int applyColorAlpha(int color, float alpha) {
        final int originalAlpha = Color.alpha(color);
        // Return the color, multiplying the original alpha by the disabled value
        return (color & 0x00ffffff) | (Math.round(originalAlpha * alpha) << 24);
    }

}
