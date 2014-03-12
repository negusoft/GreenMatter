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
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.negusoft.greenmatter.MatPalette;

/**
 * Drawable to display the Material style indeterminate progress bar.
 * <br/><br/>
 * It sets the position of the lines based on the current millis so refreshing it constantly will
 * produce an animation.
 */
public class ProgressBarCircleDrawable extends Drawable {

    // Sizes as a ratio of the smallest side of the drawable bounds.
    private static final float LINE_WIDTH = 4f / 48f;
    private static final float LINE_MARGIN = 5f / 48f;

    private static final long ROTATION_ANIMATION_DURATION_MILLIS = 6665;
    private static final long LINE_ANIMATION_DURATION_MILLIS = 1333;

	private final Paint mPaint;

	public ProgressBarCircleDrawable(Resources res, MatPalette palette) {
		DisplayMetrics metrics = res.getDisplayMetrics();
        mPaint = initPaint(palette.getColorControlActivated(), metrics);
	}

    private Paint initPaint(int color, DisplayMetrics metrics) {
        float lineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, metrics);
        Paint result = new Paint();
        result.setColor(color);
        result.setStyle(Paint.Style.STROKE);
        result.setStrokeWidth(lineWidth);
        result.setAntiAlias(true);
        return result;
    }
	
	@Override
	public void draw(Canvas canvas) {
		Rect r = getBounds();
        float centerX = (r.left + r.right) / 2f;
        float centerY = (r.top + r.bottom) / 2f;

        // All sizes are relative to the smallest side
        float sizeReference = Math.min(r.width(), r.height());

        long currentMillis = System.currentTimeMillis();
        canvas.save();
        rotateCanvas(canvas, centerX, centerY, currentMillis);
        drawLine(canvas, sizeReference, centerX, centerY, currentMillis);
        canvas.restore();
	}

    private void rotateCanvas(Canvas canvas, float centerX, float centerY, long currentMillis) {
        float offsetMillis = (float)(currentMillis % ROTATION_ANIMATION_DURATION_MILLIS);
        float progress = offsetMillis / ROTATION_ANIMATION_DURATION_MILLIS;
        float rotation = 720f * progress;

        canvas.rotate(rotation, centerX, centerY);
    }

    private void drawLine(Canvas canvas, float reference, float centerX, float centerY, long currentMillis) {
        float radius = reference / 2f;
        radius -= reference * LINE_MARGIN;
        mPaint.setStrokeWidth(reference * LINE_WIDTH);

        float offsetMillis = (float)(currentMillis % LINE_ANIMATION_DURATION_MILLIS);
        float progress = offsetMillis / LINE_ANIMATION_DURATION_MILLIS;
        float trimOffset = 0.25f * mTrimOffsetInterpolator.getInterpolation(progress);
        float trimStart = 0.75f * mTrimStartInterpolator.getInterpolation(progress);
        trimStart -= 0.05f;
        float trimEnd = 0.75f * mTrimEndInterpolator.getInterpolation(progress);
        float startAngle = 360f * ((trimStart + trimOffset) % 1.0f);
        float endAngle = 360f * ((trimEnd + trimOffset) % 1.0f);

        RectF rect = new RectF(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius
        );
        canvas.drawArc(rect, startAngle, endAngle - startAngle, false, mPaint);
    }

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// empty
	}

    // The fraction of the line to trim from the start.
    // Accelerate decelerate during the second half.
    Interpolator mTrimStartInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float input) {
            // Still
            if (input < 0.5f)
                return 0f;
            // Accelerate
            if (input < 0.75) {
                input -= 0.5;
                return input * input * 8;
            }
            // Decelerate
            input = 1f - input;
            return 1f - (input * input * 8);
        }
    };

    // The fraction of the line to trim from the end
    // Accelerate decelerate during the first half.
    Interpolator mTrimEndInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float input) {
            // Still
            if (input > 0.5f)
                return 1f;
            // Accelerate
            if (input < 0.25f)
                return input * input * 8;
            // Decelerate
            input = 0.5f - input;
            return 1f - (input * input * 8);
        }
    };

    // Shift trim region: linear
    Interpolator mTrimOffsetInterpolator = new LinearInterpolator();
}
