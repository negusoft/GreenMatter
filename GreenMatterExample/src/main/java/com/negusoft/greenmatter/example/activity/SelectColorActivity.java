package com.negusoft.greenmatter.example.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.activity.MatActivity;
import com.negusoft.greenmatter.example.R;
import com.negusoft.greenmatter.example.util.ColorOverrider;

public class SelectColorActivity extends MatActivity {

    private SwitchCompat mOverrideSwitch;
    private TextView mOverrideText;
    private SeekBar mPrimarySeekbar;
    private SeekBar mAccentSeekbar;
    private View mPrimaryPreview;
    private View mAccentPreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_color);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MatPalette palette = getMatHelper().getPalette(this);
        final ColorOverrider overrider = ColorOverrider.getInstance(palette);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overrider.enabled = mOverrideSwitch.isChecked();
                overrider.colorPrimary = replaceHue(overrider.colorPrimary, mPrimarySeekbar.getProgress());
                overrider.colorAccent = replaceHue(overrider.colorAccent, mAccentSeekbar.getProgress());
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        mOverrideSwitch = (SwitchCompat)findViewById(R.id.overrideSwitch);
        mOverrideSwitch.setChecked(overrider.enabled);
        mOverrideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setComponentsEnable(isChecked, overrider);
            }
        });

        mOverrideText = (TextView)findViewById(R.id.overrideText);
        mOverrideText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOverrideSwitch.toggle();
            }
        });

        mPrimarySeekbar = (SeekBar)findViewById(R.id.primary_seekbar);
        mPrimarySeekbar.setProgress((int) getHue(overrider.colorPrimary));
        mPrimarySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int color = replaceHue(overrider.colorPrimary, progress);
                mPrimaryPreview.setBackgroundColor(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mAccentSeekbar = (SeekBar)findViewById(R.id.accent_seekbar);
        mAccentSeekbar.setProgress((int) getHue(overrider.colorAccent));
        mAccentSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int color = replaceHue(overrider.colorAccent, progress);
                mAccentPreview.setBackgroundColor(color);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        mPrimaryPreview = findViewById(R.id.primary_preview);
        mAccentPreview = findViewById(R.id.accent_preview);

        setComponentsEnable(overrider.enabled, overrider);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setComponentsEnable(boolean enabled, ColorOverrider overrider) {
        mPrimarySeekbar.setEnabled(enabled);
        mAccentSeekbar.setEnabled(enabled);

        int primaryColor = replaceHue(overrider.colorPrimary, mPrimarySeekbar.getProgress());
        mPrimaryPreview.setBackgroundColor(enabled ? primaryColor : Color.DKGRAY);
        int accentColor = replaceHue(overrider.colorAccent, mAccentSeekbar.getProgress());
        mAccentPreview.setBackgroundColor(enabled ? accentColor : Color.GRAY);

        int textId = enabled ? R.string.select_colors_override_on : R.string.select_colors_override_off;
        mOverrideText.setText(textId);
    }

    @Override
    public MatPalette overridePalette(MatPalette palette) {
        return ColorOverrider.getInstance(palette).applyOverride(palette);
    }

    /** Get the hue component of the color [0..360]. */
    private float getHue(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        return hsv[0];
    }

    /** Replace the hue in the given color */
    private int replaceHue(int color, float hue) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[0] = hue;
        return Color.HSVToColor(hsv);
    }

}
