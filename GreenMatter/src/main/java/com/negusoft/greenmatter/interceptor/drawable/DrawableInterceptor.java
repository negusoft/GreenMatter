package com.negusoft.greenmatter.interceptor.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.greenmatter.MatPalette;

/**
 * Represents an object that can replace a drawable from the resources.
 */
public interface DrawableInterceptor {

    /** @return The drawable to be replaced. If null, res.getDrawable() will be performed. */
    public Drawable getDrawable(Resources res, MatPalette palette, int resId);

}
