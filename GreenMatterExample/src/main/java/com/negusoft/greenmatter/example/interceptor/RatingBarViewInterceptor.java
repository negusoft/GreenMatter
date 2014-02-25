package com.negusoft.greenmatter.example.interceptor;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.negusoft.greenmatter.interceptor.view.ViewInterceptor;
import com.negusoft.greenmatter.widget.MatRatingBar;

/** Used along with MatResources to modify colors at runtime */
public class RatingBarViewInterceptor implements ViewInterceptor {

    @Override
    public String[] getViewNames() {
        return new String[] { "RatingBar" };
    }

    @Override
    public View createView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return null;
        return new MatRatingBar(context, attrs);
    }
}
