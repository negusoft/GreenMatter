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
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.negusoft.greenmatter.interceptor.drawable.CircleDrawableInterceptor;
import com.negusoft.greenmatter.interceptor.color.MatColorInterceptor;
import com.negusoft.greenmatter.interceptor.drawable.DialogBackgroundDrawableInterceptor;
import com.negusoft.greenmatter.interceptor.drawable.ScrollbarInterceptor;
import com.negusoft.greenmatter.interceptor.drawable.ScrubberHorizontalInterceptor;
import com.negusoft.greenmatter.interceptor.drawable.ProgressInterceptor;
import com.negusoft.greenmatter.interceptor.drawable.RoundRectDrawableInterceptor;
import com.negusoft.greenmatter.interceptor.drawable.SolidDrawableInterceptor;
import com.negusoft.greenmatter.interceptor.drawable.TintDrawableDrawableInterceptor;
import com.negusoft.greenmatter.interceptor.drawable.UnderlineDrawableInterceptor;
import com.negusoft.greenmatter.interceptor.view.ViewInterceptor;
import com.negusoft.greenmatter.interceptor.view.ViewInterceptorHelper;
import com.negusoft.greenmatter.util.BitmapUtils;
import com.negusoft.greenmatter.util.NativeResources;

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

	public interface DrawableInterceptor {
		/** @return The drawable to be replaced or null to continue the normal flow. */
		public Drawable getDrawable(Resources res, MatPalette palette, int resId);
	}

    public interface ColorInterceptor {
        /** @return The color to be replaced or 0 to continue the normal flow. */
        public int getColor(Resources res, MatPalette palette, int resId);
    }

    public interface PaletteOverrider {
        /** @return The color to be replaced or 0 to continue the normal flow. */
        public MatPalette getPalette(MatPalette palette);
    }

	private static final int[] TINT_DRAWABLE_IDS = new int[] {
	};

	private final Context mContext;
    private PaletteOverrider mOverrider;

    private final List<DrawableInterceptor> mDrawableInterceptors = new ArrayList<DrawableInterceptor>();
    private final List<ColorInterceptor> mColorInterceptors = new ArrayList<ColorInterceptor>();

    private List<Integer> mCustomTintDrawableIds;
    private int[] mTintDrawableIds;

	private boolean mInitialized = false;
    private boolean mInitializingFlag = false;
	private MatPalette mPalette;

    private final ViewInterceptorHelper mViewInterceptorHelper = new ViewInterceptorHelper();

	public MatResources(Context c, Resources resources) {
		super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
		mContext = c;
        mOverrider = null;
	}

	public MatResources(Context c, Resources resources, PaletteOverrider palletteOverrider) {
		super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
		mContext = c;
        mOverrider = palletteOverrider;
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
		addDrawableInterceptors(c);
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

	private void addDrawableInterceptors(Context c) {
        mDrawableInterceptors.add(new SolidDrawableInterceptor(c));
        mDrawableInterceptors.add(new TintDrawableDrawableInterceptor());
        mDrawableInterceptors.add(new CircleDrawableInterceptor());
        mDrawableInterceptors.add(new OverScrollDrawableInterceptor());
        mDrawableInterceptors.add(new RoundRectDrawableInterceptor());
        mDrawableInterceptors.add(new UnderlineDrawableInterceptor());
        mDrawableInterceptors.add(new ProgressInterceptor());
        mDrawableInterceptors.add(new ScrubberHorizontalInterceptor());
        mDrawableInterceptors.add(new ScrollbarInterceptor());
        mDrawableInterceptors.add(new DialogBackgroundDrawableInterceptor());

        mColorInterceptors.add(new MatColorInterceptor(c));
	}

    @Override
    public int getColor(int resId) throws NotFoundException {
        if (checkInitialized())
            return super.getColor(resId);

        // Give a chance to the interceptors to replace the drawable
        int result;
        for(ColorInterceptor interceptor : mColorInterceptors) {
            result = interceptor.getColor(this, mPalette, resId);
            if (result != 0)
                return result;
        }

        return super.getColor(resId);
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
        Drawable result;
        for(DrawableInterceptor interceptor : mDrawableInterceptors) {
            result = interceptor.getDrawable(this, mPalette, resId);
            if (result != null)
                return result;
        }
        return null;
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
     * Add a drawable interceptor. They are evaluated in the order they are added, and before the
     * default interceptors.
     */
    public void addDrawableInterceptor(DrawableInterceptor interceptor) {
        mDrawableInterceptors.add(0, interceptor);
    }

    /**
     * Add a color interceptor. They are evaluated in the order they are added, and before the
     * default interceptors.
     */
    public void addColorInterceptor(ColorInterceptor interceptor) {
        mColorInterceptors.add(0, interceptor);
    }

    /** Add the given ViewInterceptor. */
    public void addViewInterceptor(ViewInterceptor interceptor) {
        mViewInterceptorHelper.addInterceptor(interceptor);
    }

    /** Remove the given ViewInterceptor. */
    public void removeViewInterceptor(ViewInterceptor interceptor) {
        mViewInterceptorHelper.removeInterceptor(interceptor);
    }

    /** Remove the ViewInterceptor for the given name. */
    public void removeViewInterceptor(String name) {
        mViewInterceptorHelper.removeInterceptor(name);
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
	
	/**
	 * Inner class holding the logic for applying a ColorFilter to the OverScroll 
	 * drawables. It is implemented a an inner class because it needs to access 
	 * the parents implementation of getDrawable().
	 */
	private class OverScrollDrawableInterceptor implements DrawableInterceptor {

		private static final String RESOURCE_NAME_EDGE = "overscroll_edge";
		private static final String RESOURCE_NAME_GLOW = "overscroll_glow";

		private final int mOverscrollEdgeId;
		private final int mOverscrollGlowId;

        private Drawable mOverscrollEdgeDrawable;
        private Drawable mOverscrollGlowDrawable;
		
		public OverScrollDrawableInterceptor() {
            mOverscrollEdgeId = NativeResources.getDrawableIdentifier(RESOURCE_NAME_EDGE);
            mOverscrollGlowId = NativeResources.getDrawableIdentifier(RESOURCE_NAME_GLOW);
		}

		@Override
		public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
			if (resId == mOverscrollEdgeId)
				return getEdgeDrawable();
			if (resId == mOverscrollGlowId)
				return getGlowDrawable();
			return null;
		}
		
		private Drawable getEdgeDrawable() {
            if (mOverscrollEdgeDrawable == null) {
                mOverscrollEdgeDrawable = MatResources.super.getDrawable(R.drawable.gm__overscroll_edge);
                mOverscrollEdgeDrawable.setColorFilter(mPalette.getColorAccent(), PorterDuff.Mode.MULTIPLY);
            }
			return mOverscrollEdgeDrawable;
		}
		
		private Drawable getGlowDrawable() {
            if (mOverscrollGlowDrawable == null) {
                mOverscrollGlowDrawable = MatResources.super.getDrawable(R.drawable.gm__overscroll_glow);
                mOverscrollGlowDrawable.setColorFilter(mPalette.getColorAccent(), PorterDuff.Mode.MULTIPLY);
            }
			return mOverscrollGlowDrawable;
		}
	}

}
