package com.example.contactapptoken.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App: Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        instance = this
    }
}