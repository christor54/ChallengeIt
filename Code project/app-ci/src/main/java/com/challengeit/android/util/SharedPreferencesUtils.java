package com.challengeit.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by christophe on 6/29/2014.
 */
public class SharedPreferencesUtils {


    public static void saveUsernameUserLocal(String username,Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences("userLocalDetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", username);
        editor.commit();
    }

    public static void savePasswordUserLocal(String username,Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences("userLocalDetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("password", username);
        editor.commit();
    }


    public static String getUserLocalSavedUsername(Activity activity) {
        String username=null;
        SharedPreferences sharedPref = activity.getSharedPreferences("userLocalDetails",Context.MODE_PRIVATE);
        username= sharedPref.getString("username",username);
        return username;
    }

    public static String getUserLocalSavedPassword(Activity activity) {
        String password=null;
        SharedPreferences sharedPref = activity.getSharedPreferences("userLocalDetails",Context.MODE_PRIVATE);
        password= sharedPref.getString("password",password);
        return password;
    }
}
