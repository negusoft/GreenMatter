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
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.util.NativeResources;

public class OverScrollDrawableIterceptorProvider {

    private static final String RESOURCE_NAME_EDGE = "overscroll_edge";
    private static final String RESOURCE_NAME_GLOW = "overscroll_glow";

    public static void setupInterceptors(DrawableInterceptorHelper helper) {
        int edgeId = NativeResources.getDrawableIdentifier(RESOURCE_NAME_EDGE);
        int glowId = NativeResources.getDrawableIdentifier(RESOURCE_NAME_GLOW);
        helper.putInterceptor(edgeId, new TintedDrawableIterceptor());
        helper.putInterceptor(glowId, new TintedDrawableIterceptor());
    }

    private static class TintedDrawableIterceptor implements DrawableInterceptor {

        @Override
        public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
            Drawable drawable =  ((MatResources)res).getOriginalDrawable(resId);
            drawable.setColorFilter(palette.getColorAccent(), PorterDuff.Mode.SRC_IN);
            return drawable;
        }
    }

}
