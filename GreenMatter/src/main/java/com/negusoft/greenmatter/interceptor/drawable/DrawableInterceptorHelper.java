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

        SolidDrawableInterceptorProvider.setupInterceptors(this, context);
        TintDrawableDrawableInterceptorProvider.setupInterceptors(this);
        CircleDrawableInterceptorProvider.setupInterceptors(this);
        RoundRectDrawableInterceptorProvider.setupInterceptors(this);
        UnderlineDrawableInterceptorProvider.setupInterceptors(this);
        ProgressInterceptorProvider.setupInterceptors(this);
        ScrubberHorizontalInterceptorProvider.setupInterceptors(this);
        ScrollbarInterceptorProvider.setupInterceptors(this);
        DialogBackgroundDrawableInterceptorProvider.setupInterceptors(this);
        OverScrollDrawableIterceptorProvider.setupInterceptors(this);
    }

    public void putInterceptor(int resId, DrawableInterceptor interceptor) {
        mInterceptors.put(resId, interceptor);
    }

    public Drawable getOverrideDrawable(Resources res, MatPalette palette, int resId) {
        DrawableInterceptor interceptor = mInterceptors.get(resId);
        return interceptor != null ? interceptor.getDrawable(res, palette, resId) : null;
    }
}
