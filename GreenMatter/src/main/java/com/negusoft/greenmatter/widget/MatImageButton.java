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
package com.negusoft.greenmatter.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;

/** Extends the button to add the "color" property. */
public class MatImageButton extends ImageButton {

    private static final float DEFAULT_DISABLED_ALPHA = 0.3f;

    /** Abstraction for setting the colorState list to a drawable. */
    private interface ColorDelegate {
        public void setColorStateList(ColorStateList colorStateList);
    }

    private final Context mContext;

    public MatImageButton(Context context) {
        super(context);
        mContext = context;
        MatButton.initBackgroundColor(getBackground(), context, null);
    }

    public MatImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        MatButton.initBackgroundColor(getBackground(), context, attrs);
    }

    public MatImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        MatButton.initBackgroundColor(getBackground(), context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MatImageButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        MatButton.initBackgroundColor(getBackground(), context, attrs);
    }

    /**
     * Set the buttons background color. The disabled color is calculated using the 'disabledAlpha'
     * attribute. The method may not work if you changed the button's background, either
     * programmatically or by using a style.
     */
    public void setColor(int color) {
        MatButton.setColor(mContext, getBackground(), color);
    }

    /**
     * Set the buttons background color. The method may not work if you changed the button's
     * background, either programmatically or by using a style.
     */
    public void setColor(ColorStateList colorStateList) {
        MatButton.setColor(getBackground(), colorStateList);
    }

}
