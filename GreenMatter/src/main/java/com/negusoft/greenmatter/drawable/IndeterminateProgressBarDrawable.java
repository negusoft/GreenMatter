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
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.Interpolator;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.util.ColorUtils;

/**
 * Drawable to display the Material style indeterminate progress bar.
 * <br/><br/>
 * It sets the position of the lines based on the current millis so refreshing it constantly will
 * produce an animation.
 */
public class IndeterminateProgressBarDrawable extends Drawable {

    // Line width relative to the drawable height
    private static final float LINE_WIDTH_RATIO = 0.2f;

    private static final long ANIMATION_DURATION_MILLIS = 2000;

	private final Paint mLinePaint;
    private final Paint mBackgroundPaint;

	public IndeterminateProgressBarDrawable(Resources res, MatPalette palette) {
		DisplayMetrics metrics = res.getDisplayMetrics();
        mLinePaint = initLinePaint(palette.getColorControlActivated(), metrics);
        mBackgroundPaint = initBackgroundPaint(palette.getColorControlActivated(), metrics);
	}

    private Paint initLinePaint(int color, DisplayMetrics metrics) {
        Paint result = new Paint();
        result.setColor(color);
        result.setStyle(Paint.Style.STROKE);
        return result;
    }

    private Paint initBackgroundPaint(int color, DisplayMetrics metrics) {
        Paint result = new Paint();
        result.setColor(ColorUtils.applyColorAlpha(color, 0.1f));
        result.setStyle(Paint.Style.STROKE);
        return result;
    }
	
	@Override
	public void draw(Canvas canvas) {
		Rect r = getBounds();
		float centerY = (r.top + r.bottom) / 2f;

        long currentMillis = System.currentTimeMillis();
        float offsetMillis = (float)(currentMillis % ANIMATION_DURATION_MILLIS);
        float progress = offsetMillis / ANIMATION_DURATION_MILLIS;
        float canvasWidth = canvas.getWidth();

        float lineWidth = r.height() * LINE_WIDTH_RATIO;
        mBackgroundPaint.setStrokeWidth(lineWidth);
        mLinePaint.setStrokeWidth(lineWidth);

        drawBackground(canvas, canvasWidth, centerY);
        drawFirstLine(canvas, canvasWidth, progress, centerY);
        drawSecondLine(canvas, canvasWidth, progress, centerY);
	}

    private void drawBackground(Canvas canvas, float canvasWidth, float centerY) {
        canvas.drawLine(0, centerY, canvasWidth, centerY, mBackgroundPaint);
    }

    private void drawFirstLine(Canvas canvas, float canvasWidth, float progress, float centerY) {
        float startX = canvasWidth * mFirstStartInterpolator.getInterpolation(progress);
        float stopX = canvasWidth * mFirstStopInterpolator.getInterpolation(progress);
        canvas.drawLine(startX, centerY, stopX, centerY, mLinePaint);
    }

    private void drawSecondLine(Canvas canvas, float canvasWidth, float progress, float centerY) {
        float startX = canvasWidth * mSecondStartInterpolator.getInterpolation(progress);
        float stopX = canvasWidth * mSecondStopInterpolator.getInterpolation(progress);
        canvas.drawLine(startX, centerY, stopX, centerY, mLinePaint);
    }

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int alpha) {
        mLinePaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// empty
	}

    // Fist bars start : grows linearly and accelerates toward the middle
    Interpolator mFirstStartInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float input) {
            // linear speed
            float result = 1.2f * input;
            // start accelerating half way
            if (input  > 0.4) {
                float var = input - 0.4f;
                result += (4f * var * var);
            }
            // offset
            result -= 0.2f;
            return result;
        }
    };

    // Fist bars stop: accelerates constantly
    Interpolator mFirstStopInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float input) {
            //---------[acceleration]-----[linear speed]--[offset]
            return (2f * input * input) + (1.5f * input) - 0.1f;
        }
    };

    // Second bars start: decelerates constantly
    Interpolator mSecondStartInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float input) {
            if (input < 0.6f)
                return 0;
            input -= 0.6f;
            //---------[acceleration]-----[linear speed]-[offset]
            return -(4f * input * input) + (6f * input) - 0.7f;
        }
    };

    // Second bars stop:  decelerates constantly
    Interpolator mSecondStopInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float input) {
            if (input < 0.6f)
                return 0;
            input -= 0.6f;
            //---------[acceleration]------[linear speed]
            return -(6f * input * input) + (5.2f * input);
        }
    };
}
