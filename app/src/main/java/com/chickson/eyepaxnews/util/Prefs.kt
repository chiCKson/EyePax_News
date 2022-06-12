package com.chickson.eyepaxnews.util

import android.content.Context
import android.content.SharedPreferences
import com.chickson.eyepaxnews.models.User
import com.google.gson.Gson

class Prefs (context: Context)
{
    private val prefsFileName = "com.chickson.eyepaxnews"
    private val USER = "current_user"
    private val preferences: SharedPreferences = context.getSharedPreferences(prefsFileName,0)

    var user: User?
        get() = Gson().fromJson(preferences.getString(USER, ""), User::class.java)
        set(value) = preferences.edit().putString(USER, Gson().toJson(value)).apply()
}