package com.ichimaya.androidhackathon

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import timber.log.Timber

class BestBeforeApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}