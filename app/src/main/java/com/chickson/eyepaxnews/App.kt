package com.chickson.eyepaxnews

import android.app.Application
import com.chickson.eyepaxnews.util.Prefs
import dagger.hilt.android.HiltAndroidApp

val prefs: Prefs by lazy {
    App.prefs!!
}

@HiltAndroidApp
class App: Application() {
    companion object {
        var prefs: Prefs? = null


        lateinit var instance: App
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = Prefs(applicationContext)

    }
}