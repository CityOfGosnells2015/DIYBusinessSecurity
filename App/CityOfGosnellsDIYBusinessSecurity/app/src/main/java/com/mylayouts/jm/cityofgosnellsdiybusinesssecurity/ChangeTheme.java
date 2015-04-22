package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.Activity;
import android.content.Intent;

/**
 * Class
 */
public class ChangeTheme {

    private static int sTheme;
    public final static int THEME_LARGE_TEXT = 1;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity, int sTheme) {
        switch (sTheme) {
            case THEME_LARGE_TEXT:
                activity.setTheme(R.style.AppThemeLargeText);
                break;

            default:
                activity.setTheme(R.style.AppTheme);
                break;
        }
    }
}
