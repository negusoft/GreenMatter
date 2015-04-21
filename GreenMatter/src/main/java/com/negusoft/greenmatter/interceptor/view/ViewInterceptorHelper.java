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
package com.negusoft.greenmatter.interceptor.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.activity.MatActivity;
import com.negusoft.greenmatter.widget.MatButton;
import com.negusoft.greenmatter.widget.MatImageButton;
import com.negusoft.greenmatter.widget.MatNumberPicker;
import com.negusoft.greenmatter.widget.MatRatingBar;
import com.negusoft.greenmatter.widget.MatSeekBar;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Holds a dictionary that contains ViewInterceptors. It sets up the default interceptors.
 */
public class ViewInterceptorHelper {

    private final Dictionary<String, ViewInterceptor> mViewInterceptors;

    public ViewInterceptorHelper() {
        mViewInterceptors = new Hashtable<>();
        addInterceptor(mButtonProvider);
        addInterceptor(mImageButtonProvider);
        addInterceptor(mNumberPickerProvider);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            addInterceptor(mSeekBarProvider);
        }
    }

    public void addInterceptor(ViewInterceptor interceptor) {
        for (String name : interceptor.getViewNames()) {
            mViewInterceptors.put(name, interceptor);
        }
    }

    public void removeInterceptor(ViewInterceptor interceptor) {
        for (String name : interceptor.getViewNames()) {
            mViewInterceptors.remove(name);
        }
    }

    public void removeInterceptor(String name) {
        mViewInterceptors.remove(name);
    }

    /**
     * If there is a interceptor for the given view name, it returns the result of the interceptor's
     * createView() method. It returns null otherwise.
     */
    public View createView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        ViewInterceptor interceptor = mViewInterceptors.get(name);
        return interceptor != null ? interceptor.createView(name, context, attrs) : null;
    }

    /** A view interceptor to swap Button by MatButton. */
    private final ViewInterceptor mButtonProvider = new ViewInterceptor() {
        @Override
        public String[] getViewNames() {
            return new String[] { "Button" };
        }
        @Override
        public View createView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {
            return new MatButton(context, attrs);
        }
    };

    /** A view interceptor to swap ImageButton by MatImageButton. */
    private final ViewInterceptor mImageButtonProvider = new ViewInterceptor() {
        @Override
        public String[] getViewNames() {
            return new String[] { "ImageButton" };
        }
        @Override
        public View createView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {
            return new MatImageButton(context, attrs);
        }
    };

    /** A view interceptor to swap SeekBar by MatSeekBar. */
    private final ViewInterceptor mSeekBarProvider = new ViewInterceptor() {
        @Override
        public String[] getViewNames() {
            return new String[] { "SeekBar" };
        }
        @Override
        public View createView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {
            return new MatSeekBar(context, attrs);
        }
    };

    /** A view interceptor to swap the pickers by their custom counterparts. */
    private final ViewInterceptor mNumberPickerProvider = new ViewInterceptor() {
        @Override
        public String[] getViewNames() {
            return new String[] { "NumberPicker" };
        }
        @Override
        public View createView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {
            return new MatNumberPicker(context, attrs);
        }
    };

}
