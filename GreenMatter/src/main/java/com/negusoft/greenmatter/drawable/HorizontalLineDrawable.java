/*******************************************************************************
 * Copyright 2013 NEGU Soft
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
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.Interpolator;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.util.ColorUtils;

/**
 * A vertically centered line that fills the width.
 * The line width can be specified in DP or percentage of the height.
 */
public class HorizontalLineDrawable extends Drawable {

	private final Paint mPaint;
    private final HorizontalLineConstantState mState;

    /** Init with line width specified in DP. */
    public HorizontalLineDrawable(Resources res, int color, float lineWidthDp) {
        DisplayMetrics metrics = res.getDisplayMetrics();
        mState = new HorizontalLineConstantState(metrics, color, lineWidthDp, 0f);
        float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, metrics);
        mPaint = initPaint(color, lineWidth);
    }

    /** Init with line width specified as percentage of the drawable size. */
    public HorizontalLineDrawable(int color, float lineWidthRelative) {
        mState = new HorizontalLineConstantState(null, color, 0f, lineWidthRelative);
        mPaint = initPaint(color, 0f);
    }

    public HorizontalLineDrawable(DisplayMetrics metrics, int color, float lineWidthDp, float lineWidthRelative) {
        mState = new HorizontalLineConstantState(metrics, color, lineWidthDp, lineWidthRelative);
        float borderWidth = metrics == null ? 0f :
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, metrics);
        mPaint = initPaint(color, borderWidth);
    }

    private Paint initPaint(int color, float strokeWidth) {
        Paint result = new Paint();
        result.setColor(color);
        result.setStyle(Paint.Style.STROKE);
        result.setStrokeWidth(strokeWidth);
        return result;
    }
	
	@Override
	public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        float centerY = bounds.exactCenterY();
        float startX = bounds.left;
        float stopX = startX + bounds.width();

        if (mState.mLineWidthRelative > 0) {
            float lineWidth = canvas.getHeight() * mState.mLineWidthRelative;
            mPaint.setStrokeWidth(lineWidth);
        }

        canvas.drawLine(bounds.left, centerY, stopX, centerY, mPaint);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int alpha) {
        int borderAlpha = ColorUtils.calculateAlpha(alpha, mState.mColor);
        mPaint.setAlpha(borderAlpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// empty
	}

    @Override
    public ConstantState getConstantState() {
        return mState;
    }

    public static class HorizontalLineConstantState extends ConstantState {

        public final DisplayMetrics mDisplayMetrics;
        public final int mColor;

        public final float mLineWidthDp;

        // Line width, relative to the drawables height. If 0, the paints stroke width is used.
        public final float mLineWidthRelative;

        int changingConfigurationValue;

        public HorizontalLineConstantState(DisplayMetrics metrics, int color, float lineWidthDp, float lineWidthRelative) {
            mDisplayMetrics = metrics;
            mColor = color;
            mLineWidthDp = lineWidthDp;
            mLineWidthRelative = lineWidthRelative;
        }

        @Override
        public int getChangingConfigurations() {
            return changingConfigurationValue;
        }

        @Override
        public Drawable newDrawable() {
            return new HorizontalLineDrawable(mDisplayMetrics, mColor, mLineWidthDp, mLineWidthRelative);
        }
    }
}
