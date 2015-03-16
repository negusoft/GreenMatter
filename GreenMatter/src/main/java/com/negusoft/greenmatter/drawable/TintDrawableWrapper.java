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

    private final TintDrawableWrapperConstantState mState;

    private int mCurrentColor;

    public TintDrawableWrapper(Drawable drawable, ColorStateList tintStateList) {
        this(drawable, tintStateList, DEFAULT_TINT_MODE);
    }

    public TintDrawableWrapper(Drawable drawable, ColorStateList tintStateList,
                               PorterDuff.Mode tintMode) {
        super(drawable);
        mState = new TintDrawableWrapperConstantState(drawable, tintStateList, tintMode);
    }

    TintDrawableWrapper(TintDrawableWrapperConstantState state) {
        super(state.mInnerConstantState.newDrawable());
        mState = state;
    }

    @Override
    public boolean isStateful() {
        return (mState.mTintStateList != null && mState.mTintStateList.isStateful()) || super.isStateful();
    }

    @Override
    public boolean setState(int[] stateSet) {
        boolean handled = super.setState(stateSet);
        handled = updateTint(stateSet) || handled;
        return handled;
    }

    @Override
    public final ConstantState getConstantState() {
        mState.changingConfigurationValue = super.getChangingConfigurations();
        return mState;
    }

    public ColorStateList getTintStateList() {
        return mState.mTintStateList;
    }

    public void setTintStateList(ColorStateList tintStateList) {
        mState.mTintStateList = tintStateList;
        updateTint(getState());
    }

    private boolean updateTint(int[] state) {
        if (mState.mTintStateList != null) {
            final int color = mState.mTintStateList.getColorForState(state, mCurrentColor);
            if (color != mCurrentColor) {
                if (color != Color.TRANSPARENT) {
                    setColorFilter(color, mState.mTintMode);
                } else {
                    clearColorFilter();
                }
                mCurrentColor = color;
                return true;
            }
        }
        return false;
    }

    private class TintDrawableWrapperConstantState extends ConstantState {

        private ColorStateList mTintStateList;
        private final PorterDuff.Mode mTintMode;
        private final ConstantState mInnerConstantState;

        int changingConfigurationValue;

        TintDrawableWrapperConstantState(Drawable innerDrawable, ColorStateList tintStateList, PorterDuff.Mode tintMode) {
            mTintStateList = tintStateList;
            mTintMode = tintMode;
            mInnerConstantState = innerDrawable.getConstantState();
        }

        @Override
        public Drawable newDrawable() {
            return new TintDrawableWrapper(this);
        }

        @Override
        public int getChangingConfigurations() {
            return changingConfigurationValue;
        }
    }

}
