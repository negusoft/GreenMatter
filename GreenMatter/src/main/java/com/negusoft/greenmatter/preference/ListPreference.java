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

package com.negusoft.greenmatter.preference;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.negusoft.greenmatter.MatHelper;
import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;
import com.negusoft.greenmatter.activity.MatActivity;
import com.negusoft.greenmatter.dialog.DialogUtils;
import com.negusoft.greenmatter.interceptor.drawable.DrawableInterceptor;

public class ListPreference extends android.preference.ListPreference {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(initContext(context), attrs, defStyleAttr, defStyleRes);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(initContext(context), attrs, defStyleAttr);
    }

    public ListPreference(Context context, AttributeSet attrs) {
        super(initContext(context), attrs);
    }

    public ListPreference(Context context) {
        super(initContext(context));
    }

    private static Context initContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context;
        return new CustomContextWrapper(context);
    }

    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);
        Window w = getDialog().getWindow();
        DialogUtils.prepareDialog(getContext(), w);
    }

    private static class CustomContextWrapper extends ContextWrapper {

        private final MatHelper mMatHelper = new MatHelper(
                new MatResources.PaletteOverrider() {
                    @Override
                    public MatPalette getPalette(MatPalette palette) {
                        Context baseContext = getBaseContext();
                        if (baseContext instanceof MatActivity)
                            return ((MatActivity)baseContext).overridePalette(palette);
                        return null;
                    }
                },
                new MatHelper.OnInitListener() {
                    @Override
                    public void onInitResources(MatResources resources) {
                        resources.putDrawableInterceptor(R.drawable.gm__btn_radio_reference, new DrawableInterceptor() {
                            @Override
                            public Drawable getDrawable(Resources res, MatPalette palette, int resId) {
                                return res.getDrawable(R.drawable.gm__btn_radio_simple);
                            }
                        });
                    }
                }
        );

        public CustomContextWrapper(Context base) {
            super(base);
        }

        @Override
        public Resources getResources() {
            return mMatHelper.getResources(this, super.getResources());
        }
    }
}
