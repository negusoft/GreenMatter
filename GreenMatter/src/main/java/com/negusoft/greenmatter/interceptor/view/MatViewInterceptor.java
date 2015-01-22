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
package com.negusoft.greenmatter.interceptor.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.negusoft.greenmatter.activity.MatActivity;
import com.negusoft.greenmatter.widget.MatButton;
import com.negusoft.greenmatter.widget.MatImageButton;

public class MatViewInterceptor implements MatActivity.ViewInterceptor {

    @Override
    public View createView(String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        switch (name) {
            case "Button": return new MatButton(context, attrs);
            case "ImageButton": return new MatImageButton(context, attrs);
            default: return null;
        }
    }

}
