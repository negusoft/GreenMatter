package com.negusoft.greenmatter.interceptor.color;

import android.content.res.Resources;

import com.negusoft.greenmatter.MatPalette;

/**
 * Represents an object that can replace a color from the resources.
 */
public interface ColorInterceptor {
    /** @return The color to be replaced or 0 to continue the normal flow. */
    public int getColor(Resources res, MatPalette palette, int resId);
}