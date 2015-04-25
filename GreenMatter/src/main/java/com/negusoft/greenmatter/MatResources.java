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
package com.negusoft.greenmatter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.negusoft.greenmatter.interceptor.color.ColorInterceptor;
import com.negusoft.greenmatter.interceptor.color.ColorInterceptorHelper;
import com.negusoft.greenmatter.interceptor.drawable.DrawableInterceptor;
import com.negusoft.greenmatter.interceptor.drawable.DrawableInterceptorHelper;
import com.negusoft.greenmatter.interceptor.view.ViewInterceptor;
import com.negusoft.greenmatter.interceptor.view.ViewInterceptorHelper;
import com.negusoft.greenmatter.util.BitmapUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Extends the default android Resources to replace and modify 
 * drawables at runtime and apply the appropriate colors.
 * <br/><br/>
 * "openRawResource()" and "getDrawable()" are called when inflating 
 * XML drawable resources. By overriding them, we can replace the 
 * components that form the drawables at runtime.
 * <br/><br/>
 * For the OverScroll, the native android drawables are modified 
 * directly. We look up their id by name, and then we replace the 
 * drawable with a tinted version by applying a ColorFilter.
 */
public class MatResources extends Resources {

    public interface PaletteOverrider {
        /** @return The color to be replaced or 0 to continue the normal flow. */
        public MatPalette getPalette(MatPalette palette);
    }

	private static final int[] TINT_DRAWABLE_IDS = new int[] {
	};

	private final Context mContext;
    private PaletteOverrider mOverrider;

    private List<Integer> mCustomTintDrawableIds;
    private int[] mTintDrawableIds;

	private boolean mInitialized = false;
    private boolean mInitializingFlag = false;
	private MatPalette mPalette;

    private final DrawableInterceptorHelper mDrawableInterceptorHelper;
    private final ColorInterceptorHelper mColorInterceptorHelper;
    private final ViewInterceptorHelper mViewInterceptorHelper = new ViewInterceptorHelper();

	public MatResources(Context c, Resources resources) {
		super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
		mContext = c;
        mOverrider = null;
        mDrawableInterceptorHelper = new DrawableInterceptorHelper(c);
        mColorInterceptorHelper = new ColorInterceptorHelper(c);
	}

	public MatResources(Context c, Resources resources, PaletteOverrider palletteOverrider) {
		super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
		mContext = c;
        mOverrider = palletteOverrider;
        mDrawableInterceptorHelper = new DrawableInterceptorHelper(c);
        mColorInterceptorHelper = new ColorInterceptorHelper(c);
	}

	/**
	 * Make sure that the instance is initialized. It will check the 'mInitialized'
	 * flag even if it is done within 'initialize()', to avoid getting into the
	 * synchronized block every time.
     * @return True if we are initializing. This means that initialize has been called recursively,
     * in which case a default value must be returned to avoid a "stack overflow".
	 */
	private boolean checkInitialized() {
		if (mInitialized)
			return false;
		return initialize(mContext, mOverrider);
	}

	private synchronized boolean initialize(Context c, PaletteOverrider overrider) {
		if (mInitialized)
			return false;
        if (mInitializingFlag)
            return true;

        mInitializingFlag = true;
		mPalette = initPalette(c, overrider);
        mTintDrawableIds = appendDrawableIds(TINT_DRAWABLE_IDS, mCustomTintDrawableIds);
        mInitializingFlag = false;
		mInitialized = true;

        return false;
	}

    private int[] appendDrawableIds(int[] defaults, List<Integer> custom) {
        if (custom == null)
            return defaults;

        int customSize = custom.size();
        int[] result = new int[defaults.length + customSize];
        for (int i=0; i<customSize; i++)
            result[i] = custom.get(i);
        for (int i=0; i<defaults.length; i++)
            result[customSize + i] = defaults[i];

        return result;
    }

	private MatPalette initPalette(Context c, PaletteOverrider overrider) {
		MatPalette result = MatPalette.createFromTheme(c);
        return (overrider != null) ? overrider.getPalette(result) : result;
	}

    @Override
    public int getColor(int resId) throws NotFoundException {
        if (checkInitialized())
            return super.getColor(resId);

        // Give a chance to the interceptors to replace the drawable
        int overrideColor = mColorInterceptorHelper.getOverrideColor(this, mPalette, resId);
        return overrideColor != 0 ? overrideColor : super.getColor(resId);
    }

    @Override
    public Drawable getDrawable(int resId) throws NotFoundException {
        Drawable overrideDrawable = getOverrideDrawable(resId);
        if (overrideDrawable != null)
            return overrideDrawable;
        return super.getDrawable(resId);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public Drawable getDrawableForDensity(int resId, int density) throws NotFoundException {
        Drawable overrideDrawable = getOverrideDrawable(resId);
        if (overrideDrawable != null)
            return overrideDrawable;
        return super.getDrawableForDensity(resId, density);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Drawable getDrawable(int resId, Theme theme) throws NotFoundException {
        Drawable overrideDrawable = getOverrideDrawable(resId);
        if (overrideDrawable != null)
            return overrideDrawable;
        return super.getDrawable(resId, theme);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Drawable getDrawableForDensity(int resId, int density, Theme theme) {
        Drawable overrideDrawable = getOverrideDrawable(resId);
        if (overrideDrawable != null)
            return overrideDrawable;
        return super.getDrawableForDensity(resId, density, theme);
    }

    private Drawable getOverrideDrawable(int resId) {
        if (checkInitialized())
            return null;

        // Give a chance to the interceptors to replace the drawable
        return mDrawableInterceptorHelper.getOverrideDrawable(this, mPalette, resId);
    }

    /** Return the drawable without letting the interceptors intervene. **/
    public Drawable getOriginalDrawable(int resId) {
        return super.getDrawable(resId);
    }
	
	@Override
	public InputStream openRawResource(int resId, TypedValue value)
			throws NotFoundException {
        if (checkInitialized())
            return super.openRawResource(resId, value);
		
		for (int id : mTintDrawableIds) {
			if (resId == id)
				return getTintendResourceStream(resId, value, mPalette.getColorAccent());
		}
		return super.openRawResource(resId, value);
	}

    /**
     * Method to access the palette instance
     */
    public MatPalette getPalette() {
        if (checkInitialized())
            throw new RuntimeException("GreenMatter: Unexpected exception in initialization.");
        return mPalette;
    }

    /**
     * Set a drawable interceptor for a given resource ID. If the ID already exits, the corresponding
     * interceptor will be replaced.
     */
    public void putDrawableInterceptor(int resId, DrawableInterceptor interceptor) {
        mDrawableInterceptorHelper.putInterceptor(resId, interceptor);
    }

    /** Remove the drawable interceptor that corresponds to the given ID. */
    public void removeDrawableInterceptor(int resId) {
        mDrawableInterceptorHelper.removeInterceptor(resId);
    }

    /**
     * Add a color interceptor for a given resource ID. If the ID already exits, the corresponding
     * interceptor will be replaced.
     */
    public void putColorInterceptor(int resId, ColorInterceptor interceptor) {
        mColorInterceptorHelper.putInterceptor(resId, interceptor);
    }

    /** Remove the color interceptor that corresponds to the given ID. */
    public void removeColorInterceptor(int resId) {
        mColorInterceptorHelper.removeInterceptor(resId);
    }

    /** Add a ViewInterceptor for the given view name. */
    public void putViewInterceptor(String viewName, ViewInterceptor interceptor) {
        mViewInterceptorHelper.putInterceptor(viewName, interceptor);
    }

    /** Remove the ViewInterceptor that corresponds to the given view name. */
    public void removeViewInterceptor(String viewName) {
        mViewInterceptorHelper.removeInterceptor(viewName);
    }

    /** Add a drawable resource to which to apply the "tint" technique. */
    public void addTintResourceId(int resId) {
        if (mCustomTintDrawableIds == null)
            mCustomTintDrawableIds = new ArrayList<Integer>();
        mCustomTintDrawableIds.add(resId);
    }

    /**
     * If there is an interceptor for the given view name, it returns the result of the interceptor's
     * createView() method. It returns null otherwise.
     */
    public View createView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return mViewInterceptorHelper.createView(name, context, attrs);
    }
	
	/**
	 * Get a reference to a resource that is equivalent to the one requested, 
	 * but with the accent color applied to it.
	 */
	private InputStream getTintendResourceStream(int id, TypedValue value, int color) {
		Bitmap bitmap = getBitmapFromResource(id, value);
		bitmap = BitmapUtils.applyColor(bitmap, color);
		return getStreamFromBitmap(bitmap);
	}
	
	private Bitmap getBitmapFromResource(int resId, TypedValue value) {
		InputStream original = super.openRawResource(resId, value);
		value.density = getDisplayMetrics().densityDpi;
		final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inScaled = false;
        options.inScreenDensity = getDisplayMetrics().densityDpi;
		return BitmapFactory.decodeResourceStream(
				this, value, original, 
				new Rect(), options);
	}
	
	private InputStream getStreamFromBitmap(Bitmap bitmap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
		byte[] bitmapData = bos.toByteArray();
		try {
			bos.close();
		} catch (IOException e) { /* ignore */}
		
		return new ByteArrayInputStream(bitmapData);
	}

}
