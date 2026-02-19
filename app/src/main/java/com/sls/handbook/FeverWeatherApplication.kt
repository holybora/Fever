package com.sls.handbook

import android.app.Application
import android.util.Log
import com.theapache64.rebugger.RebuggerConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FeverWeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            RebuggerConfig.init(
                tag = "FeverWeather",
                logger = { tag, message -> Log.d(tag, message) },
            )
        }
    }
}
