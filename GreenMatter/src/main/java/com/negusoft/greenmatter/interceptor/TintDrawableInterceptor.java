package com.negusoft.greenmatter.interceptor;

import android.content.res.Resources;
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
        return new TintDrawableWrapper(baseDrawable, palette.getControlColorStateList());
    }
}
