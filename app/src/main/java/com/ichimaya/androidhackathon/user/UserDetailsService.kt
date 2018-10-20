package com.ichimaya.androidhackathon.user

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.*

class UserDetailsService {
    fun getUUID(context: Context): String {
        val preferenceManager = PreferenceManager.getDefaultSharedPreferences(context)
        return if (preferenceManager.contains(KEY_UUID)) {
            preferenceManager.getString(KEY_UUID, null) ?: generateUUID(preferenceManager)
        } else {
            generateUUID(preferenceManager)
        }
    }

    private fun generateUUID(preferenceManager: SharedPreferences): String {
        val uuid = UUID.randomUUID().toString()
        preferenceManager.edit().putString(KEY_UUID, uuid).apply()
        return uuid
    }

    companion object {
        const val KEY_UUID = "UUID"
    }
}