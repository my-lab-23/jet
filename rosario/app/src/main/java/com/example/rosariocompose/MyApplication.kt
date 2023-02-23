package com.example.rosariocompose

import android.app.Application
import android.content.Context

// Class to get the application context from anywhere in the app
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
    }
}
