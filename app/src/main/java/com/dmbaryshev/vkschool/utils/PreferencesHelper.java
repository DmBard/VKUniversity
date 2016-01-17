package com.dmbaryshev.vkschool.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    private static final String PREF_NAME  = "com.dmbaryshev.vkschool.utils.pref.NAME";
    private static final String PREF_TOKEN = "com.dmbaryshev.vkschool.utils.pref.TOKEN";

    private static PreferencesHelper sPreferencesHelper;
    private        SharedPreferences mSharedPreferences;

    private PreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized PreferencesHelper getInstance(Context context) {
        if (sPreferencesHelper == null) {
            sPreferencesHelper = new PreferencesHelper(context);
        }
        return sPreferencesHelper;
    }

    public String getToken() {
        return mSharedPreferences.getString(PREF_TOKEN, null);
    }

    public void setToken(String token) {
        mSharedPreferences.edit().putString(PREF_TOKEN, token).apply();
    }
}
