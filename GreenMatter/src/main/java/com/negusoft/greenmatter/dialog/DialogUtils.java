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
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.negusoft.greenmatter.R;
import com.negusoft.greenmatter.util.NativeResources;

import org.w3c.dom.Text;

/**
 * Utility class to help give the dialogs a Material style.
 */
public class DialogUtils {

    private static final String IDENTIFIER_NAME_DIVIDER = "titleDivider";
    private static final String IDENTIFIER_NAME_BUTTON_PANEL = "buttonPanel";

    public static void prepareDialog(Context context, Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return;

        removeDivider(window);
        arrangeButtons(context, window);
        arrangeContentSpacing(context, window);
        prepareList(context, window);
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

        // Align the buttons by setting the horizontal padding
        int paddingHorizontal = context.getResources().getDimensionPixelSize(R.dimen.gm__dialog_buttons_padding_horizontal);
        panel.setPadding(paddingHorizontal, panel.getPaddingTop(), paddingHorizontal, panel.getPaddingBottom());

        // Align all buttons to the end
        panel.setHorizontalGravity(Gravity.END);
        int childCount = panel.getChildCount();
        for (int i=0; i<childCount; i++)
            removeLayoutWeight(panel.getChildAt(i));

        // Align the neutral button to the start
        View buttonNeutral = window.findViewById(NativeResources.getIdentifier("button3"));
        if (buttonNeutral != null) {
            panel.removeView(buttonNeutral);
            panel.addView(buttonNeutral, 0);

            // Add a spacer view to fill the width
            View space = new View(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, 0, 1f
            );
            panel.addView(space, 1, params);
        }
    }

    /** Remove the divider under the title */
    public static void arrangeContentSpacing(Context context, Window window) {
        int minHeight = context.getResources().getDimensionPixelSize(R.dimen.gm__dialog_content_min_height);

        // Content panel
        View content = window.findViewById(NativeResources.getIdentifier("contentPanel"));
        if (content != null) {
            content.setMinimumHeight(minHeight);
        }

        // Custom panel
        View custom = window.findViewById(NativeResources.getIdentifier("customPanel"));
        if (custom != null) {
            int topPadding = context.getResources().getDimensionPixelSize(R.dimen.abc_dialog_padding_top_material);
            int horizontalPadding = context.getResources().getDimensionPixelSize(R.dimen.gm__dialog_bottom_padding);
            custom.setPadding(horizontalPadding, topPadding, horizontalPadding, custom.getPaddingLeft());
            custom.setMinimumHeight(minHeight);
        }

        // Title panel
        View title = window.findViewById(NativeResources.getIdentifier("title_template"));
        if (title != null) {
            int topPadding = context.getResources().getDimensionPixelSize(R.dimen.abc_dialog_padding_top_material);
            title.setPadding(title.getPaddingLeft(), topPadding, title.getPaddingRight(), title.getPaddingLeft());
            title.setMinimumHeight(0);
        }

        // Message TextView
        View message = window.findViewById(NativeResources.getIdentifier("message"));
        if (message != null && message instanceof TextView) {
            // Text color
            TypedArray attrs = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.textColorPrimary});
            ColorStateList textColor = attrs.getColorStateList(0);
            ((TextView)message).setTextColor(textColor);
            attrs.recycle();

            // Text size
            int textSize = context.getResources().getDimensionPixelSize(R.dimen.abc_text_size_subhead_material);
            ((TextView)message).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

            // Padding top
            int paddingTop = context.getResources().getDimensionPixelSize(R.dimen.abc_dialog_padding_top_material);
            ((TextView)message).setPadding(message.getPaddingLeft(), paddingTop, message.getPaddingRight(), message.getPaddingBottom());
        }
    }

    /** Remove the ListView divider */
    public static void prepareList(Context context, Window window) {
        // Custom panel
        View content = window.findViewById(NativeResources.getIdentifier("contentPanel"));
        if (content != null && content instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)content;
            if (viewGroup.getChildCount() > 0) {
                View firstView = viewGroup.getChildAt(0);
                if (firstView instanceof ListView)
                    ((ListView)firstView).setDividerHeight(0);
            }
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
        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
