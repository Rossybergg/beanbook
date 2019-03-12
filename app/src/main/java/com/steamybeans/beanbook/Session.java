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

    public void setFullname(String fullname) {
        prefs.edit().putString("fullname", fullname).commit();
    }

    public String getUsername() {
        String username = prefs.getString("username", "");
        return username;
    }

    public String getFullname() {
        String fullname = prefs.getString("fullname","");
        return fullname;
    }

    public void logout() {
        prefs.edit().remove("username").commit();
    }
}
