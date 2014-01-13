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
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;
import com.negusoft.greenmatter.drawable.CircleDrawable;

public class CircleInterceptor implements MatResources.Interceptor {
	
	@Override
	public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
		if (resId == R.drawable.gm__circle_pressed_reference) {
			int backColor = palette.getColorControlHighlight(0x88);
			return new CircleDrawable(res, 16f, backColor, 0f, Color.TRANSPARENT);
		}
		if (resId == R.drawable.gm__circle_focused_reference) {
            int borderColor = palette.getColorControlHighlight(0x88);
            return new CircleDrawable(res, 15f, Color.TRANSPARENT, 2f, borderColor);
		}
		return null;
	}

}
