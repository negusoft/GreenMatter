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
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.negusoft.greenmatter.util.ColorUtils;

/**
 * A vertically centered line that fills the current progress.
 * The line width can be specified in DP or percentage of the height.
 */
public class ScrubberHorizontalDrawable extends Drawable {

    private static final int GAP_WIDTH_DP = 12;

    public enum Type { BACKGROUND, PROGRESS }

	private final Paint mPaint;
    private final ScrubberHorizontalConstantState mState;

    /** Init with line width specified in DP. */
    public ScrubberHorizontalDrawable(Resources res, int color, float lineWidthDp, Type type) {
        DisplayMetrics metrics = res.getDisplayMetrics();
        mState = new ScrubberHorizontalConstantState(metrics, color, lineWidthDp, type);
        float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, metrics);
        mPaint = initPaint(color, lineWidth);
    }

    public ScrubberHorizontalDrawable(DisplayMetrics metrics, int color, float lineWidthDp, Type type) {
        mState = new ScrubberHorizontalConstantState(metrics, color, lineWidthDp, type);
        float borderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, metrics);
        mPaint = initPaint(color, borderWidth);
    }

    private Paint initPaint(int color, float strokeWidth) {
        Paint result = new Paint();
        result.setColor(color);
        result.setStyle(Paint.Style.STROKE);
        result.setStrokeWidth(strokeWidth);
        result.setAntiAlias(true);
        return result;
    }
	
	@Override
	public void draw(Canvas canvas) {
        float level = getLevel() / 10000f;
        Rect bounds = getBounds();
        float centerY = bounds.exactCenterY();
        float startX = bounds.left;
        float progressX = startX + (bounds.width() * level);

        if (mState.mType == Type.PROGRESS) {
            canvas.drawLine(bounds.left, centerY, progressX, centerY, mPaint);
        } else {
            float stopX = startX + bounds.width();
            float gapWidthHalf = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GAP_WIDTH_DP, mState.mDisplayMetrics) / 2f;
            canvas.drawLine(bounds.left, centerY, progressX - gapWidthHalf, centerY, mPaint);
            canvas.drawLine(progressX + gapWidthHalf, centerY, stopX, centerY, mPaint);
        }
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

    public static class ScrubberHorizontalConstantState extends ConstantState {

        public final DisplayMetrics mDisplayMetrics;
        public final int mColor;
        public final Type mType;
        public final float mLineWidthDp;

        int changingConfigurationValue;

        public ScrubberHorizontalConstantState(DisplayMetrics metrics, int color, float lineWidthDp, Type type) {
            mDisplayMetrics = metrics;
            mColor = color;
            mLineWidthDp = lineWidthDp;
            mType = type;
        }

        @Override
        public int getChangingConfigurations() {
            return changingConfigurationValue;
        }

        @Override
        public Drawable newDrawable() {
            return new ScrubberHorizontalDrawable(mDisplayMetrics, mColor, mLineWidthDp, mType);
        }
    }
}
