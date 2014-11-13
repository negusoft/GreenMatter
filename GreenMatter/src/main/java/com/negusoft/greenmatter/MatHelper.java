/*******************************************************************************
 * Copyright 2013 NEGU Soft
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
/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.negusoft.greenmatter;

import android.content.Context;
import android.content.res.Resources;
import android.view.Window;

import com.negusoft.greenmatter.dialog.DividerPainter;

/**
 * Helper class to lazily initialize MatResources from your activities.
 * Simply add the following code to your activity in order to replace the 
 * default android Resources implementation by MatResources:
 * <pre><code>
private final MatHelper mMatHelper = new MatHelper();
{@literal @}Override
public Resources getResources() {
	return mMatHelper.getResources(this, super.getResources());
}
 * </code></pre>
 * And don't forget to add the corresponding imports:
 * <pre><code>
import com.negusoft.greenmatter.MatHelper;
import android.content.res.Resources;
 * </code></pre>
 * 
 * In addition, it allows to easily prepare dialogs for display by calling: 
 * prepareDialog().
 */
public class MatHelper {

    public interface OnInitListener {
        public void onInitResources(MatResources resources);
    }

    public static final int COLOR_NO_OVERRIDE = 0;

    /**
     * Get the MatPalette instance from the the context.
     * @return It might return null if GreenMatter has not been correctly configured.
     */
    public static MatPalette getPalette(Context context) {
        Resources resources = context.getResources();
        if (!(resources instanceof MatResources))
            return null;
        return ((MatResources)resources).getPalette();
    }

	private MatResources mMatResources;
	private DividerPainter mDividerPainter;

	private final boolean mOverrideThemeColor;
    private final int mOverridePrimaryColor;
    private final int mOverridePrimaryColorDark;
    private final int mOverrideAccentColor;

    private OnInitListener mInitListener;

	public MatHelper() {
		mOverrideThemeColor = false;
		mOverridePrimaryColor = 0;
        mOverridePrimaryColorDark = 0;
        mOverrideAccentColor = 0;
	}

    /**
     * Initialize by specifying a explicit color.
     * @param color The color to override. If it is 0, it will not override the color so it
     *              will be taken from the theme.
     * @param colorDark The dark version to override. If it is 0, it be taken from the theme.
     *                  Or it will be calculated from the main color if it not specified in
     *                  the theme either.
     * @param colorActionBar The action bar version to override. If it is 0, it will be taken
     *                       from the theme. Or same as the main color if it not specified in
     *                       the theme either.
     */
    public MatHelper(int color, int colorDark, int colorActionBar) {
        this(color, colorDark, colorActionBar, null);
    }

    /**
     * Initialize by specifying a explicit color.
     * @param color The color to override. If it is 0, it will not override the color so it
     *              will be taken from the theme.
     * @param colorDark The dark version to override. If it is 0, it be taken from the theme.
     *                  Or it will be calculated from the main color if it not specified in
     *                  the theme either.
     * @param colorActionBar The action bar version to override. If it is 0, it will be taken
     *                       from the theme. Or same as the main color if it not specified in
     *                       the theme either.
     * @param listener Listener to receive the init event.
     */
    public MatHelper(int color, int colorDark, int colorActionBar, OnInitListener listener) {
        mOverrideThemeColor =  color != COLOR_NO_OVERRIDE;
        mOverridePrimaryColor = color;
        mOverridePrimaryColorDark = colorDark;
        mOverrideAccentColor = colorActionBar;
        mInitListener = listener;
    }
	
	/** @return The MatResources instance, properly initialized. */
	public Resources getResources(Context c, Resources resources) {
		if (mMatResources == null) {
            mMatResources = createInstance(c, resources);
            if (mInitListener != null)
                mInitListener.onInitResources(mMatResources);
        }
		return mMatResources;
	}
	
	/** Paint the dialog's divider if required to correctly customize it. */
	public void prepareDialog(Context c, Window window) {
		if (mDividerPainter == null)
			mDividerPainter = initPainter(c, mOverridePrimaryColor);
		mDividerPainter.paint(window);
	}

    /**
     * Set a listener to be notified when the instance of MatResources is created.
     * @param listener The actual listener or null to disable any event reporting.
     */
    public void setOnInitListener(OnInitListener listener) {
        mInitListener = listener;
    }

    private DividerPainter initPainter(Context c, int color) {
        return color == 0 ? new DividerPainter(c) : new DividerPainter(color);
    }
	
	private MatResources createInstance(Context c, Resources resources) {
        return new MatResources(c, resources, mOverridePrimaryColor, mOverridePrimaryColorDark, mOverrideAccentColor);
	}

}
