package com.lucas.mynews.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.lucas.mynews.Controllers.Activities.MainActivity;

public class SharedPref {

    private static SharedPreferences mSharedPref;
    public static String nbArticles = "nbArticles";
    public static String checkBoxArts = "checkBoxArts";
    public static String checkBoxBusiness = "checkBoxBusiness";
    public static String checkBoxEntrepreneurs = "checkBoxEntrepreneurs";
    public static String checkBoxPolitics = "checkBoxPolitics";
    public static String checkBoxSports = "checkBoxSports";
    public static String checkBoxTravel = "checkBoxTravel";
    public static String txtBoxSearchNotification = "txtBoxSearchNotification";
    public static String switchNotification = "switchNotification";
    public static String notificationAllow = "notificationAllow";
    public static String notificationArticleSearch = "notificationArticleSearch";

    static Context applicationContext = MainActivity.getContextOfApplication();


    private SharedPref() { }

    public static void init(Context context) {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).commit();
    }
}
