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
        // Check Box
        if (resId == R.drawable.gm__btn_check_reference) {
            Drawable primary = getTintedControlDrawable(res, palette, R.drawable.gm__btn_check_main);
            Drawable secondary = res.getDrawable(R.drawable.gm__circle_indicator);
            return new CompoundDrawableWrapper(primary, secondary);
        }

        // Radio Button
        if (resId == R.drawable.gm__btn_radio_reference) {
            Drawable primary = getTintedControlDrawable(res, palette, R.drawable.gm__btn_radio_main);
            Drawable secondary = res.getDrawable(R.drawable.gm__circle_indicator);
            return new CompoundDrawableWrapper(primary, secondary);
        }

        // Button
        if (resId == R.drawable.gm__btn_default_reference) {
            Drawable frame = getTintedButtonDrawable(res, palette, R.drawable.gm__btn_default_frame);
            Drawable foreground = res.getDrawable(R.drawable.gm__btn_default_foreground);
            return new CompoundDrawableWrapper(frame, false, foreground);
        }

        // Toggle Button
        if (resId == R.drawable.gm__btn_toggle_reference) {
            Drawable frame = getTintedButtonDrawable(res, palette, R.drawable.gm__btn_default_frame);
            Drawable indicator = res.getDrawable(R.drawable.gm__btn_toggle_indicator);
            Drawable foreground = res.getDrawable(R.drawable.gm__btn_default_foreground);
            return new CompoundDrawableWrapper(frame, false, indicator, foreground);
        }

        // Switch
        if (resId == R.drawable.gm__switch_track_reference)
            return getTintedSwitchTrackDrawable(res, palette);
        if (resId == R.drawable.gm__switch_thumb_reference)
            return getTintedSwitchThumbDrawable(res, palette);

        // Edit Text
        if (resId == R.drawable.gm__edit_text_activated_reference)
            return getTintedActivatedDrawable(res, palette, R.drawable.abc_textfield_activated_mtrl_alpha);
        if (resId == R.drawable.gm__edit_text_default_reference)
            return getTintedNormalDrawable(res, palette, R.drawable.abc_textfield_default_mtrl_alpha);
        if (resId == R.drawable.gm__edit_text_disabled_reference)
            return getTintedNormalDisabledDrawable(res, palette, R.drawable.abc_textfield_default_mtrl_alpha);

        // Text cursor
        if (resId == R.drawable.gm__text_cursor_reference)
            return getTintedActivatedDrawable(res, palette, R.drawable.gm__text_cursor_mtrl_alpha);

        // Text select handle
        if (resId == R.drawable.gm__text_select_handle_left_reference)
            return getTintedActivatedDrawable(res, palette, R.drawable.gm__text_select_handle_left_mtrl_alpha);
        if (resId == R.drawable.gm__text_select_handle_right_reference)
            return getTintedActivatedDrawable(res, palette, R.drawable.gm__text_select_handle_right_mtrl_alpha);
        if (resId == R.drawable.gm__text_select_handle_middle_reference)
            return getTintedActivatedDrawable(res, palette, R.drawable.gm__text_select_handle_middle_mtrl_alpha);

        // ActionMode background
        if (resId == R.drawable.gm__cab_background_top_reference)
            return getTintedActivatedDrawable(res, palette, R.drawable.abc_cab_background_top_mtrl_alpha);

        return null;
    }

    private Drawable getTintedControlDrawable(Resources res, MatPalette palette, int id) {
        Drawable baseDrawable = res.getDrawable(id);
        return new TintDrawableWrapper(baseDrawable, getControlColorStateList(palette));
    }

    private Drawable getTintedButtonDrawable(Resources res, MatPalette palette, int id) {
        Drawable baseDrawable = res.getDrawable(id);
        return new TintDrawableWrapper(baseDrawable, getButtonColorStateList(palette));
    }

    private Drawable getTintedSwitchTrackDrawable(Resources res, MatPalette palette) {
        Drawable baseDrawable = res.getDrawable(R.drawable.abc_switch_track_mtrl_alpha);
        return new TintDrawableWrapper(baseDrawable, getSwitchTrackColorStateList(res, palette));
    }

    private Drawable getTintedSwitchThumbDrawable(Resources res, MatPalette palette) {
        Drawable baseDrawable = res.getDrawable(R.drawable.gm__switch_thumb_stateful);
        return new TintDrawableWrapper(baseDrawable, getSwitchThumbColorStateList(palette));
    }

    private Drawable getTintedActivatedDrawable(Resources res, MatPalette palette, int id) {
        Drawable baseDrawable = res.getDrawable(id);
        int color = palette.getColorControlActivated();
        return new TintDrawableWrapper(baseDrawable, ColorStateList.valueOf(color));
    }
    private Drawable getTintedNormalDrawable(Resources res, MatPalette palette, int id) {
        Drawable baseDrawable = res.getDrawable(id);
        int color = palette.getColorControlNormal();
        return new TintDrawableWrapper(baseDrawable, ColorStateList.valueOf(color));
    }
    private Drawable getTintedNormalDisabledDrawable(Resources res, MatPalette palette, int id) {
        Drawable baseDrawable = res.getDrawable(id);
        int color = palette.getColorControlNormal();
        float disabledAlpha = palette.getDisabledAlpha();
        color = ColorUtils.applyColorAlpha(color, disabledAlpha);
        return new TintDrawableWrapper(baseDrawable, ColorStateList.valueOf(color));
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

    /**
     *  Get a color state list for the Switch track
     *  It is slightly different than the original. The colorControlNormal is used in stead of
     *  colorForeground. Also, the opacity is relative, so the disabled opacity is a third of the
     *  disabledAlpha, and not just an absolute value.
     */
    private ColorStateList getSwitchTrackColorStateList(Resources res, MatPalette palette) {
        int colorControlNormal = palette.getColorControlNormal();
        int colorControlActivated = palette.getColorControlActivated();
        float disabledAlpha = palette.getDisabledAlpha();

        // opacity to apply to each state color
        float relativeOpacity = 1/3f;
        disabledAlpha *= relativeOpacity;

        final int[][] states = new int[4][];
        final int[] colors = new int[4];

        // Disabled states
        states[0] = new int[] { -android.R.attr.state_enabled, android.R.attr.state_checked };
        colors[0] = ColorUtils.applyColorAlpha(colorControlActivated, disabledAlpha);
        states[1] = new int[] { -android.R.attr.state_enabled };
        colors[1] = ColorUtils.applyColorAlpha(colorControlNormal, disabledAlpha);
        // Enabled states
        states[2] = new int[] { android.R.attr.state_checked };
        colors[2] = ColorUtils.applyColorAlpha(colorControlActivated, relativeOpacity);
        states[3] = new int[0];
        colors[3] = ColorUtils.applyColorAlpha(colorControlNormal, relativeOpacity);

        return new ColorStateList(states, colors);
    }

    /** Get a color state list for the Switch thumb */
    private ColorStateList getSwitchThumbColorStateList(MatPalette palette) {
        int colorSwitchThumbThumbNormal = palette.getColorSwitchThumbNormal();
        int colorControlActivated = palette.getColorControlActivated();
        float disabledAlpha = palette.getDisabledAlpha();

        final int[][] states = new int[4][];
        final int[] colors = new int[4];

        // Disabled states
        states[0] = new int[] { -android.R.attr.state_enabled, android.R.attr.state_checked };
        colors[0] = ColorUtils.applyColorAlpha(colorControlActivated, disabledAlpha);
        states[1] = new int[] { -android.R.attr.state_enabled };
        colors[1] = ColorUtils.applyColorAlpha(colorSwitchThumbThumbNormal, disabledAlpha);
        // Enabled states
        states[2] = new int[] { android.R.attr.state_checked };
        colors[2] = colorControlActivated;
        states[3] = new int[0];
        colors[3] = colorSwitchThumbThumbNormal;

        return new ColorStateList(states, colors);
    }

    /** Get a color state list for the EditText when not active */
    private ColorStateList getEditTextDefaultColorStateList(MatPalette palette) {
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
