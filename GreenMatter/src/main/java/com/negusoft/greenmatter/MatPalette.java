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

import android.content.res.ColorStateList;
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

    private float mDisabledAlpha = 0.5f;

    private ColorWrapper mColorPrimary;
    private ColorWrapper mColorPrimaryDark;
    private ColorWrapper mColorAccent;

    private ColorWrapper mColorControlNormal;
    private ColorWrapper mColorControlActivated;
    private ColorWrapper mColorControlHighlight;

	/**
	 * Create an instance with the specified colors. The dark
	 * variant of the primary color will be derived from it.
	 */
	public MatPalette(int colorPrimary, int colorAccent) {
        mColorPrimary = new ColorWrapper(colorPrimary);
        mColorPrimaryDark = new ColorWrapper(colorPrimary, DARK_COLOR_PERCENTAGE);
        mColorAccent = new ColorWrapper(colorAccent);
	}

	/**
	 * Create an instance with the colors explicitly specified.
	 */
	public MatPalette(int colorPrimary, int colorPrimaryDark, int colorAccent) {
        mColorPrimary = new ColorWrapper(colorPrimary);
        mColorPrimaryDark = colorPrimaryDark == 0 ?
                new ColorWrapper(colorPrimary, DARK_COLOR_PERCENTAGE) :
                new ColorWrapper(colorPrimaryDark);
        mColorAccent = new ColorWrapper(colorAccent);
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

    /** Get a color state list for widgets */
    // TODO set colors based on widget colors
    public ColorStateList getControlColorStateList() {
        final int[][] states = new int[7][];
        final int[] colors = new int[7];
        int i = 0;

        // Disabled state
        states[i] = new int[] { -android.R.attr.state_enabled };
        colors[i] = Color.argb((int)(mDisabledAlpha * 255), 0x88, 0x88, 0x88);
        i++;

        states[i] = new int[] { android.R.attr.state_focused };
        colors[i] = mColorAccent.color;
        i++;

        states[i] = new int[] { android.R.attr.state_activated };
        colors[i] = mColorAccent.color;
        i++;

        states[i] = new int[] { android.R.attr.state_pressed };
        colors[i] = mColorAccent.color;
        i++;

        states[i] = new int[] { android.R.attr.state_checked };
        colors[i] = mColorAccent.color;
        i++;

        states[i] = new int[] { android.R.attr.state_selected };
        colors[i] = mColorAccent.color;
        i++;

        // Default enabled state
        states[i] = new int[0];
        colors[i] = Color.GRAY;
        i++;

        return new ColorStateList(states, colors);
    }

}
