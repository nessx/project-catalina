package com.team.projectcatalina.sp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class for Shared Preference
 */
public class sp_manager {

    Context context;

    public sp_manager(Context context) {
        this.context = context;
    }

    public void saveLoginDetails(String user, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("User", user);
        editor.putString("Password", password);
        editor.commit();
    }

    public void savelangDetails(String lang) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LangDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Lang", lang);
        editor.commit();
    }
    public String getLang() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LangDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Lang", "");
    }

    public boolean noLang() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LangDetails", Context.MODE_PRIVATE);
        boolean langnull = sharedPreferences.getString("Lang", "").isEmpty();
        return langnull;
    }

    public String getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("User", "");
    }
    public String getPass() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Password", "");
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isUserEmpty = sharedPreferences.getString("User", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isUserEmpty || isPasswordEmpty;
    }

    public void reset() {
        SharedPreferences login = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences lang = context.getSharedPreferences("LangDetails", Context.MODE_PRIVATE);
        login.edit().clear().commit();
        lang.edit().clear().commit();
    }

}