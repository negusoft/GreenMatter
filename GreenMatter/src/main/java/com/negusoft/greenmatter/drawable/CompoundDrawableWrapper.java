/*******************************************************************************
 * Copyright 2014 NEGU Soft
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.negusoft.greenmatter.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Extends the Drawable wrapper to add a list of secondary drawables that will be drawn along with
 * the wrapped primary Drawable. It can be seen as a simplified version of the LayerDrawable.
 */
public class CompoundDrawableWrapper extends DrawableWrapper {

    private final boolean mSecondaryInBackground;
    private final Drawable[] mDrawables;

    public CompoundDrawableWrapper(Drawable primary, Drawable... secondary) {
        this(primary, true, secondary);
    }

    public CompoundDrawableWrapper(Drawable primary, boolean secondaryInBackgound, Drawable... secondary) {
        super(primary);
        mSecondaryInBackground = secondaryInBackgound;
        mDrawables = secondary;
        for (Drawable d : mDrawables)
            d.setCallback(this);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!mSecondaryInBackground)
            super.draw(canvas);
        for (Drawable d : mDrawables)
            d.draw(canvas);
        if (mSecondaryInBackground)
            super.draw(canvas);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        for (Drawable d : mDrawables)
            d.setBounds(left, top, right, bottom);
        super.setBounds(left, top, right, bottom);
    }

    @Override
    public void setChangingConfigurations(int configs) {
        for (Drawable d : mDrawables)
            d.setChangingConfigurations(configs);
        super.setChangingConfigurations(configs);
    }

    @Override
    public void setDither(boolean dither) {
        for (Drawable d : mDrawables)
            d.setDither(dither);
        super.setDither(dither);
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        for (Drawable d : mDrawables)
            d.setFilterBitmap(filter);
        super.setFilterBitmap(filter);
    }

    @Override
    public void setAlpha(int alpha) {
        for (Drawable d : mDrawables)
            d.setAlpha(alpha);
        super.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        for (Drawable d : mDrawables)
            d.setColorFilter(cf);
        super.setColorFilter(cf);
    }

    @Override
    public boolean setState(final int[] stateSet) {
        for (Drawable d : mDrawables)
            d.setState(stateSet);
        return super.setState(stateSet);
    }

    public void jumpToCurrentState() {
        for (Drawable d : mDrawables)
            DrawableCompat.jumpToCurrentState(d);
        super.jumpToCurrentState();
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        for (Drawable d : mDrawables)
            d.setVisible(visible, restart);
        return super.setVisible(visible, restart);
    }

    @Override
    protected boolean onLevelChange(int level) {
        for (Drawable d : mDrawables)
            d.setLevel(level);
        return super.setLevel(level);
    }

    @Override
    public void setAutoMirrored(boolean mirrored) {
        for (Drawable d : mDrawables)
            DrawableCompat.setAutoMirrored(d, mirrored);
        super.setAutoMirrored(mirrored);
    }

    @Override
    public void setTint(int tint) {
        for (Drawable d : mDrawables)
            DrawableCompat.setTint(d, tint);
        super.setTint(tint);
    }

    @Override
    public void setTintList(ColorStateList tint) {
        for (Drawable d : mDrawables)
            DrawableCompat.setTintList(d, tint);
        super.setTintList(tint);
    }

    @Override
    public void setTintMode(PorterDuff.Mode tintMode) {
        for (Drawable d : mDrawables)
            DrawableCompat.setTintMode(d, tintMode);
        super.setTintMode(tintMode);
    }

    @Override
    public void setHotspot(float x, float y) {
        for (Drawable d : mDrawables)
            DrawableCompat.setHotspot(d, x, y);
        super.setHotspot(x, y);
    }

    @Override
    public void setHotspotBounds(int left, int top, int right, int bottom) {
        for (Drawable d : mDrawables)
            DrawableCompat.setHotspotBounds(d, left, top, right, bottom);
        super.setHotspotBounds(left, top, right, bottom);
    }
}
