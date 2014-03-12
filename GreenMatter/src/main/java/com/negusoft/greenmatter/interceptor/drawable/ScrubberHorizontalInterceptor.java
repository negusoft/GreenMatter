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
package com.negusoft.greenmatter.interceptor.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;
import com.negusoft.greenmatter.drawable.CompoundDrawableWrapper;
import com.negusoft.greenmatter.drawable.HorizontalLineDrawable;
import com.negusoft.greenmatter.drawable.ScrubberHorizontalDrawable;
import com.negusoft.greenmatter.drawable.ScrubberThumbDrawable;

public class ScrubberHorizontalInterceptor implements MatResources.DrawableInterceptor {
	
	@Override
	public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
        if (resId == R.drawable.gm__scrubber_background_default_reference) {
            return new HorizontalLineDrawable(res, palette.getColorControlNormal(), 1f);
        }
        if (resId == R.drawable.gm__scrubber_background_disabled_reference) {
            return new ScrubberHorizontalDrawable(res, palette.getColorControlNormal(), 1f, ScrubberHorizontalDrawable.Type.BACKGROUND);
        }
        if (resId == R.drawable.gm__scrubber_secondary_reference) {
            return new ScrubberHorizontalDrawable(res, palette.getColorControlNormal(), 3f, ScrubberHorizontalDrawable.Type.PROGRESS);
        }
		if (resId == R.drawable.gm__scrubber_progress_reference) {
            return new ScrubberHorizontalDrawable(res, palette.getColorControlActivated(), 3f, ScrubberHorizontalDrawable.Type.PROGRESS);
		}
        // Thumb
        if (resId == R.drawable.gm__scrubber_thumb_item_reference) {
            return getThumbDrawable(res, palette);
        }
        if (resId == R.drawable.gm__scrubber_thumb_default_reference) {
            return new ScrubberThumbDrawable(res, palette, ScrubberThumbDrawable.SelectorType.NORMAL);
        }
        if (resId == R.drawable.gm__scrubber_thumb_pressed_reference) {
            return new ScrubberThumbDrawable(res, palette, ScrubberThumbDrawable.SelectorType.PRESSED);
        }
        if (resId == R.drawable.gm__scrubber_thumb_disabled_reference) {
            return new ScrubberThumbDrawable(res, palette, ScrubberThumbDrawable.SelectorType.DISABLED);
        }
		return null;
	}

    private Drawable getThumbDrawable(Resources res, MatPalette palette) {
        Drawable circleDrawable = res.getDrawable(R.drawable.gm__scrubber_thumb_main);
        Drawable secondary = res.getDrawable(R.drawable.gm__circle_indicator);
        return new CompoundDrawableWrapper(circleDrawable, secondary);
    }

}
