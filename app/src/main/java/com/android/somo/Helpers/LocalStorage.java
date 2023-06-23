/**For reading and writing data to local storage using shared preferences**/
package com.android.somo.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalStorage {

    private SharedPreferences sharedPreferences;

    public void initializeSharedPreferences(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void storeData(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String retrieveData(String key){
        return sharedPreferences.getString(key, "");
    }

    public void deleteData(String key){
     SharedPreferences.Editor editor = sharedPreferences.edit();
     editor.remove(key);
     editor.apply();
    }

}
