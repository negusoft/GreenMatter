package com.negusoft.greenmatter.interceptor.color;

import android.content.res.Resources;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;

/** Replace the color references by actual colors */
public class MatColorInterceptor implements MatResources.ColorInterceptor {

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
        return 0;
    }

}
