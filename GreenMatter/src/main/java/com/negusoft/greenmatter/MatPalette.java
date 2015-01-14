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
package com.negusoft.greenmatter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;

/**
 * Represents a set of color and makes it easy to
 * access their derived versions: Like translucent or
 * dark versions.
 */
public class MatPalette {

    /** Inner class to manage color components */
    private class ColorWrapper {
        final int color;
        final int alpha, red, green, blue;
        ColorWrapper(int color) {
            this.color = color;
            alpha = Color.alpha(color);
            red = Color.red(color);
            green = Color.green(color);
            blue = Color.blue(color);
        }
        ColorWrapper(int color, float percentage) {
            this.color = color;
            alpha = Color.alpha(color);
            red = (int)(Color.red(color) * percentage);
            green = (int)(Color.green(color) * percentage);
            blue = (int)(Color.blue(color) * percentage);
        }
        int getColor(int alpha) {
            int resultAlpha = this.alpha == 255 ? alpha : this.alpha * alpha / 255;
            return Color.argb(resultAlpha, red, green, blue);
        }
    }

	private static final float DARK_COLOR_PERCENTAGE = 0.85f;
    private static final int DEFAULT_COLOR = 0xff33b5e5;
    private static final float DEFAULT_DISABLED_ALPHA = 0.5f;

    /** Create an instance from the context theme. */
    public static MatPalette createFromTheme(Context context) {
        TypedArray attrs = context.getTheme().obtainStyledAttributes(R.styleable.GreenMatter);

        int primary = attrs.getColor(R.styleable.GreenMatter_matColorPrimary, DEFAULT_COLOR);
        int primaryDark = attrs.getColor(R.styleable.GreenMatter_matColorPrimaryDark, 0);
        int accent = attrs.getColor(R.styleable.GreenMatter_matColorAccent, DEFAULT_COLOR);
        int controlNormal = attrs.getColor(R.styleable.GreenMatter_matColorControlNormal, DEFAULT_COLOR);
        int controlActivated = attrs.getColor(R.styleable.GreenMatter_matColorControlActivated, DEFAULT_COLOR);
        int controlHighlighted = attrs.getColor(R.styleable.GreenMatter_matColorControlHighlight, DEFAULT_COLOR);
        int buttonNormal = attrs.getColor(R.styleable.GreenMatter_matColorButtonNormal, DEFAULT_COLOR);
        int switchThumbNormal = attrs.getColor(R.styleable.GreenMatter_matColorSwitchThumbNormal, DEFAULT_COLOR);
        int edgeEffect = attrs.getColor(R.styleable.GreenMatter_matColorEdgeEffect, DEFAULT_COLOR);
        float disabledAlpha = attrs.getFloat(R.styleable.GreenMatter_android_disabledAlpha, DEFAULT_DISABLED_ALPHA);

        attrs.recycle();

        return new MatPalette(primary, primaryDark, accent, controlNormal, controlActivated,
                controlHighlighted, buttonNormal, switchThumbNormal, edgeEffect, disabledAlpha);
    }

    private float mDisabledAlpha = 0.5f;

    private ColorWrapper mColorPrimary;
    private ColorWrapper mColorPrimaryDark;
    private ColorWrapper mColorAccent;

    private ColorWrapper mColorControlNormal;
    private ColorWrapper mColorControlActivated;
    private ColorWrapper mColorControlHighlight;

    private ColorWrapper mColorButtonNormal;
    private ColorWrapper mColorSwitchThumbNormal;
    private ColorWrapper mColorEdgeEffect;

	/** Create an instance with the colors explicitly specified. */
	public MatPalette(int colorPrimary, int colorPrimaryDark, int colorAccent,
                      int colorControlNormal, int colorControlActivated, int colorControlHighlighted,
                      int colorButtonNormal, int colorSwitchThumbNormal, int colorEdgeEffect,
                      float disabledAlpha) {
        mColorPrimary = new ColorWrapper(colorPrimary);
        mColorPrimaryDark = colorPrimaryDark == 0 ?
                new ColorWrapper(colorPrimary, DARK_COLOR_PERCENTAGE) :
                new ColorWrapper(colorPrimaryDark);
        mColorAccent = new ColorWrapper(colorAccent);

        mColorControlNormal = new ColorWrapper(colorControlNormal);
        mColorControlActivated = new ColorWrapper(colorControlActivated);
        mColorControlHighlight = new ColorWrapper(colorControlHighlighted);

        mColorButtonNormal = new ColorWrapper(colorButtonNormal);
        mColorSwitchThumbNormal = new ColorWrapper(colorSwitchThumbNormal);
        mColorEdgeEffect = new ColorWrapper(colorEdgeEffect);

        mDisabledAlpha = disabledAlpha;
	}

    /** @return The transparency for the disabled state (between 0..1). */
    public float getDisabledAlpha() {
        return mDisabledAlpha;
    }
    /** Set the transparency for the disabled state (between 0..1). */
    public void setDisabledAlpha(float alpha) {
        mDisabledAlpha = alpha;
    }
	
	/** @return The primary color. */
	public int getColorPrimary() {
		return mColorPrimary.color;
	}
    /**
     * Get a translucent version of the primary color.
     * @param alpha The opacity of the color [0..255]
     */
    public int getColorPrimary(int alpha) {
        return mColorPrimary.getColor(alpha);
    }
    /** Set the primary color. */
    public void setColorPrimary(int color) {
        mColorPrimary = new ColorWrapper(color);
    }

    /** @return The dark primary color. */
    public int getColorPrimaryDark() {
        return mColorPrimaryDark.color;
    }
    /**
     * Get a translucent version of the dark variant of the primary color.
     * @param alpha The opacity of the color [0..255]
     */
    public int getColorPrimaryDark(int alpha) {
        return mColorPrimaryDark.getColor(alpha);
    }
    /** Set the dark variant of the primary color. */
    public void setColorPrimaryDark(int color) {
        mColorPrimaryDark = new ColorWrapper(color);
    }

    /** @return The accent color. */
    public int getColorAccent() {
        return mColorAccent.color;
    }
    /**
     * Get a translucent version of the accent color.
     * @param alpha The opacity of the color [0..255]
     */
    public int getColorAccent(int alpha) {
        return mColorAccent.getColor(alpha);
    }
    /** Set the accent color. */
    public void setColorAccent(int color) {
        mColorAccent = new ColorWrapper(color);
    }

    /** @return The normal control color. */
    public int getColorControlNormal() {
        return mColorControlNormal.color;
    }
    /**
     * Get a translucent version of the normal control color.
     * @param alpha The opacity of the color [0..255]
     */
    public int getColorControlNormal(int alpha) {
        return mColorControlNormal.getColor(alpha);
    }
    /** Set the normal control color. */
    public void setColorControlNormal(int color) {
        mColorControlNormal = new ColorWrapper(color);
    }

    /** @return The activated control color. */
    public int getColorControlActivated() {
        return mColorControlActivated.color;
    }
    /**
     * Get a translucent version of the activated control color.
     * @param alpha The opacity of the color [0..255]
     */
    public int getColorControlActivated(int alpha) {
        return mColorControlActivated.getColor(alpha);
    }
    /** Set the activated control color. */
    public void setColorControlActivated(int color) {
        mColorControlActivated = new ColorWrapper(color);
    }

    /** @return The highlight control color. */
    public int getColorControlHighlight() {
        return mColorControlHighlight.color;
    }
    /**
     * Get a translucent version of the highlight control color.
     * @param alpha The opacity of the color [0..255]
     */
    public int getColorControlHighlight(int alpha) {
        return mColorControlHighlight.getColor(alpha);
    }
    /** Set the highlight control color. */
    public void setColorControlHighlight(int color) {
        mColorControlHighlight = new ColorWrapper(color);
    }

    /** @return The button color in normal state. */
    public int getColorButtonNormal() {
        return mColorButtonNormal.color;
    }
    /**
     * Get a translucent version of the button color in normal state.
     * @param alpha The opacity of the color [0..255]
     */
    public int getColorButtonNormal(int alpha) {
        return mColorButtonNormal.getColor(alpha);
    }
    /** Set the button color in normal state. */
    public void setColorButtonNormal(int color) {
        mColorButtonNormal = new ColorWrapper(color);
    }

    /** @return The switch thumb color in normal state. */
    public int getColorSwitchThumbNormal() {
        return mColorSwitchThumbNormal.color;
    }
    /**
     * Get a translucent version of the switch thumb color in normal state.
     * @param alpha The opacity of the color [0..255]
     */
    public int getColorSwitchThumbNormal(int alpha) {
        return mColorSwitchThumbNormal.getColor(alpha);
    }
    /** Set the switch thumb color in normal state. */
    public void setColorSwitchThumbNormal(int color) {
        mColorSwitchThumbNormal = new ColorWrapper(color);
    }

    /** @return The edge effect color. */
    public int getColorEdgeEffect() {
        return mColorEdgeEffect.color;
    }
    /**
     * Get a translucent version of the edge effect color.
     * @param alpha The opacity of the color [0..255]
     */
    public int getColorEdgeEffect(int alpha) {
        return mColorEdgeEffect.getColor(alpha);
    }
    /** Set the edge effect color. */
    public void setColorEdgeEffect(int color) {
        mColorEdgeEffect = new ColorWrapper(color);
    }

}
