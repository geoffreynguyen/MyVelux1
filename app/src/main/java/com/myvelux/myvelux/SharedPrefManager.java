package com.myvelux.myvelux;

import android.content.Context;
import android.content.SharedPreferences;

// all methods are static , so we can call from any where in the code
//all member variables are private, so that we can save load with our own fun only
public class SharedPrefManager {
    //this is your shared preference file name, in which we will save data
    public static final String MY_EMP_PREFS = "MySharedPref";

    //saving the context, so that we can call all
    //shared pref methods from non activity classes.
    //because getSharedPreferences required the context.
    //but in activity class we can call without this context
    private static Context 	mContext;

    // will get user input in below variables, then will store in to shared pref
    private static String 	mLogin 			= "";
    private static int 	mIdLogin 		    = 0;
    private static boolean 	mConnected 		= false;
    private static int 	idClient 		    = 0;

    public static void Init(Context context)
    {
        mContext 		= context;
    }
    public static void LoadFromPref()
    {
        SharedPreferences settings 	= mContext.getSharedPreferences(MY_EMP_PREFS, 0);
        // Note here the 2nd parameter 0 is the default parameter for private access,
        //Operating mode. Use 0 or MODE_PRIVATE for the default operation,
        mLogin 			= settings.getString("Login",""); // 1st parameter Name is the key and 2nd parameter is the default if data not found
        mConnected 		= settings.getBoolean("Connected",false);
        mIdLogin 		= settings.getInt("idLogin",0);
        idClient 		= settings.getInt("idClient",0);
    }
    public static void StoreToPref()
    {
        // get the existing preference file
        SharedPreferences settings = mContext.getSharedPreferences(MY_EMP_PREFS, 0);
        //need an editor to edit and save values
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Login",mLogin); // Name is the key and mName is holding the value
        editor.putBoolean("Connected", mConnected); // Age is the key and mAge is holding the value
        editor.putInt("idLogin", mIdLogin); // Age is the key and mAge is holding the value
        editor.putInt("idClient", idClient); // Age is the key and mAge is holding the value

        //final step to commit (save)the changes in to the shared pref
        editor.commit();
    }

    public static void DeleteSingleEntryFromPref(String keyLogin)
    {
        SharedPreferences settings = mContext.getSharedPreferences(MY_EMP_PREFS, 0);
        //need an editor to edit and save values
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(keyLogin);
        editor.commit();
    }

    public static void DeleteAllEntriesFromPref()
    {
        SharedPreferences settings = mContext.getSharedPreferences(MY_EMP_PREFS, 0);
        //need an editor to edit and save values
        SharedPreferences.Editor editor = settings.edit();
        setIdClient(0);
        setConnected(false);
        setLogin("");
        setIdLogin(0);
        editor.clear();
        editor.commit();
    }

    public static void setLogin(String login)
    {
        mLogin = login;
    }

    public static void setConnected(boolean connected)
    {
        mConnected = connected;
    }

    public static String getLogin()
    {
        return mLogin ;
    }

    public static boolean getConnected()
    {
        return mConnected ;
    }

    public static int getIdClient() {
        return idClient;
    }

    public static void setIdClient(int id) {
        idClient = id;
    }

    public static int getIdLogin() {
        return mIdLogin;
    }

    public static void setIdLogin(int idLogin) {
        mIdLogin = idLogin;
    }

}