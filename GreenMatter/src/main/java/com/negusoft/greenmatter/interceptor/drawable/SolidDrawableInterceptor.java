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
package com.negusoft.greenmatter.interceptor.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;

public class SolidDrawableInterceptor implements MatResources.DrawableInterceptor {

	private static final int PRESSED_ALPHA = 0x88;
	private static final int FOCUSED_ALPHA = 0x55;

    private final Context mContext;

    public SolidDrawableInterceptor(Context context) {
        mContext = context;
    }

	@Override
	public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
        // Solid colors
        if (resId == R.drawable.gm__solid_primary_reference || resId == R.color.gm__primary)
            return new ColorDrawable(palette.getColorPrimary());
        if (resId == R.drawable.gm__solid_primary_dark_reference || resId == R.color.gm__primary_dark)
            return new ColorDrawable(palette.getColorPrimaryDark());
        if (resId == R.drawable.gm__solid_accent_reference || resId == R.color.gm__accent)
            return new ColorDrawable(palette.getColorAccent());
        if (resId == R.color.gm__control_normal)
            return new ColorDrawable(palette.getColorControlNormal());
        if (resId == R.color.gm__control_activated)
            return new ColorDrawable(palette.getColorControlActivated());
        if (resId == R.color.gm__control_highlighted)
            return new ColorDrawable(palette.getColorControlHighlight());
        if (resId == R.color.gm__background)
            return new ColorDrawable(getBackgroundColor());
        // Other solid drawables
        if (resId == R.drawable.gm__solid_pressed_reference)
            return new ColorDrawable(palette.getColorControlHighlight(PRESSED_ALPHA));
        if (resId == R.drawable.gm__solid_focused_reference)
            return new ColorDrawable(palette.getColorControlHighlight(FOCUSED_ALPHA));
		return null;
	}

    private int getBackgroundColor() {
        int[] styleable = new int[] { android.R.attr.colorBackground };
        TypedArray a = mContext.obtainStyledAttributes(styleable);
        int backgroundColor = a.getColor(0, 0);
        a.recycle();

        return backgroundColor;
    }

}
