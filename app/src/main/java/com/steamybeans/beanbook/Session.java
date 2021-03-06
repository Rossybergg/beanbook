package com.steamybeans.beanbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUsername(String username) {
        prefs.edit().putString("username", username).commit();
    }

    public String getUsername() {
        String username = prefs.getString("username", "");
        return username;
    }

    public void setFullName(String fullName) {
        prefs.edit().putString("fullName", NameFormatter.capitalize(fullName)).commit();
    }

    public String getFullName() {
        String fullName = prefs.getString("fullName", "");
        return fullName;
    }

    public void logout() {
        prefs.edit().remove("username").commit();
    }
}
