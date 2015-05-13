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
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;

import com.negusoft.greenmatter.MatHelper;
import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.MatResources;
import com.negusoft.greenmatter.R;
import com.negusoft.greenmatter.activity.MatActivity;
import com.negusoft.greenmatter.dialog.DialogUtils;
import com.negusoft.greenmatter.interceptor.drawable.DrawableInterceptor;
import com.negusoft.greenmatter.util.NativeResources;

public class CheckBoxPreference extends android.preference.CheckBoxPreference {

    public CheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public CheckBoxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckBoxPreference(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            setWidgetLayoutResource(R.layout.gm__checkbox_preference);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        View checkableView = view.findViewById(R.id.checkbox);
        if (checkableView != null && checkableView instanceof Checkable) {
            ((Checkable) checkableView).setChecked(isChecked());

            if (checkableView instanceof CompoundButton) {
                ((CompoundButton)checkableView).setOnCheckedChangeListener(new Listener());
            }
        }
    }

    private class Listener implements android.widget.CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
            if (!callChangeListener(isChecked)) {
                // Listener didn't like it, change it back.
                // CompoundButton will make sure we don't recurse.
                buttonView.setChecked(!isChecked);
                return;
            }

            CheckBoxPreference.this.setChecked(isChecked);
        }
    }
}
