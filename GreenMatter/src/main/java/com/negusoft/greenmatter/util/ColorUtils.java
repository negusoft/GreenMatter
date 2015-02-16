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

package com.negusoft.greenmatter.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ColorUtils {


    /** Modify the colors translucency by alpha [0..1] with respect to the original color alpha. */
    public static int applyColorAlpha(int color, float alpha) {
        final int originalAlpha = Color.alpha(color);
        // Return the color, multiplying the original alpha by the disabled value
        return (color & 0x00ffffff) | (Math.round(originalAlpha * alpha) << 24);
    }

    /** Calculate the resulting alpha from the original color and the relative alpha. */
    public static int calculateAlpha(int alpha, int originalColor) {
        int originalAlpha = Color.alpha(originalColor);
        return originalAlpha * alpha / 255;
    }


}
