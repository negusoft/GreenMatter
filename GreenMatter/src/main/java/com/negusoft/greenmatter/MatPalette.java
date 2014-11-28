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

    private static final float DEFAULT_DISABLED_ALPHA = 0.5f;
	private static final float DARK_COLOR_PERCENTAGE = 0.85f;

	public final int colorPrimary;
	public final int redPrimary;
	public final int greenPrimary;
	public final int bluePrimary;

    public final int colorPrimaryDark;
    public final int redPrimaryDark;
    public final int greenPrimaryDark;
    public final int bluePrimaryDark;

    public final int colorAccent;
    public final int redAccent;
    public final int greenAccent;
    public final int blueAccent;

    // TODO get disabled alpha from theme
    public final float disabledAlpha = DEFAULT_DISABLED_ALPHA;

	/**
	 * Create an instance with the specified colors. The dark
	 * variant of the primary color will be derived from it.
	 */
	public MatPalette(int colorPrimary, int colorAccent) {
		redPrimary = Color.red(colorPrimary);
		greenPrimary = Color.green(colorPrimary);
		bluePrimary = Color.blue(colorPrimary);
        this.colorPrimary = Color.rgb(redPrimary, greenPrimary, bluePrimary);

		redPrimaryDark = (int)(redPrimary * DARK_COLOR_PERCENTAGE);
		greenPrimaryDark = (int)(greenPrimary * DARK_COLOR_PERCENTAGE);
		bluePrimaryDark = (int)(bluePrimary * DARK_COLOR_PERCENTAGE);
        this.colorPrimaryDark = Color.rgb(redPrimaryDark, greenPrimaryDark, bluePrimaryDark);

        redAccent = Color.red(colorAccent);
        greenAccent = Color.green(colorAccent);
        blueAccent = Color.blue(colorAccent);
        this.colorAccent = Color.rgb(redAccent, greenAccent, blueAccent);
	}

	/**
	 * Create an instance with the colors explicitly specified.
	 */
	public MatPalette(int colorPrimary, int colorPrimaryDark, int colorAccent) {
		redPrimary = Color.red(colorPrimary);
		greenPrimary = Color.green(colorPrimary);
		bluePrimary = Color.blue(colorPrimary);
        this.colorPrimary = Color.rgb(redPrimary, greenPrimary, bluePrimary);

		if (colorPrimaryDark == 0) {
			redPrimaryDark = (int)(redPrimary * DARK_COLOR_PERCENTAGE);
			greenPrimaryDark = (int)(greenPrimary * DARK_COLOR_PERCENTAGE);
			bluePrimaryDark = (int)(bluePrimary * DARK_COLOR_PERCENTAGE);
            this.colorPrimaryDark = Color.rgb(redPrimaryDark, greenPrimaryDark, bluePrimaryDark);
		}
		else {
			redPrimaryDark = Color.red(colorPrimaryDark);
			greenPrimaryDark = Color.green(colorPrimaryDark);
			bluePrimaryDark = Color.blue(colorPrimaryDark);
            this.colorPrimaryDark = Color.rgb(redPrimaryDark, greenPrimaryDark, bluePrimaryDark);
		}

        redAccent = Color.red(colorAccent);
        greenAccent = Color.green(colorAccent);
        blueAccent = Color.blue(colorAccent);
        this.colorAccent = Color.rgb(redAccent, greenAccent, blueAccent);
	}
	
	/** @return The primary color. Same as 'colorPrimary'. */
	public int getColorPrimary() {
		return colorPrimary;
	}

	/** 
	 * Get a translucent version of the primary color.
	 * @param alpha The opacity of the color [0..255]
	 */
	public int getColorPrimary(int alpha) {
		return Color.argb(alpha, redPrimary, greenPrimary, bluePrimary);
	}

    /** @return The dark primary color. Same as 'colorPrimaryDark'. */
    public int getColorPrimaryDark() {
        return colorPrimaryDark;
    }

    /**
     * Get a translucent version of the dark variant of the primary color.
     * @param alpha The opacity of the color [0..255]
     */
    public int getPrimaryColorDark(int alpha) {
        return Color.argb(alpha, redPrimaryDark, greenPrimaryDark, bluePrimaryDark);
    }

    /** @return The accent color. Same as 'colorAccent'. */
    public int getColorAccent() {
        return colorAccent;
    }

    /**
     * Get a translucent version of the accent color.
     * @param alpha The opacity of the color [0..255]
     */
    public int getColorAccent(int alpha) {
        return Color.argb(alpha, redAccent, greenAccent, blueAccent);
    }

    /** Get a color state list for widgets */
    // TODO set colors based on widget colors
    public ColorStateList getControlColorStateList() {
        final int[][] states = new int[7][];
        final int[] colors = new int[7];
        int i = 0;

        // Disabled state
        states[i] = new int[] { -android.R.attr.state_enabled };
        colors[i] = Color.argb((int)(disabledAlpha * 255), 0x88, 0x88, 0x88);
        i++;

        states[i] = new int[] { android.R.attr.state_focused };
        colors[i] = colorAccent;
        i++;

        states[i] = new int[] { android.R.attr.state_activated };
        colors[i] = colorAccent;
        i++;

        states[i] = new int[] { android.R.attr.state_pressed };
        colors[i] = colorAccent;
        i++;

        states[i] = new int[] { android.R.attr.state_checked };
        colors[i] = colorAccent;
        i++;

        states[i] = new int[] { android.R.attr.state_selected };
        colors[i] = colorAccent;
        i++;

        // Default enabled state
        states[i] = new int[0];
        colors[i] = Color.GRAY;
        i++;

        return new ColorStateList(states, colors);
    }

}
