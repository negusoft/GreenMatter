package com.negusoft.greenmatter.interceptor.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;
import com.negusoft.greenmatter.drawable.CompoundDrawableWrapper;
import com.negusoft.greenmatter.drawable.TintDrawableWrapper;
import com.negusoft.greenmatter.util.ColorUtils;

/** Intercepts drawables that need to be wrapped in TintDrawableWrapper. */
public class TintDrawableDrawableInterceptor implements MatResources.DrawableInterceptor {

    @Override
    public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
        if (resId == R.drawable.gm__btn_check_reference) {
            Drawable primary = getTintendControlDrawable(res, palette, R.drawable.gm__btn_check_main);
            Drawable secondary = res.getDrawable(R.drawable.gm__circle_indicator);
            return new CompoundDrawableWrapper(primary, secondary);
        }
        if (resId == R.drawable.gm__btn_radio_reference) {
            Drawable primary = getTintendControlDrawable(res, palette, R.drawable.gm__btn_radio_main);
            Drawable secondary = res.getDrawable(R.drawable.gm__circle_indicator);
            return new CompoundDrawableWrapper(primary, secondary);
        }
        if (resId == R.drawable.gm__btn_default_reference) {
            Drawable frame = getTintendButtonDrawable(res, palette, R.drawable.gm__btn_default_frame);
            Drawable foreground = res.getDrawable(R.drawable.gm__btn_default_foreground);
            return new CompoundDrawableWrapper(frame, false, foreground);
        }
        return null;
    }

    private Drawable getTintendControlDrawable(Resources res, MatPalette palette, int id) {
        Drawable baseDrawable = res.getDrawable(id);
        return new TintDrawableWrapper(baseDrawable, getControlColorStateList(palette));
    }

    private Drawable getTintendButtonDrawable(Resources res, MatPalette palette, int id) {
        Drawable baseDrawable = res.getDrawable(id);
        return new TintDrawableWrapper(baseDrawable, getButtonColorStateList(palette));
    }

    /** Get a color state list for widgets */
    private ColorStateList getControlColorStateList(MatPalette palette) {
        int colorNormal = palette.getColorControlNormal();
        int colorActivated = palette.getColorControlActivated();
        float disabledAlpha = palette.getDisabledAlpha();

        final int[][] states = new int[8][];
        final int[] colors = new int[8];
        int i = 0;

        // Disabled states
        states[i] = new int[] { -android.R.attr.state_enabled, android.R.attr.state_checked };
        colors[i] = ColorUtils.applyColorAlpha(colorActivated, disabledAlpha);
        i++;

        states[i] = new int[] { -android.R.attr.state_enabled };
        colors[i] = ColorUtils.applyColorAlpha(colorNormal, disabledAlpha);
        i++;

        // Enabled states
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

    /** Get a color state list for the button shape */
    private ColorStateList getButtonColorStateList(MatPalette palette) {
        int color = palette.getColorButtonNormal();
        float disabledAlpha = palette.getDisabledAlpha();

        final int[][] states = new int[2][];
        final int[] colors = new int[2];

        // Disabled
        states[0] = new int[] { -android.R.attr.state_enabled };
        colors[0] = ColorUtils.applyColorAlpha(color, disabledAlpha);
        // Default (enabled)
        states[1] = new int[0];
        colors[1] = color;

        return new ColorStateList(states, colors);
    }

}
