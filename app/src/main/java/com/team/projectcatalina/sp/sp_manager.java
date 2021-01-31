package com.team.projectcatalina.sp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

    //GOOGLE
    public void SaveGoogleLoginDetails(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GoogleLoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ID", id);
        editor.commit();
    }

    public void savelangDetails(String lang) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LangDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Lang", lang);
        editor.commit();
    }
    public String getLang() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GoogleLoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("ID", "");
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

    public boolean isUserGoogleLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GoogleLoginDetails", Context.MODE_PRIVATE);
        boolean isIdEmpty = sharedPreferences.getString("ID", "").isEmpty();
        Log.i("SHARED", "SP "+isIdEmpty);
        return isIdEmpty;
    }

    public void reset() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GoogleLoginDetails", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        //SharedPreferences login = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        //SharedPreferences lang = context.getSharedPreferences("LangDetails", Context.MODE_PRIVATE);
        //SharedPreferences Glang = context.getSharedPreferences("GoogleLoginDetails", Context.MODE_PRIVATE);
        //login.edit().clear().commit();
        //ang.edit().clear().commit();
        //Glang.edit().clear().commit();
    }

}