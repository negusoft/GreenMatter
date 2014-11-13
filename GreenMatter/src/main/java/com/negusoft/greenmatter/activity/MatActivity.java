package com.negusoft.greenmatter.activity;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;

import com.negusoft.greenmatter.MatHelper;
import com.negusoft.greenmatter.MatResources;

/**
 * Extends the ActionBarActivity class and adds the GreenMatter configuration.
 */
public abstract class MatActivity extends ActionBarActivity {

    private final MatHelper mMatHelper = new MatHelper(getOverridePrimaryColor(),
            getOverridePrimaryColorDark(), getOverrideAccentColor(), new MyInitListener());

    @Override
    public Resources getResources() {
        return mMatHelper.getResources(this, super.getResources());
    }

    /**
     * Override this method to set the primary color programmatically.
     * @return The color to override. If the color is equals to 0, the
     * primary color will be taken from the theme.
     */
    public int getOverridePrimaryColor() {
        return 0;
    }

    /**
     * Override this method to set the dark variant of the primary color programmatically.
     * @return The color to override. If the color is equals to 0, the dark version will be
     * taken from the theme. If it is specified in the theme either, it will be calculated
     * based on the primary color.
     */
    public int getOverridePrimaryColorDark() {
        return 0;
    }

    /**
     * Override this method to set the  accent color programmatically.
     * @return The color to override. If the color is equals to 0, the
     * accent color will be taken from the theme.
     */
    public int getOverrideAccentColor() {
        return 0;
    }

    /** Getter for the MatHelper instance. */
    public MatHelper getMatHelper() {
        return mMatHelper;
    }

    /**
     * Override this function to modify the MatResources instance. You can add your own logic
     * to the default GreenMatter behaviour.
     */
    public void onInitMatResources(MatResources resources) {
        // To be overriden in child classes.
    }

    private class MyInitListener implements MatHelper.OnInitListener {
        @Override
        public void onInitResources(MatResources resources) {
            onInitMatResources(resources);
        }
    }

}
