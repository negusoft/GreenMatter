package com.negusoft.greenmatter.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

/**
 * Created by blurkidi on 13/01/15.
 */
public class GreenLagerDrawable extends LayerDrawable {

    /**
     * Create a new layer drawable with the list of specified layers.
     *
     * @param layers A list of drawables to use as layers in this new drawable.
     */
    public GreenLagerDrawable(Drawable[] layers) {
        super(layers);
    }

    @Override
    protected boolean onStateChange(int[] state) {

        super.onStateChange(state);
        for (int i = 0; i < getNumberOfLayers(); i++) {
            getDrawable(i).setState(state);
        }

        return false;
    }
}
