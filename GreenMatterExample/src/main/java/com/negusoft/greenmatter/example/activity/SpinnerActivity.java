package com.negusoft.greenmatter.example.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.negusoft.greenmatter.MatPalette;
import com.negusoft.greenmatter.activity.MatActivity;
import com.negusoft.greenmatter.example.R;
import com.negusoft.greenmatter.example.util.ColorOverrider;

public class SpinnerActivity extends MatActivity {

    private static final String[] ITEMS = new String[] { "Item 1", "Item 2", "Item 3", "Item 4" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        initSpinners();
    }

    @Override
    public MatPalette overridePalette(MatPalette palette) {
        return ColorOverrider.getInstance(this).applyOverride(palette);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initSpinners() {
        Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner1.setAdapter(new DefaultArrayAdapter());
        Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner2.setAdapter(new DefaultArrayAdapter());
    }

    private class DefaultArrayAdapter extends ArrayAdapter<String> {
        public DefaultArrayAdapter() {
            super(SpinnerActivity.this, android.R.layout.simple_spinner_dropdown_item, ITEMS);
        }
    }

}
