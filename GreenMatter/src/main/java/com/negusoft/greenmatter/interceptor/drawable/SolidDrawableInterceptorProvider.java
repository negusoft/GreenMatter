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
package com.negusoft.greenmatter.interceptor.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;

public class SolidDrawableInterceptorProvider {

	private static final int PRESSED_ALPHA = 0x88;
	private static final int FOCUSED_ALPHA = 0x55;

    public static void setupInterceptors(DrawableInterceptorHelper helper, Context context) {
        setupPrimary(helper);
        setupPrimaryDark(helper);
        setupAccent(helper);
        setupControlNormal(helper);
        setupControlActivated(helper);
        setupControlHighlight(helper);
        setupBackground(helper, context);
        // Other solid drawables
        setupPressed(helper);
        setupFocused(helper);
    }

    /** Put the interceptor for the primary color. **/
    private static void setupPrimary(DrawableInterceptorHelper helper) {
        DrawableInterceptor interceptor = new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                return new ColorDrawable(palette.getColorPrimary());
            }
        };
        helper.putInterceptor(R.drawable.gm__solid_primary_reference, interceptor);
        helper.putInterceptor(R.color.gm__primary, interceptor);
    }

    /** Put the interceptor for the primary dark color. **/
    private static void setupPrimaryDark(DrawableInterceptorHelper helper) {
        DrawableInterceptor interceptor = new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                return new ColorDrawable(palette.getColorPrimaryDark());
            }
        };
        helper.putInterceptor(R.drawable.gm__solid_primary_dark_reference, interceptor);
        helper.putInterceptor(R.color.gm__primary_dark, interceptor);
    }

    /** Put the interceptor for the accent color. **/
    private static void setupAccent(DrawableInterceptorHelper helper) {
        DrawableInterceptor interceptor = new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                return new ColorDrawable(palette.getColorAccent());
            }
        };
        helper.putInterceptor(R.drawable.gm__solid_accent_reference, interceptor);
        helper.putInterceptor(R.color.gm__accent, interceptor);
    }

    /** Put the interceptor for the control normal color. **/
    private static void setupControlNormal(DrawableInterceptorHelper helper) {
        helper.putInterceptor(R.color.gm__control_normal, new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                return new ColorDrawable(palette.getColorControlNormal());
            }
        });
    }

    /** Put the interceptor for the control activated color. **/
    private static void setupControlActivated(DrawableInterceptorHelper helper) {
        helper.putInterceptor(R.color.gm__control_activated, new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                return new ColorDrawable(palette.getColorControlActivated());
            }
        });
    }

    /** Put the interceptor for the control highlighted color. **/
    private static void setupControlHighlight(DrawableInterceptorHelper helper) {
        helper.putInterceptor(R.color.gm__control_highlighted, new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                return new ColorDrawable(palette.getColorControlHighlight());
            }
        });
    }

    /** Put the interceptor for the background color. **/
    private static void setupBackground(DrawableInterceptorHelper helper, Context context) {
        helper.putInterceptor(R.color.gm__background, new BackgroundColorDrawable(context));
    }

    /** Put the interceptor for the pressed color. **/
    private static void setupPressed(DrawableInterceptorHelper helper) {
        helper.putInterceptor(R.drawable.gm__solid_pressed_reference, new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                return new ColorDrawable(palette.getColorControlHighlight(PRESSED_ALPHA));
            }
        });
    }

    /** Put the interceptor for the focused color. **/
    private static void setupFocused(DrawableInterceptorHelper helper) {
        helper.putInterceptor(R.drawable.gm__solid_focused_reference, new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                return new ColorDrawable(palette.getColorControlHighlight(FOCUSED_ALPHA));
            }
        });
    }

    private static class BackgroundColorDrawable implements DrawableInterceptor {

        private final Context mContext;

        BackgroundColorDrawable(Context context) {
            mContext = context;
        }

        @Override
        public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
            return new ColorDrawable(getBackgroundColor());
        }

        private int getBackgroundColor() {
            int[] styleable = new int[] { android.R.attr.colorBackground };
            TypedArray a = mContext.obtainStyledAttributes(styleable);
            int backgroundColor = a.getColor(0, 0);
            a.recycle();

            return backgroundColor;
        }
    }

}
