package com.negusoft.greenmatter.interceptor;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;
import com.negusoft.greenmatter.drawable.DoubleDrawableWrapper;
import com.negusoft.greenmatter.drawable.DrawableWrapper;
import com.negusoft.greenmatter.drawable.GreenLagerDrawable;
import com.negusoft.greenmatter.drawable.SimpleListDrawable;
import com.negusoft.greenmatter.drawable.TintDrawableWrapper;

/** Intercepts drawables that need to be wrapped in TintDrawableWrapper. */
public class TintDrawableInterceptor implements MatResources.Interceptor {

    @Override
    public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
        if (resId == R.drawable.gm__btn_check_reference) {
            Drawable primary = getTintendDrawable(res, palette, R.drawable.gm__btn_check_main);
            Drawable secondary = res.getDrawable(R.drawable.gm__circle_indicator);
            return new DoubleDrawableWrapper(primary, secondary);
        }
        if (resId == R.drawable.gm__btn_radio_reference)
            return  getTintendDrawable(res, palette, R.drawable.gm__btn_radio_main);
        return null;
    }

    private Drawable getTintendDrawable(Resources res, MatPalette palette, int id) {
        Drawable baseDrawable = res.getDrawable(id);
        return new TintDrawableWrapper(baseDrawable, palette.getControlColorStateList());
    }
}
