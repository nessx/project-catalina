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

    //GOOGLE
    public void SaveGoogleLoginDetails(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GoogleLoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ID", id);
        editor.commit();
    }

    public boolean isUserGoogleLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GoogleLoginDetails", Context.MODE_PRIVATE);
        boolean isIdEmpty = sharedPreferences.getString("ID", "").isEmpty();
        Log.i("SHARED", "SP "+isIdEmpty);
        return isIdEmpty;
    }
    //END

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


    public void reset() {
        SharedPreferences login = context.getSharedPreferences("GoogleLoginDetails", Context.MODE_PRIVATE);
        login.edit().clear().commit();
    }

}