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
package com.negusoft.greenmatter.dialog;

import android.app.ActionBar;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.negusoft.greenmatter.util.NativeResources;

/**
 * Utility class to help give the dialogs a Material style.
 */
public class DialogUtils {

    private static final String IDENTIFIER_NAME_DIVIDER = "titleDivider";
    private static final String IDENTIFIER_NAME_BUTTON_PANEL = "buttonPanel";

    public static void prepareDialog(Context context, Window window) {
        removeDivider(window);
        arrangeButtons(context, window);
    }

    /** Change the button order and alignment. */
    public static void removeDivider(Window window) {
        int id = NativeResources.getIdentifier(IDENTIFIER_NAME_DIVIDER);
        View divider = window.findViewById(id);
        if (divider != null)
            divider.setVisibility(View.GONE);
    }

    /** Remove the divider under the title */
    public static void arrangeButtons(Context context, Window window) {
        LinearLayout panel = findButtonPanel(window);
        if (panel == null)
            return;

        // Align all buttons to the end
        panel.setHorizontalGravity(Gravity.END);
        int childCount = panel.getChildCount();
        for (int i=0; i<childCount; i++)
            removeLayoutWeight(panel.getChildAt(i));

        // Align the neutral button to the start
        if (childCount >= 3) {
            // Put it in first position
            View neutral = panel.getChildAt(1);
            panel.removeView(neutral);
            panel.addView(neutral, 0);

            // Add a spacer view to fill the width
            View space = new View(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, 0, 1f
            );
            panel.addView(space, 1, params);
        }
    }

    private static LinearLayout findButtonPanel(Window window) {
        int panelId = NativeResources.getIdentifier(IDENTIFIER_NAME_BUTTON_PANEL);
        View panelView = window.findViewById(panelId);
        if (panelView == null)
            return null;
        if (!(panelView instanceof LinearLayout))
            return null;

        View innerPanel = ((LinearLayout)panelView).getChildAt(0);
        if (innerPanel == null)
            return null;
        if (!(innerPanel instanceof LinearLayout))
            return null;

        return (LinearLayout)innerPanel;
    }

    private static void removeLayoutWeight(View view) {
        if (view == null)
            return;
        if (!(view.getLayoutParams() instanceof LinearLayout.LayoutParams))
            return;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();
        params.weight = 0;
    }
}
