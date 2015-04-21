package com.negusoft.greenmatter.interceptor.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;

import com.negusoft.greenmatter.MatPalette;

/**
 * Holds a dictionary that contains DrawableInterceptors. It sets up the default interceptors.
 */
public class DrawableInterceptorHelper {

    private final SparseArray<DrawableInterceptor> mInterceptors;

    public DrawableInterceptorHelper(Context context) {
        mInterceptors = new SparseArray<>();

        SolidDrawableInterceptor.setupInterceptors(this, context);
        new TintDrawableDrawableInterceptor().setupInterceptors(this);
        new CircleDrawableInterceptor().setupInterceptors(this);
        new RoundRectDrawableInterceptor().setupInterceptors(this);
        new UnderlineDrawableInterceptor().setupInterceptors(this);
        new ProgressInterceptor().setupInterceptors(this);
        new ScrubberHorizontalInterceptor().setupInterceptors(this);
        new ScrollbarInterceptor().setupInterceptors(this);
        DialogBackgroundDrawableInterceptor.setupInterceptors(this);
        OverScrollDrawableIterceptor.setupInterceptors(this);
    }

    public void putInterceptor(int resId, DrawableInterceptor interceptor) {
        mInterceptors.put(resId, interceptor);
    }

    public Drawable getOverrideDrawable(Resources res, MatPalette palette, int resId) {
        DrawableInterceptor interceptor = mInterceptors.get(resId);
//        return interceptor != null ? interceptor.getDrawable(res, palette, resId) : null;
        if (interceptor != null) {
            return interceptor.getDrawable(res, palette, resId);
        } else {
            return null;
        }
    }
}
