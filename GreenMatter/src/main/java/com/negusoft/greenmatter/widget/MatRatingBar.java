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
package com.negusoft.greenmatter.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RatingBar;

import com.negusoft.greenmatter.MatHelper;
import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.util.ColorUtils;

/** Extends RatingBar to apply the correct styling. */
public class MatRatingBar extends RatingBar {

    private static final float BORDER_RATIO = 20f;
    private static final float STAR_INNER_RATION = 6f;
    private static final float STAR_OUTER_RATION = 2.5f;

    private MatPalette mPalette;
    private Paint mFillPaint;

    public MatRatingBar(Context context) {
        super(context);
        init(context, null, android.R.attr.ratingBarStyle);
    }

    public MatRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, android.R.attr.ratingBarStyle);
    }

    public MatRatingBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mPalette = MatHelper.getPalette(context);
        mFillPaint = initFillPaint();
    }

    private Paint initFillPaint() {
        Paint result = new Paint();
        result.setStyle(Paint.Style.FILL);
        result.setAntiAlias(false);
        return result;
    }

    private Paint initBorderPaint(float starWidth) {
        Paint result = new Paint();
        result.setStyle(Paint.Style.STROKE);
        result.setStrokeWidth(starWidth / BORDER_RATIO);
        result.setStrokeJoin(Paint.Join.ROUND);
        result.setAntiAlias(true);
        return result;
    }

    protected synchronized void onDraw(Canvas canvas) {
        if (mPalette == null) {
            super.onDraw(canvas);
            return;
        }

        float width = getWidth();
        float starWidth = width / getNumStars();
        float centerX = starWidth / 2f;
        float centerY = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2f;
        centerY += getPaddingTop();
        Path starPath = getStarPath(centerX, centerY, starWidth / STAR_INNER_RATION, starWidth / STAR_OUTER_RATION);

        float progress = (float)getProgress() / getMax();
        int nonEmptyStars = Math.round(getNumStars() * progress);

        int[] drawableState = getDrawableState();
        boolean pressed = hasValue(drawableState, android.R.attr.state_pressed);
        boolean focused = hasValue(drawableState, android.R.attr.state_focused);

        drawBackground(canvas, starPath, starWidth, progress, focused, pressed);
        drawBorder(canvas, starPath, starWidth, nonEmptyStars, focused, pressed);
    }

    private void drawBackground(Canvas canvas, Path starPath, float starWidth, float progress, boolean pressed, boolean focused) {
        int color = (pressed || focused) ? mPalette.getColorControlActivated() : mPalette.getColorControlNormal();
        mFillPaint.setColor(color);

        canvas.save();
        canvas.clipRect(0f, 0f, getWidth() * progress, getHeight());
        for (int i=0; i<getNumStars(); i++) {
            canvas.drawPath(starPath, mFillPaint);
            canvas.translate(starWidth, 0f);
        }
        canvas.restore();
    }

    private void drawBorder(Canvas canvas, Path starPath, float starWidth, int nonEmptyStars, boolean pressed, boolean focused) {
        Paint borderPaint = initBorderPaint(starWidth);
        int color = (pressed || focused) ? mPalette.getColorControlActivated() : mPalette.getColorControlNormal();
        borderPaint.setColor(color);

        canvas.save();
        for (int i=0; i<getNumStars(); i++) {
            canvas.drawPath(starPath, borderPaint);
            canvas.translate(starWidth, 0f);
        }
        canvas.restore();

        canvas.save();
        color = ColorUtils.replaceColorAlpha(color, 255);
        borderPaint.setColor(color);
        for (int i=0; i<nonEmptyStars; i++) {
            canvas.drawPath(starPath, borderPaint);
            canvas.translate(starWidth, 0f);
        }
        canvas.restore();
    }

    private boolean hasValue(int[] allValues, int value) {
        for (int i : allValues) {
            if (i == value)
                return true;
        }
        return false;
    }


    private Path getStarPath(float centerX, float centerY, float innerRadius, float outerRadius) {

        double angle = 2.0*Math.PI/10.0;
        float innerTopX = innerRadius*(float)Math.sin(angle);
        float innerTopY = innerRadius*(float)Math.cos(angle);
        float outerTopX = outerRadius*(float)Math.sin(2f*angle);
        float outerTopY = outerRadius*(float)Math.cos(2f*angle);
        float innerBottomX = innerRadius*(float)Math.sin(3f*angle);
        float innerBottomY = innerRadius*(float)Math.cos(3f*angle);
        float outerBottomX = outerRadius*(float)Math.sin(4f*angle);
        float outerBottomY = outerRadius*(float)Math.cos(4f*angle);

        Path result = new Path();
        result.moveTo(centerX, centerY - outerRadius);

        result.lineTo(centerX + innerTopX, centerY - innerTopY);
        result.lineTo(centerX + outerTopX, centerY - outerTopY);
        result.lineTo(centerX + innerBottomX, centerY - innerBottomY);
        result.lineTo(centerX + outerBottomX, centerY - outerBottomY);

        result.lineTo(centerX, centerY + innerRadius);

        result.lineTo(centerX - outerBottomX, centerY - outerBottomY);
        result.lineTo(centerX - innerBottomX, centerY - innerBottomY);
        result.lineTo(centerX - outerTopX, centerY - outerTopY);
        result.lineTo(centerX - innerTopX, centerY - innerTopY);

        result.close();
        return result;
    }
}