package com.example.contactapptoken.data.source.local

import android.content.Context
import com.example.contactapptoken.app.App

class MySharedPref {
    companion object {
        private val instance = MySharedPref()
        private val pref = App.instance.getSharedPreferences("ContactApp", Context.MODE_PRIVATE )

        fun getInstance() = instance
    }

    var token: String
        set(value) = pref.edit().putString("TOKEN", value).apply()
        get() = pref.getString("TOKEN", "").toString() // WARNING!!

    var hasToken: Boolean
        set(bool) = pref.edit().putBoolean("HASTOKEN", bool).apply()
        get() = pref.getBoolean("HASTOKEN", false)

    var phone: String
        set(log) = pref.edit().putString("LOGIN", log).apply()
        get() = pref.getString("LOGIN", "").toString()

    var parol: String
        set(pas) = pref.edit().putString("PASSWORD", pas).apply()
        get() = pref.getString("PASSWORD", "").toString()
}