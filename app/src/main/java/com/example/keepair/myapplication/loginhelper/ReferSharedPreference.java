package com.example.keepair.myapplication.loginhelper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Keepair on 2016-09-17.
 */
public class ReferSharedPreference {


    private final String REF_PREF_NAME = "com.rabiaband.pref";

    public final static String PREF_INTRO_USER_AGREEMENT = "PREF_USER_AGREEMENT";
    public final static String REF_LOGIN_KEY = "REF_LOGIN_KEY";


    static Context mContext;

    public ReferSharedPreference(Context c) {
        mContext = c;
    }

    public boolean put(String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences(REF_PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value);
        editor.commit();
        return false;
    }

    public void put(String key, boolean value) {
        SharedPreferences pref = mContext.getSharedPreferences(REF_PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);
        editor.commit();
    }

    public void put(String key, int value) {
        SharedPreferences pref = mContext.getSharedPreferences(REF_PREF_NAME,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(key, value);
        editor.commit();
    }

    public String getValue(String key, String dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(REF_PREF_NAME,
                Activity.MODE_PRIVATE);

        try {
            return pref.getString(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }

    public int getValue(String key, int dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(REF_PREF_NAME,
                Activity.MODE_PRIVATE);

        try {
            return pref.getInt(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }

    public boolean getValue(String key, boolean dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(REF_PREF_NAME,
                Activity.MODE_PRIVATE);

        try {
            return pref.getBoolean(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }
}
