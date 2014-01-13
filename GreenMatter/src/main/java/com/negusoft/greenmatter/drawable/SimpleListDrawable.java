package com.negusoft.greenmatter.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * Created by blurkidi on 13/01/15.
 */
public class SimpleListDrawable extends Drawable {

    private final Drawable[] mDrawables;

    public SimpleListDrawable(Drawable... drawables) {
        mDrawables = drawables;
    }

    @Override
    public void draw(Canvas canvas) {
        for (Drawable d : mDrawables)
            d.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        for (Drawable d : mDrawables)
            setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        for (Drawable d : mDrawables)
            d.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        boolean result = super.setVisible(visible, restart);
        for (Drawable d : mDrawables)
            d.setVisible(visible, restart);
        return result;
    }
}
