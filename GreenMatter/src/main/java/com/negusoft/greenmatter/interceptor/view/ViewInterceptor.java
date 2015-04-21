package com.negusoft.greenmatter.interceptor.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

/**
 * Represents an object that can replace a view by name when inflating the layout.
 */
public interface ViewInterceptor {

    /** Return the list of view names on which createView() will be called. */
    public String[] getViewNames();

    /** Return the view to replace when inflating with the the layout. */
    public View createView(String name, @NonNull Context context, @NonNull AttributeSet attrs);

}
