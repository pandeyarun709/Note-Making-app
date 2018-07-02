package com.example.arunpandey.notemakingapp;

import android.content.Context;
import android.content.SharedPreferences;


public class Prefrences {

    SharedPreferences sharedPreferences;
     Context context;

    private final String username = "username";
    private final String passwor = "password";
    private final String isLogIn = "IsLogin";
    private static Prefrences prefrences;



    private Prefrences(Context context)
    {
        this.context = context;

        sharedPreferences = context.getSharedPreferences("Prefs",Context.MODE_PRIVATE);
    }

    public static Prefrences getInstance(Context context)
    {
        if(prefrences == null)
        {
            prefrences =new Prefrences(context);

        }
        return prefrences;
    }
    void createUser(String user,String password)
    {
        sharedPreferences.edit().putString(username,user).apply();
        sharedPreferences.edit().putString(passwor,password).apply();
        sharedPreferences.edit().putBoolean(isLogIn,true).apply();

    }

    String getUserName()
    {
        return sharedPreferences.getString(username, " ");

    }

    Boolean isLogin()
    {
        return sharedPreferences.getBoolean(isLogIn,false);
    }

    void logOut()
    {
        sharedPreferences.edit().putBoolean(isLogIn,false).apply();
    }




}
