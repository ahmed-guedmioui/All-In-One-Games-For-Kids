package com.ahmedapps.walkthrough.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared {

    private static final String filename = "Ahmed_all_in_one_games_for_kids_app_data";
    private static SharedPreferences mPreferences;

    private static SharedPreferences getInstance(Context context) {
        if (mPreferences == null) {
            mPreferences = context.getApplicationContext()
                    .getSharedPreferences(filename, Context.MODE_PRIVATE);
        }
        return mPreferences;
    }

    public static void setLocked(Context context, int id, boolean isLocked) {
        getInstance(context).edit().putBoolean("" + id, isLocked).apply();
    }

    public static boolean getLocked(Context context, int id) {
        return getInstance(context).getBoolean("" + id, true);
    }


    public static void setString(Context context, String key, String v) {

        getInstance(context).edit().putString(key, v).apply();

    }

    public static String getString(Context context, String key, String def) {
        return getInstance(context).getString(key, def);
    }


    public static boolean getBoolean(Context context, String key, boolean mDefault) {
        return getInstance(context).getBoolean(key, mDefault);
    }

    public static void setBoolean(Context context, String key, boolean v) {
        getInstance(context).edit().putBoolean(key, v).apply();
    }


    public static int getInt(Context context, String key, int def) {
        return getInstance(context).getInt(key, def);
    }

    public static void setInt(Context context, String key, int value) {
        getInstance(context).edit().putInt(key, value).apply();
    }

}
