package com.negusoft.greenmatter.example.interceptor;

import android.content.res.Resources;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.example.R;
import com.negusoft.greenmatter.interceptor.color.ColorInterceptor;

/** Used along with MatResources to modify colors at runtime */
public class CheckableBackgroundColorInterceptor implements ColorInterceptor {

    public static final int RESOURCE_ID = R.color.checkable_listview_item_reference;

    @Override
    public int getColor(Resources res, MatPalette palette, int resId) {
        return palette.getColorControlActivated(0x66);
    }
}
