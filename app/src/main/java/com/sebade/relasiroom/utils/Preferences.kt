package com.sebade.relasiroom.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(context : Context) {
    companion object {
        const val USER_PREFERENCE = "USER_PREFERENCE"
    }

    private var sharedPreferences = context.getSharedPreferences(USER_PREFERENCE, 0)

    fun setValue(key : String, value : String) {
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValue(key: String) : String? {
        return sharedPreferences.getString(key, "")
    }

    fun checkKey(key: String) : Boolean {
        return sharedPreferences.contains(key)
    }
    fun clearKey(key : String){
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(key).apply()
    }

}