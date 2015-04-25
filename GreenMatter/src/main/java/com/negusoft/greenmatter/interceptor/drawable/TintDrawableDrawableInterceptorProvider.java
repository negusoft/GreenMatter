package com.negusoft.greenmatter.interceptor.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.R;
import com.negusoft.greenmatter.drawable.CompoundDrawableWrapper;
import com.negusoft.greenmatter.drawable.TintDrawableWrapper;
import com.negusoft.greenmatter.util.ColorUtils;

/** Intercepts drawables that need to be wrapped in TintDrawableWrapper. */
public class TintDrawableDrawableInterceptorProvider {

    public static void setupInterceptors(DrawableInterceptorHelper helper) {
        // Check Box
        putCheckBoxInterceptor(helper);

        // Radio Button
        putRadioButtonInterceptor(helper);

        // Button
        putButtonInterceptor(helper);

        // Toggle Button
        putToggleButtonInterceptor(helper);

        // Switch
        putSwitchTrackInterceptor(helper);
        putSwitchThumbInterceptor(helper);

        // Edit Text
        putActivatedInterceptor(helper, R.drawable.gm__edit_text_activated_reference, R.drawable.abc_textfield_activated_mtrl_alpha);
        putNormalInterceptor(helper, R.drawable.gm__edit_text_default_reference, R.drawable.abc_textfield_default_mtrl_alpha);
        putNormalDisabledInterceptor(helper, R.drawable.gm__edit_text_disabled_reference, R.drawable.abc_textfield_default_mtrl_alpha);

        // SearchView
        putActivatedInterceptor(helper, R.drawable.gm__textfield_search_activated_reference, R.drawable.abc_textfield_search_activated_mtrl_alpha);
        putNormalInterceptor(helper, R.drawable.gm__textfield_search_default_reference, R.drawable.abc_textfield_search_default_mtrl_alpha);

        // Text cursor
        putActivatedInterceptor(helper, R.drawable.gm__text_cursor_reference, R.drawable.gm__text_cursor_mtrl_alpha);

        // Text select handle
        putActivatedInterceptor(helper, R.drawable.gm__text_select_handle_left_reference, R.drawable.gm__text_select_handle_left_mtrl_alpha);
        putActivatedInterceptor(helper, R.drawable.gm__text_select_handle_right_reference, R.drawable.gm__text_select_handle_right_mtrl_alpha);
        putActivatedInterceptor(helper, R.drawable.gm__text_select_handle_middle_reference, R.drawable.gm__text_select_handle_middle_mtrl_alpha);

        // TabBar selected indicator
        putActivatedInterceptor(helper, R.drawable.gm__tab_indicator_reference, R.drawable.gm__tab_indicator_main);
    }

    private static void putActivatedInterceptor(DrawableInterceptorHelper helper, int interceptedResId, int tintedResId) {
        DrawableInterceptor interceptor = new ColorStateDrawableInterceptor(tintedResId) {
            @Override
            public ColorStateList getTintColor(Resources res, MatPalette palette) {
                return ColorStateList.valueOf(palette.getColorControlActivated());
            }
        };
        helper.putInterceptor(interceptedResId, interceptor);
    }

    private static void putNormalInterceptor(DrawableInterceptorHelper helper, int interceptedResId, int tintedResId) {
        DrawableInterceptor interceptor = new ColorStateDrawableInterceptor(tintedResId) {
            @Override
            public ColorStateList getTintColor(Resources res, MatPalette palette) {
                return ColorStateList.valueOf(palette.getColorControlNormal());
            }
        };
        helper.putInterceptor(interceptedResId, interceptor);
    }

    private static void putNormalDisabledInterceptor(DrawableInterceptorHelper helper, int interceptedResId, int tintedResId) {
        DrawableInterceptor interceptor = new ColorStateDrawableInterceptor(tintedResId) {
            @Override
            public ColorStateList getTintColor(Resources res, MatPalette palette) {
                int color = palette.getColorControlNormal();
                float disabledAlpha = palette.getDisabledAlpha();
                color = ColorUtils.applyColorAlpha(color, disabledAlpha);
                return ColorStateList.valueOf(color);
            }
        };
        helper.putInterceptor(interceptedResId, interceptor);
    }

    private static void putSwitchTrackInterceptor(DrawableInterceptorHelper helper) {
        DrawableInterceptor interceptor = new ColorStateDrawableInterceptor(R.drawable.abc_switch_track_mtrl_alpha) {
            @Override
            public ColorStateList getTintColor(Resources res, MatPalette palette) {
                return getSwitchTrackColorStateList(res, palette);
            }
        };
        helper.putInterceptor(R.drawable.gm__switch_track_reference, interceptor);
    }

    private static void putSwitchThumbInterceptor(DrawableInterceptorHelper helper) {
        DrawableInterceptor interceptor = new ColorStateDrawableInterceptor(R.drawable.gm__switch_thumb_stateful) {
            @Override
            public ColorStateList getTintColor(Resources res, MatPalette palette) {
                return getSwitchThumbColorStateList(palette);
            }
        };
        helper.putInterceptor(R.drawable.gm__switch_thumb_reference, interceptor);
    }

    private static void putCheckBoxInterceptor(DrawableInterceptorHelper helper) {
        DrawableInterceptor interceptor = new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                Drawable primary = getTintedControlDrawable(res, palette, R.drawable.gm__btn_check_main);
                Drawable secondary = res.getDrawable(R.drawable.gm__circle_indicator);
                return new CompoundDrawableWrapper(primary, secondary);
            }
        };
        helper.putInterceptor(R.drawable.gm__btn_check_reference, interceptor);
    }

    private static void putRadioButtonInterceptor(DrawableInterceptorHelper helper) {
        DrawableInterceptor interceptor = new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                Drawable primary = getTintedControlDrawable(res, palette, R.drawable.gm__btn_radio_main);
                Drawable secondary = res.getDrawable(R.drawable.gm__circle_indicator);
                return new CompoundDrawableWrapper(primary, secondary);
            }
        };
        helper.putInterceptor(R.drawable.gm__btn_radio_reference, interceptor);
    }

    private static void putButtonInterceptor(DrawableInterceptorHelper helper) {
        DrawableInterceptor interceptor = new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                Drawable frame = getTintedButtonDrawable(res, palette, R.drawable.gm__btn_default_frame);
                Drawable foreground = res.getDrawable(R.drawable.gm__btn_default_foreground);
                return new CompoundDrawableWrapper(frame, false, foreground);
            }
        };
        helper.putInterceptor(R.drawable.gm__btn_default_reference, interceptor);
    }

    private static void putToggleButtonInterceptor(DrawableInterceptorHelper helper) {
        DrawableInterceptor interceptor = new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                Drawable frame = getTintedButtonDrawable(res, palette, R.drawable.gm__btn_default_frame);
                Drawable indicator = res.getDrawable(R.drawable.gm__btn_toggle_indicator);
                Drawable foreground = res.getDrawable(R.drawable.gm__btn_default_foreground);
                return new CompoundDrawableWrapper(frame, false, indicator, foreground);
            }
        };
        helper.putInterceptor(R.drawable.gm__btn_toggle_reference, interceptor);
    }

    private static Drawable getTintedControlDrawable(Resources res, MatPalette palette, int id) {
        Drawable baseDrawable = res.getDrawable(id);
        return new TintDrawableWrapper(baseDrawable, getControlColorStateList(palette));
    }

    private static Drawable getTintedButtonDrawable(Resources res, MatPalette palette, int id) {
        Drawable baseDrawable = res.getDrawable(id);
        return new TintDrawableWrapper(baseDrawable, getButtonColorStateList(palette));
    }

    /** Get a color state list for widgets */
    private static ColorStateList getControlColorStateList(MatPalette palette) {
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
    private static ColorStateList getButtonColorStateList(MatPalette palette) {
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
    private static ColorStateList getSwitchTrackColorStateList(Resources res, MatPalette palette) {
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
    private static ColorStateList getSwitchThumbColorStateList(MatPalette palette) {
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

    private static abstract class ColorStateDrawableInterceptor implements DrawableInterceptor {

        private final int mResId;

        ColorStateDrawableInterceptor(int resId) {
            mResId = resId;
        }

        @Override
        public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
            Drawable baseDrawable = res.getDrawable(mResId);
            ColorStateList color = getTintColor(res, palette);
            return new TintDrawableWrapper(baseDrawable, color);
        }

        public abstract ColorStateList getTintColor(Resources res, MatPalette palette);
    }

}
