package com.example.newsmoengage.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPrefs {

    private static final String PREFS_NAME = "com.articles.moengage";

    public  static final String PREFS_ARTICLE = "prefsArticles";

    public static boolean setPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "");
    }

}
