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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CheckedTextView;

import com.negusoft.greenmatter.drawable.CompoundDrawableWrapper;
import com.negusoft.greenmatter.drawable.TintDrawableWrapper;
import com.negusoft.greenmatter.util.ColorUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/** Extends the CheckedTextView to set the check mark at the start by default. */
public class MatCheckedTextView extends CheckedTextView {

    private Drawable mCheckMarkDrawable;

    public MatCheckedTextView(Context context) {
        super(context);
    }

    public MatCheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MatCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setCheckMarkDrawable(int resid) {

    }

    @Override
    public void setCheckMarkDrawable(Drawable d) {
        Drawable mutated = d;//.mutate();
        super.setCheckMarkDrawable(mutated);
        mCheckMarkDrawable = mutated;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCheckMarkDrawable == null) {
            super.onDraw(canvas);
            return;
        }

        canvas.save();
        canvas.translate(mCheckMarkDrawable.getIntrinsicWidth(), 0);
        setSuperCheckMarkDrawable(null);
        super.onDraw(canvas);
        setSuperCheckMarkDrawable(mCheckMarkDrawable);
        canvas.restore();

        final int verticalGravity = getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
        final int height = mCheckMarkDrawable.getIntrinsicHeight();
        int y = 0;

        switch (verticalGravity) {
            case Gravity.BOTTOM:
                y = getHeight() - height;
                break;
            case Gravity.CENTER_VERTICAL:
                y = (getHeight() - height) / 2;
                break;
        }

        final Drawable checkMarkDrawable = mCheckMarkDrawable;
        checkMarkDrawable.setBounds(
                getCheckMarkPaddingStart(),
                y,
                getCheckMarkPaddingEnd(),
                y + height);
        checkMarkDrawable.draw(canvas);
    }

    private void setSuperCheckMarkDrawable(Drawable drawable) {
        try {
            Field field = CheckedTextView.class.getDeclaredField("mCheckMarkDrawable");
            field.setAccessible(true);
            field.set(this, drawable);
        } catch (NoSuchFieldException e) {
            // Ignore exception
        } catch (IllegalAccessException e) {
            // Ignore exception
        }
    }

    private int getCheckMarkPaddingStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            return getPaddingStart();
        return getPaddingLeft();
    }

    private int getCheckMarkPaddingEnd() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            return getPaddingEnd();
        return getPaddingRight();
    }
}
