package com.negusoft.greenmatter.example.preference;

import android.content.Context;
import android.util.AttributeSet;

import com.negusoft.greenmatter.example.R;
import com.negusoft.greenmatter.preference.DialogPreference;

public class CustomDialogPreference extends DialogPreference {

	public CustomDialogPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDialogMessage(R.string.pref_message_dialog);
	}

	public CustomDialogPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setDialogMessage(R.string.pref_message_dialog);
	}

}
