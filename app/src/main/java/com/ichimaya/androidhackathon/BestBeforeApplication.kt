package com.ichimaya.androidhackathon


import android.app.Application
import timber.log.Timber

class BestBeforeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}