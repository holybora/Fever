package com.sls.handbook

import android.app.Application
import android.util.Log
import com.theapache64.rebugger.RebuggerConfig
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point for FeverWeather.
 *
 * Initializes Hilt dependency injection and configures Rebugger for
 * Compose recomposition logging in debug builds.
 */
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
