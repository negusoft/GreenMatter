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
package com.negusoft.greenmatter.drawable;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;


/**
 * A {@link DrawableWrapper} which updates it's color filter using a {@link ColorStateList}.
 */
public class TintDrawableWrapper extends DrawableWrapper {

    private static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;

    private ColorStateList mTintStateList;
    private final PorterDuff.Mode mTintMode;

    private int mCurrentColor;

    public TintDrawableWrapper(Drawable drawable, ColorStateList tintStateList) {
        this(drawable, tintStateList, DEFAULT_TINT_MODE);
    }

    public TintDrawableWrapper(Drawable drawable, ColorStateList tintStateList,
                               PorterDuff.Mode tintMode) {
        super(drawable);
        mTintStateList = tintStateList;
        mTintMode = tintMode;
    }

    @Override
    public boolean isStateful() {
        return (mTintStateList != null && mTintStateList.isStateful()) || super.isStateful();
    }

    @Override
    public boolean setState(int[] stateSet) {
        boolean handled = super.setState(stateSet);
        handled = updateTint(stateSet) || handled;
        return handled;
    }

    public ColorStateList getTintStateList() {
        return mTintStateList;
    }

    public void setTintStateList(ColorStateList tintStateList) {
        mTintStateList = tintStateList;
        updateTint(getState());
    }

    private boolean updateTint(int[] state) {
        if (mTintStateList != null) {
            final int color = mTintStateList.getColorForState(state, mCurrentColor);
            if (color != mCurrentColor) {
                if (color != Color.TRANSPARENT) {
                    setColorFilter(color, mTintMode);
                } else {
                    clearColorFilter();
                }
                mCurrentColor = color;
                return true;
            }
        }
        return false;
    }

}
