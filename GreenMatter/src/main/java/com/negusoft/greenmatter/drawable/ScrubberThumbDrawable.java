/*******************************************************************************
 * Copyright 2015 NEGU Soft
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

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.util.ColorUtils;

/**
 * Seekbar's round drawable. It is drawn in a different way depending 
 * on the SelectorType.
 */
public class ScrubberThumbDrawable extends Drawable {

    public enum SelectorType { NORMAL, DISABLED, PRESSED, FOCUSED }

    private static final float DRAWABLE_SIZE_DP = 32f;
    private static final float RADIUS_NORMAL_DP = 6f;
    private static final float RADIUS_PRESSED_DP = 9f;
    private static final float RADIUS_DISABLED_DP = 5f;
    private static final float BORDER_DISABLED_DP = 2f;

    private final ScrubberThumbConstantState mState;
    private final Paint mPaint;

    public ScrubberThumbDrawable(Resources res, MatPalette palette, SelectorType type) {
        int color = getColor(res, palette, type);
        mState = new ScrubberThumbConstantState(res.getDisplayMetrics(), color, type);
        mPaint = initPaint(color, type);
    }

    ScrubberThumbDrawable(DisplayMetrics metrics, int color, SelectorType type) {
        mState = new ScrubberThumbConstantState(metrics, color, type);
        mPaint = initPaint(color, type);
    }

    private int getColor(Resources res, MatPalette palette, SelectorType type) {
        if (type == SelectorType.DISABLED) {
            int alpha = (int)(palette.getDisabledAlpha() * 255);
            return palette.getColorControlNormal(alpha);
        }
        return palette.getColorControlActivated();
    }

    private float getRadius(SelectorType type) {
        float radiusDp = getRadiusDP(type);
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radiusDp, mState.mDisplayMetrics);
    }

    private float getRadiusDP(SelectorType type) {
        switch (type) {
            case PRESSED: return RADIUS_PRESSED_DP;
            case DISABLED: return RADIUS_DISABLED_DP;
            default: return RADIUS_NORMAL_DP;
        }
    }

    private Paint initPaint(int color, SelectorType type) {
        Paint result = new Paint();
        result.setColor(color);
        if (type == SelectorType.DISABLED) {
            result.setStyle(Paint.Style.STROKE);
            result.setStrokeWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BORDER_DISABLED_DP, mState.mDisplayMetrics));
        } else {
            result.setStyle(Paint.Style.FILL);
        }
        result.setAntiAlias(true);
        return result;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect r = getBounds();
        float centerX = r.exactCenterX();
        float centerY = r.exactCenterY();
        float radius = getRadius(mState.mType);

        canvas.drawCircle(centerX, centerY, radius, mPaint);
    }

    @Override
    public int getMinimumWidth() {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DRAWABLE_SIZE_DP, mState.mDisplayMetrics);
    }

    @Override
    public int getMinimumHeight() {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DRAWABLE_SIZE_DP, mState.mDisplayMetrics);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DRAWABLE_SIZE_DP, mState.mDisplayMetrics);
    }

    @Override
    public int getIntrinsicHeight() {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DRAWABLE_SIZE_DP, mState.mDisplayMetrics);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        alpha = ColorUtils.calculateAlpha(alpha, mState.mColor);
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        // empty
    }

    @Override
    public final ConstantState getConstantState() {
        mState.changingConfigurationValue = super.getChangingConfigurations();
        return mState;
    }

    public static class ScrubberThumbConstantState extends ConstantState {

        public final DisplayMetrics mDisplayMetrics;
        public final int mColor;
        public final SelectorType mType;

        int changingConfigurationValue;

        public ScrubberThumbConstantState(DisplayMetrics metrics, int color, SelectorType type) {
            mDisplayMetrics = metrics;
            mColor = color;
            mType = type;
        }

        @Override
        public int getChangingConfigurations() {
            return changingConfigurationValue;
        }

        @Override
        public Drawable newDrawable() {
            return new ScrubberThumbDrawable(mDisplayMetrics, mColor, mType);
        }

    }

}