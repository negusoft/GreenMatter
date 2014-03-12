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
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import com.negusoft.greenmatter.drawable.CompoundDrawableWrapper;
import com.negusoft.greenmatter.drawable.TintDrawableWrapper;
import com.negusoft.greenmatter.util.ColorUtils;

/** Extends the button to add the "color" property. */
public class MatButton extends Button {

    private static final float DEFAULT_DISABLED_ALPHA = 0.3f;

    /** Abstraction for setting the colorState list to a drawable. */
    private interface ColorDelegate {
        public void setColorStateList(ColorStateList colorStateList);
    }

    private final Context mContext;

    public MatButton(Context context) {
        super(context);
        mContext = context;
        MatButton.initBackgroundColor(getBackground(), context, null);
    }

    public MatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        MatButton.initBackgroundColor(getBackground(), context, attrs);
    }

    public MatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        MatButton.initBackgroundColor(getBackground(), context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MatButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        MatButton.initBackgroundColor(getBackground(), context, attrs);
    }

    /**
     * Set the buttons background color. The disabled color is calculated using the 'disabledAlpha'
     * attribute. The method may not work if you changed the button's background, either
     * programmatically or by using a style.
     */
    public void setColor(int color) {
        MatButton.setColor(mContext, getBackground(), color);
    }

    /**
     * Set the buttons background color. The method may not work if you changed the button's
     * background, either programmatically or by using a style.
     */
    public void setColor(ColorStateList colorStateList) {
        MatButton.setColor(getBackground(), colorStateList);
    }

    static void initBackgroundColor(Drawable background, Context context, AttributeSet attrs) {
        ColorDelegate colorSetter = MatButton.getColorSetter(background);
        if (colorSetter == null)
            return;

        int[] styleable = new int[] { android.R.attr.color, android.R.attr.disabledAlpha };
        TypedArray a = context.obtainStyledAttributes(attrs, styleable);
        ColorStateList colorStateList = a.getColorStateList(0);
        float disabledAlpha = a.getFloat(1, DEFAULT_DISABLED_ALPHA);
        a.recycle();

        if (colorStateList != null) {
            colorStateList = MatButton.completeColorStateList(colorStateList, disabledAlpha);
            colorSetter.setColorStateList(colorStateList);
        }
    }

    static void setColor(Context context, Drawable background, int color) {
        ColorDelegate colorSetter = MatButton.getColorSetter(background);
        if (colorSetter == null)
            return;

        int[] styleable = new int[] { android.R.attr.disabledAlpha };
        TypedArray a = context.obtainStyledAttributes(styleable);
        float disabledAlpha = a.getFloat(0, DEFAULT_DISABLED_ALPHA);
        a.recycle();

        final int[][] states = new int[][] { new int[0] };
        final int[] colors = new int[] { color };
        ColorStateList colorStateList = new ColorStateList(states, colors);

        colorStateList = MatButton.completeColorStateList(colorStateList, disabledAlpha);
        colorSetter.setColorStateList(colorStateList);
    }

    static void setColor(Drawable background, ColorStateList colorStateList) {
        ColorDelegate colorSetter = MatButton.getColorSetter(background);
        if (colorSetter != null) {
            colorSetter.setColorStateList(colorStateList);
        }
    }

    /**
     * Get an appropriate ColorDelegate implementation for the current Android version:
     * Pre-Lollipop: Modify the TintDrawableWrapper
     * Post-Lollipop: Modify the inner shape of the RippleDrawable
     * @return The ColorDelegate implementation or null if no coloring required.
     */
    private static ColorDelegate getColorSetter(Drawable background) {
        // Pre-Lollipop
        final TintDrawableWrapper tintDrawableWrapper = MatButton.getTinedDrawable(background);
        if (tintDrawableWrapper != null) {
            return new ColorDelegate() {
                @Override public void setColorStateList(ColorStateList colorStateList) {
                    tintDrawableWrapper.setTintStateList(colorStateList);
                }
            };
        }

        // Post-Lollipop
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return null;
        final GradientDrawable rippleShapeDrawable = MatButton.getRippleShapeDrawable(background);
        if (rippleShapeDrawable != null) {
            return new ColorDelegate() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override public void setColorStateList(ColorStateList colorStateList) {
                    rippleShapeDrawable.setColor(colorStateList);
                }
            };
        }

        return null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static GradientDrawable getRippleShapeDrawable(Drawable background) {
        if (!(background instanceof RippleDrawable))
            return null;

        RippleDrawable ripple = (RippleDrawable)background;
        Drawable possibleInset = ripple.getDrawable(0);
        if (!(possibleInset instanceof InsetDrawable))
            return null;

        InsetDrawable inset = (InsetDrawable)possibleInset;
        Drawable possibleGradient = inset.getDrawable();
        if (!(possibleGradient instanceof GradientDrawable))
            return null;

        // Mutate the drawable, otherwise the color will be set for all instances
        possibleGradient = possibleGradient.mutate();
        return (GradientDrawable)possibleGradient;
    }

    /** Return the inner tinted drawable, null if it couln't be fetched (if a custom background was set) */
    private static TintDrawableWrapper getTinedDrawable(Drawable background) {
        if (!(background instanceof StateListDrawable))
            return null;

        Drawable innerDrawalbe = ((StateListDrawable)background).getCurrent();
        if (!(innerDrawalbe instanceof CompoundDrawableWrapper))
            return null;


        Drawable primaryDrawable = ((CompoundDrawableWrapper)innerDrawalbe).getPrimaryDrawable();
        if (!(primaryDrawable instanceof TintDrawableWrapper))
            return null;
        return (TintDrawableWrapper)primaryDrawable;
    }

    /** If there is only one color in the color state list, add the disabled state */
    private static ColorStateList completeColorStateList(ColorStateList colorStateList, float alpha) {
        if (colorStateList.isStateful())
            return colorStateList;

        int color = colorStateList.getDefaultColor();

        final int[][] states = new int[2][];
        final int[] colors = new int[2];

        // Disabled
        states[0] = new int[] { -android.R.attr.state_enabled };
        colors[0] = ColorUtils.applyColorAlpha(color, alpha);
        // Default (enabled)
        states[1] = new int[0];
        colors[1] = color;

        return new ColorStateList(states, colors);
    }

}
