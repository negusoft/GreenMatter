package com.negusoft.greenmatter.example.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.activity.MatActivity;
import com.negusoft.greenmatter.example.R;
import com.negusoft.greenmatter.example.util.ColorOverrider;

public class MainActivity extends MatActivity {

    private static final String URL_GITHUB = "https://github.com/negusoft/holoaccent";

    private static final String[] ITEMS = new String[] {
            "Showcase (tabs)", "Showcase (tab strip)", "Spinner", "Preferences", "Dialog" };

    private static final String[] THEMES_DEFAULT = new String[] {
            "Default (Dark)", "ColoredActionBar", "ColoredActionBar (Inverse)",
            "Light", "Light DarkActionBar", "Light ColoredActionBar", "Light ColoredActionBar (Inverse)"
    };
    private static final String[] THEMES_DIALOG = new String[] {
            "Default (Dark)", "Light"
    };
    private static final String[][] THEMES = new String[][] {
            THEMES_DEFAULT, THEMES_DEFAULT, THEMES_DEFAULT, THEMES_DEFAULT, THEMES_DIALOG
    };

    private Spinner mActivitySpinner;
    private Spinner mThemeSpinner;

    private String[] mCurrentThemes = THEMES_DEFAULT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActivitySpinner = (Spinner)findViewById(R.id.activitySpinner);
        mActivitySpinner.setAdapter(new ActivityAdapter());
        mActivitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mCurrentThemes == THEMES[i])
                    return;
                mCurrentThemes = THEMES[i];
                mThemeSpinner.setAdapter(new ThemeAdapter(THEMES[i]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mThemeSpinner = (Spinner)findViewById(R.id.themeSpinner);
        mThemeSpinner.setAdapter(new ThemeAdapter(THEMES[0]));

        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCustomActivity();
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, SelectColorActivity.class), 5);
            }
        });
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_github:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_GITHUB));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        recreate();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public MatPalette overridePalette(MatPalette palette) {
        return ColorOverrider.getInstance(palette).applyOverride(palette);
    }

    private void startCustomActivity() {
        Intent intent = getCustomIntent();
        startActivityForResult(intent, 10);
    }

    private Intent getCustomIntent() {
        switch (mActivitySpinner.getSelectedItemPosition()) {
            case 0: // Showcase (tabs)
                return getTabbedActivityIntent();
            case 1: // Showcase (tab strip)
                return getTabbedStripActivityIntent();
            case 2: // Spinner
                return getSpinnerActivityIntent();
            case 3: // Preferences
                return getPreferencesActivityIntent();
            case 4: // Dialog
                return getDialogActivityIntent();
        }
        throw new RuntimeException("Invalid activity type selected");
    }

    private Intent getTabbedActivityIntent() {
        switch (mThemeSpinner.getSelectedItemPosition()) {
            case 0: // Default (Dark)
                return new Intent(this, TabbedActivity.class);
//            case 1: // ColoredActionBar
//                return new Intent(this, TabbedActivityColoredAB.class);
//            case 2: // ColoredActionBar (Inverse)
//                return new Intent(this, TabbedActivityColoredABInverse.class);
//            case 3: // Light
//                return new Intent(this, TabbedActivityLight.class);
//            case 4: // Light.DarkActionBar
//                return new Intent(this, TabbedActivityLightDarkAB.class);
//            case 5: // Light ColoredActionBar
//                return new Intent(this, TabbedActivityLightColoredAB.class);
//            case 6: // Light.ColoredActionBar (Inverse)
//                return new Intent(this, TabbedActivityLightColoredABInverse.class);
        }
        throw new RuntimeException("Invalid theme selected");
    }

    private Intent getTabbedStripActivityIntent() {
        switch (mThemeSpinner.getSelectedItemPosition()) {
            case 0: // Default (Dark)
                return new Intent(this, TabbedStripActivity.class);
//            case 1: // ColoredActionBar
//                return new Intent(this, TabbedStripActivityColoredAB.class);
//            case 2: // ColoredActionBar (Inverse)
//                return new Intent(this, TabbedStripActivityColoredABInverse.class);
//            case 3: // Light
//                return new Intent(this, TabbedStripActivityLight.class);
//            case 4: // Light.DarkActionBar
//                return new Intent(this, TabbedStripActivityLightDarkAB.class);
//            case 5: // Light ColoredActionBar
//                return new Intent(this, TabbedStripActivityLightColoredAB.class);
//            case 6: // Light.ColoredActionBar (Inverse)
//                return new Intent(this, TabbedStripActivityLightColoredABInverse.class);
        }
        throw new RuntimeException("Invalid theme selected");
    }

    private Intent getSpinnerActivityIntent() {
//        switch (mThemeSpinner.getSelectedItemPosition()) {
//            case 0: // Default (Dark)
//                return new Intent(this, SpinnerActivity.class);
//            case 1: // ColoredActionBar
//                return new Intent(this, SpinnerActivityColoredAB.class);
//            case 2: // ColoredActionBar (Inverse)
//                return new Intent(this, SpinnerActivityColoredABInverse.class);
//            case 3: // Light
//                return new Intent(this, SpinnerActivityLight.class);
//            case 4: // Light.DarkActionBar
//                return new Intent(this, SpinnerActivityLightDarkAB.class);
//            case 5: // Light ColoredActionBar
//                return new Intent(this, SpinnerActivityLightColoredAB.class);
//            case 6: // Light.ColoredActionBar (Inverse)
//                return new Intent(this, SpinnerActivityLightColoredABInverse.class);
//        }
        throw new RuntimeException("Invalid theme selected");
    }

    private Intent getPreferencesActivityIntent() {
//        switch (mThemeSpinner.getSelectedItemPosition()) {
//            case 0: // Default (Dark)
//                return new Intent(this, PreferencesActivity.class);
//            case 1: // ColoredActionBar
//                return new Intent(this, PreferencesActivityColoredAB.class);
//            case 2: // ColoredActionBar (Inverse)
//                return new Intent(this, PreferencesActivityColoredABInverse.class);
//            case 3: // Light
//                return new Intent(this, PreferencesActivityLight.class);
//            case 4: // Light.DarkActionBar
//                return new Intent(this, PreferencesActivityLightDarkAB.class);
//            case 5: // Light ColoredActionBar
//                return new Intent(this, PreferencesActivityLightColoredAB.class);
//            case 6: // Light.ColoredActionBar (Inverse)
//                return new Intent(this, PreferencesActivityLightColoredABInverse.class);
//        }
        throw new RuntimeException("Invalid theme selected");
    }

    private Intent getDialogActivityIntent() {
//        switch (mThemeSpinner.getSelectedItemPosition()) {
//            case 0: // Default (Dark)
//                return new Intent(this, DialogActivity.class);
//            case 1: // Light
//                return new Intent(this, DialogActivityLight.class);
//        }
        throw new RuntimeException("Invalid theme selected");
    }

    private class ActivityAdapter extends ArrayAdapter<String> {
        public ActivityAdapter() {
            super(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, ITEMS);
        }
    }

    private class ThemeAdapter extends ArrayAdapter<String> {
        public ThemeAdapter(String[] items) {
            super(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
        }
    }

}
