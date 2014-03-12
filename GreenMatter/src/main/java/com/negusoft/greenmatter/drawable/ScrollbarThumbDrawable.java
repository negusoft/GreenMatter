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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.negusoft.greenmatter.util.ColorUtils;

/**
 * Simple rectangle with margins for the scrollbar thumb.
 */
public class ScrollbarThumbDrawable extends Drawable {

    private static final float MARGIN_DP = 3.25f;

    private final ScrollbarThumbConstantState mState;
    private final Paint mPaint;
    private final float mMargin;

    public ScrollbarThumbDrawable(DisplayMetrics metrics, int color) {
        mState = new ScrollbarThumbConstantState(metrics, color);
        mPaint = initFillPaint(color);
        mMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_DP, metrics);
    }

    private Paint initFillPaint(int color) {
        Paint result = new Paint();
        result.setColor(color);
        result.setStyle(Paint.Style.FILL_AND_STROKE);
        result.setAntiAlias(true);
        return result;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.drawRect(
                bounds.left + mMargin,
                bounds.top + mMargin,
                bounds.right - mMargin,
                bounds.bottom - mMargin,
                mPaint
        );
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        int resultAlpha = ColorUtils.calculateAlpha(alpha, mState.mColor);
        mPaint.setAlpha(resultAlpha);
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

    public static class ScrollbarThumbConstantState extends ConstantState {

        public final DisplayMetrics mDisplayMetrics;
        public final int mColor;

        int changingConfigurationValue;

        public ScrollbarThumbConstantState(DisplayMetrics metrics, int color) {
            mDisplayMetrics = metrics;
            mColor = color;
        }

        @Override
        public int getChangingConfigurations() {
            return changingConfigurationValue;
        }

        @Override
        public Drawable newDrawable() {
            return new ScrollbarThumbDrawable(mDisplayMetrics, mColor);
        }

        @Override
        public Drawable newDrawable(Resources res) {
            return new ScrollbarThumbDrawable(res.getDisplayMetrics(), mColor);
        }

    }

}
