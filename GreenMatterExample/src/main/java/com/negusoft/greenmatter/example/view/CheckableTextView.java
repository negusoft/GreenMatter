package com.negusoft.greenmatter.example.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

import com.negusoft.greenmatter.example.R;

public class CheckableTextView extends TextView implements Checkable {
	
	private static final int BACKGROUND_ALPHA = 0x88;

    private static final int[] ATTR_ACCENT = new int[] { R.attr.colorControlActivated };

    private final int mColorActivated;
	private boolean mChecked = false;

	public CheckableTextView(Context context) {
		super(context);
		int defaultColor = context.getResources().getColor(android.R.color.holo_blue_light);
		TypedArray attrs = context.getTheme().obtainStyledAttributes(ATTR_ACCENT);
        mColorActivated = attrs.getColor(0, defaultColor);
	}

	public CheckableTextView(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		int defaultColor = context.getResources().getColor(android.R.color.holo_blue_light);
		TypedArray attrs = context.getTheme().obtainStyledAttributes(ATTR_ACCENT);
        mColorActivated = attrs.getColor(0, defaultColor);
	}

	public CheckableTextView(Context context, AttributeSet attrSet, int defStyle) {
		super(context, attrSet, defStyle);
		int defaultColor = context.getResources().getColor(android.R.color.holo_blue_light);
		TypedArray attrs = context.getTheme().obtainStyledAttributes(ATTR_ACCENT);
        mColorActivated = attrs.getColor(0, defaultColor);
	}

	@Override
	public boolean isChecked() {
		return mChecked;
	}

	@Override
	public void setChecked(boolean checked) {
		mChecked = checked;
        updateBackground();
		invalidate();
	}

	@Override
	public void toggle() {
		mChecked = !mChecked;
        updateBackground();
		invalidate();
	}

    private void updateBackground() {
        Drawable background = getBackground();
        if (background == null)
            return;

        int state = mChecked ? android.R.attr.state_checked : -android.R.attr.state_checked;
        background.setState(new int[] { state });
    }
	
//	@Override
//	protected void onDraw(Canvas canvas) {
//		if (mChecked)
//			canvas.drawColor(mColorActivated);
//		super.onDraw(canvas);
//	}

}
