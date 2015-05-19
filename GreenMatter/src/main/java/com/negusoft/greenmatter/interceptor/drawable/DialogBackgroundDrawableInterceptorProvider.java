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
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;
import com.negusoft.greenmatter.drawable.CircleFillDrawable;
import com.negusoft.greenmatter.util.NativeResources;

/**
 * Removes the dialog background by returning a dummy drawable in stead of the default drawables.
 */
public class DialogBackgroundDrawableInterceptorProvider {

    private static final String[] RESOURCE_NAMES = new String[] {
            "dialog_full_holo_dark",
            "dialog_top_holo_dark",
            "dialog_middle_holo_dark",
            "dialog_bottom_holo_dark",
            "dialog_full_holo_light",
            "dialog_top_holo_light",
            "dialog_middle_holo_light",
            "dialog_bottom_holo_light"
    };

    public static void setupInterceptors(DrawableInterceptorHelper helper) {
        DrawableInterceptor dummyInterceptor = new DrawableInterceptor() {
            @Override
            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                return new ColorDrawable(0);
            }
        };

        int length = RESOURCE_NAMES.length;
        for (int i=0; i<length; i++) {
            int resId = NativeResources.getDrawableIdentifier(RESOURCE_NAMES[i]);
            helper.putInterceptor(resId, dummyInterceptor);
        }
    }
}