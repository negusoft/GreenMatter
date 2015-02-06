package com.negusoft.greenmatter.example.interceptor;

import android.content.res.Resources;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.example.R;

/** Used along with MatResources to modify colors at runtime */
public class ColorInterceptor implements MatResources.ColorInterceptor {
    @Override
    public int getColor(Resources res, MatPalette palette, int resId) {
        if (resId == R.color.checkable_listview_item_reference)
            return palette.getColorControlActivated(0x66);
        return 0;
    }
}
