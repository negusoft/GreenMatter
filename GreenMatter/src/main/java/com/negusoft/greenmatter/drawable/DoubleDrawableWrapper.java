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
 * Base wrapper that delegates all calls to another {@link android.graphics.drawable.Drawable}. The wrapped {@link android.graphics.drawable.Drawable}
 * <em>must</em> be fully released from any {@link android.view.View} before wrapping, otherwise internal {@link
 * android.graphics.drawable.Drawable.Callback} may be dropped.
 */
public class DoubleDrawableWrapper extends Drawable implements Drawable.Callback {

    private final Drawable mDrawablePrimary;
    private final Drawable mDrawableSecondary;

    public DoubleDrawableWrapper(Drawable primary, Drawable secondary) {
        mDrawablePrimary = primary;
        mDrawableSecondary = secondary;
        mDrawablePrimary.setCallback(this);
        mDrawableSecondary.setCallback(this);
    }

    @Override
    public void draw(Canvas canvas) {
        mDrawableSecondary.draw(canvas);
        mDrawablePrimary.draw(canvas);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mDrawableSecondary.setBounds(left, top, right, bottom);
        mDrawablePrimary.setBounds(left, top, right, bottom);
    }

    @Override
    public void setChangingConfigurations(int configs) {
        mDrawableSecondary.setChangingConfigurations(configs);
        mDrawablePrimary.setChangingConfigurations(configs);
    }

    @Override
    public int getChangingConfigurations() {
        return mDrawablePrimary.getChangingConfigurations();
    }

    @Override
    public void setDither(boolean dither) {
        mDrawableSecondary.setDither(dither);
        mDrawablePrimary.setDither(dither);
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        mDrawableSecondary.setFilterBitmap(filter);
        mDrawablePrimary.setFilterBitmap(filter);
    }

    @Override
    public void setAlpha(int alpha) {
        mDrawableSecondary.setAlpha(alpha);
        mDrawablePrimary.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mDrawableSecondary.setColorFilter(cf);
        mDrawablePrimary.setColorFilter(cf);
    }

    @Override
    public boolean isStateful() {
        return mDrawablePrimary.isStateful();
    }

    @Override
    public boolean setState(final int[] stateSet) {
        mDrawableSecondary.setState(stateSet);
        return mDrawablePrimary.setState(stateSet);
    }

    @Override
    public int[] getState() {
        return mDrawablePrimary.getState();
    }

    public void jumpToCurrentState() {
        DrawableCompat.jumpToCurrentState(mDrawableSecondary);
        DrawableCompat.jumpToCurrentState(mDrawablePrimary);
    }

    @Override
    public Drawable getCurrent() {
        return mDrawablePrimary.getCurrent();
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        mDrawableSecondary.setVisible(visible, restart);
        return super.setVisible(visible, restart) || mDrawablePrimary.setVisible(visible, restart);
    }

    @Override
    public int getOpacity() {
        return mDrawablePrimary.getOpacity();
    }

    @Override
    public Region getTransparentRegion() {
        return mDrawablePrimary.getTransparentRegion();
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawablePrimary.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawablePrimary.getIntrinsicHeight();
    }

    @Override
    public int getMinimumWidth() {
        return mDrawablePrimary.getMinimumWidth();
    }

    @Override
    public int getMinimumHeight() {
        return mDrawablePrimary.getMinimumHeight();
    }

    @Override
    public boolean getPadding(Rect padding) {
        return mDrawablePrimary.getPadding(padding);
    }

    /**
     * {@inheritDoc}
     */
    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    /**
     * {@inheritDoc}
     */
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        scheduleSelf(what, when);
    }

    /**
     * {@inheritDoc}
     */
    public void unscheduleDrawable(Drawable who, Runnable what) {
        unscheduleSelf(what);
    }

    @Override
    protected boolean onLevelChange(int level) {
        return mDrawablePrimary.setLevel(level);
    }

    @Override
    public void setAutoMirrored(boolean mirrored) {
        DrawableCompat.setAutoMirrored(mDrawableSecondary, mirrored);
        DrawableCompat.setAutoMirrored(mDrawablePrimary, mirrored);
    }

    @Override
    public boolean isAutoMirrored() {
        return DrawableCompat.isAutoMirrored(mDrawablePrimary);
    }

    @Override
    public void setTint(int tint) {
        DrawableCompat.setTint(mDrawableSecondary, tint);
        DrawableCompat.setTint(mDrawablePrimary, tint);
    }

    @Override
    public void setTintList(ColorStateList tint) {
        DrawableCompat.setTintList(mDrawableSecondary, tint);
        DrawableCompat.setTintList(mDrawablePrimary, tint);
    }

    @Override
    public void setTintMode(PorterDuff.Mode tintMode) {
        DrawableCompat.setTintMode(mDrawableSecondary, tintMode);
        DrawableCompat.setTintMode(mDrawablePrimary, tintMode);
    }

    @Override
    public void setHotspot(float x, float y) {
        DrawableCompat.setHotspot(mDrawableSecondary, x, y);
        DrawableCompat.setHotspot(mDrawablePrimary, x, y);
    }

    @Override
    public void setHotspotBounds(int left, int top, int right, int bottom) {
        DrawableCompat.setHotspotBounds(mDrawableSecondary, left, top, right, bottom);
        DrawableCompat.setHotspotBounds(mDrawablePrimary, left, top, right, bottom);
    }
}
