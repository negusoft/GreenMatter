package com.negusoft.greenmatter.interceptor.color;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;

/** Replace the color references by actual colors */
public class MatColorInterceptor implements MatResources.ColorInterceptor {

    private final Context mContext;

    public MatColorInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public int getColor(Resources res, MatPalette palette, int resId) {
        if (resId == R.color.gm__primary_reference)
            return palette.getColorPrimary();
        if (resId == R.color.gm__primary_dark_reference)
            return palette.getColorPrimaryDark();
        if (resId == R.color.gm__accent_reference)
            return palette.getColorAccent();
        if (resId == R.color.gm__control_normal_reference)
            return palette.getColorControlNormal();
        if (resId == R.color.gm__control_activated_reference)
            return palette.getColorControlActivated();
        if (resId == R.color.gm__control_highlighted_reference)
            return palette.getColorControlHighlight();
        if (resId == R.color.gm__switch_thumb_normal_reference)
            return palette.getColorSwitchThumbNormal();
        if (resId == R.color.gm__fastscroll_track_reference)
            return palette.getColorControlNormal(0x7F);
        if (resId == R.color.gm__background_reference)
            return palette.getColorControlHighlight();
        if (resId == R.color.gm__picker_divider_reference)
            return palette.getColorControlActivated(0xCC);
        if (resId == R.color.gm__calendar_selected_week_reference)
            return palette.getColorControlActivated(0x33);
        return 0;
    }

    private int getBackgroundColor() {
        int[] styleable = new int[] { android.R.attr.colorBackground };
        TypedArray a = mContext.obtainStyledAttributes(styleable);
        int backgroundColor = a.getColor(0, 0);
        a.recycle();

        return backgroundColor;
    }

}
