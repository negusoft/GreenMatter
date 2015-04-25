package com.negusoft.greenmatter.interceptor.color;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;
import com.negusoft.greenmatter.interceptor.drawable.DrawableInterceptor;

/** Replaces the color references by actual colors */
public class ColorInterceptorHelper {

    private final Context mContext;

    private final SparseArray<ColorInterceptor> mInterceptors;

    public ColorInterceptorHelper(Context context) {
        mInterceptors = new SparseArray<>();
        mContext = context;

        initInterceptors();
    }

    private void initInterceptors() {
        mInterceptors.put(R.color.gm__primary_reference, new ColorInterceptor() {
            @Override
            public int getColor(Resources res, MatPalette palette, int resId) {
                return palette.getColorPrimary();
            }
        });
        mInterceptors.put(R.color.gm__primary_dark_reference, new ColorInterceptor() {
            @Override
            public int getColor(Resources res, MatPalette palette, int resId) {
                return palette.getColorPrimaryDark();
            }
        });
        mInterceptors.put(R.color.gm__accent_reference, new ColorInterceptor() {
            @Override
            public int getColor(Resources res, MatPalette palette, int resId) {
                return palette.getColorAccent();
            }
        });
        mInterceptors.put(R.color.gm__control_normal_reference, new ColorInterceptor() {
            @Override
            public int getColor(Resources res, MatPalette palette, int resId) {
                return palette.getColorControlNormal();
            }
        });
        mInterceptors.put(R.color.gm__control_activated_reference, new ColorInterceptor() {
            @Override
            public int getColor(Resources res, MatPalette palette, int resId) {
                return palette.getColorControlActivated();
            }
        });
        mInterceptors.put(R.color.gm__control_highlighted_reference, new ColorInterceptor() {
            @Override
            public int getColor(Resources res, MatPalette palette, int resId) {
                return palette.getColorControlHighlight();
            }
        });
        mInterceptors.put(R.color.gm__switch_thumb_normal_reference, new ColorInterceptor() {
            @Override
            public int getColor(Resources res, MatPalette palette, int resId) {
                return palette.getColorSwitchThumbNormal();
            }
        });
        mInterceptors.put(R.color.gm__fastscroll_track_reference, new ColorInterceptor() {
            @Override
            public int getColor(Resources res, MatPalette palette, int resId) {
                return palette.getColorControlNormal(0x7F);
            }
        });
        mInterceptors.put(R.color.gm__background_reference, new ColorInterceptor() {
            @Override
            public int getColor(Resources res, MatPalette palette, int resId) {
                return palette.getColorControlHighlight();
            }
        });
        mInterceptors.put(R.color.gm__picker_divider_reference, new ColorInterceptor() {
            @Override
            public int getColor(Resources res, MatPalette palette, int resId) {
                return palette.getColorControlActivated(0xCC);
            }
        });
        mInterceptors.put(R.color.gm__calendar_selected_week_reference, new ColorInterceptor() {
            @Override
            public int getColor(Resources res, MatPalette palette, int resId) {
                return palette.getColorControlActivated(0x33);
            }
        });
    }

    public void putInterceptor(int resId, ColorInterceptor interceptor) {
        mInterceptors.put(resId, interceptor);
    }

    public void removeInterceptor(int resId) {
        mInterceptors.remove(resId);
    }

    public int getOverrideColor(Resources res, MatPalette palette, int resId) {
        ColorInterceptor interceptor = mInterceptors.get(resId);
        if (interceptor == null)
            return 0;
        return interceptor.getColor(res, palette, resId);
    }

}
