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
package com.negusoft.greenmatter.interceptor;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;

public class SolidInterceptor implements MatResources.Interceptor {

//	private static final int PRESSED_ALPHA = 0xAA;
//	private static final int FOCUSED_ALPHA = 0x55;

	@Override
	public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
        if (resId == R.drawable.gm__solid_primary_reference || resId == R.color.gm__primary)
            return new ColorDrawable(palette.colorPrimary);
        if (resId == R.drawable.gm__solid_primary_dark_reference || resId == R.color.gm__primary_dark)
            return new ColorDrawable(palette.colorPrimaryDark);
        if (resId == R.drawable.gm__solid_accent_reference || resId == R.color.gm__accent)
            return new ColorDrawable(palette.colorAccent);
		return null;
	}

}
